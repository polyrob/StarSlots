package com.scheidt.slots.api;

import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;
import info.movito.themoviedbapi.TmdbApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.*;


public class Batch {

	private TmdbApi tmdb;
	private ActorUtil au;
	private MovieUtil mu;

	private static final String LANGUAGE_ENGLISH = "en";
	private static final String APIKEY = "7fa8a73aff547477888c3c5e03f864b1";
	private static final String RAW_ACTOR_IDS = "data/actorIDs.txt";
	private static final String DATAFILE_ACTORS = "actors.data";
	private static final String DATAFILE_MOVIES = "movies.data";
	private static final Level LOG_LEVEL = Level.FINEST;

	private List<Star> actorList;
	private List<Movie> movieList;
	private Map<String, Integer> movieIdMap;
	private List<String> actorIdList;

	private static Logger LOG = Logger.getLogger("com.hwslots");

	public static void main(String[] args) throws Exception {

		setupLogger();
		
		LOG.info("Starting batch...");
		long startTime = System.currentTimeMillis();
		
		Batch batch = new Batch();

		batch.loadDiskData();

		batch.generateActors(140); // max

		batch.generateMovies();

		LOG.info("Batch completed. " + (System.currentTimeMillis() - startTime) + "ms.");
	}

	private void loadDiskData() throws Exception {
//		actorList = getActorsFromDisk();
		if (actorList == null)
			actorList = new ArrayList<Star>();
//		movieList = getMoviesFromDisk();
		if (movieList == null)
			movieList = new ArrayList<Movie>();
	}

	public Batch() {
		tmdb = new TmdbApi(APIKEY);
		au = new ActorUtil(tmdb);
		mu = new MovieUtil(tmdb, LANGUAGE_ENGLISH);
	}

	public void generateActors(int max) throws Exception {

		/* load ID file */
		actorIdList = au.getActorIDsFromFile(RAW_ACTOR_IDS);

		//removeExistingActorIds(actorIdList);
		au.getActorListFromRemote(actorList, actorIdList, max);

		/* optimize actorList filmographies and get set of valid movieIds */
		movieIdMap = au.optimizeFilmographies(actorList);
		
		/* TEST */
		actorList.get(2).getBonuses().put("ROB BIGGITY BONUS!", 50);

		/* serialize actor data to disk */
		SerializeUtil.saveObjectToDisk(actorList, DATAFILE_ACTORS);
	}

	private void generateMovies() throws Exception {
		
//		removeExistingMovieIds(movieIdMap);
		mu.getMovieListFromRemote(movieList, movieIdMap, actorIdList);

		/* serialize movie data to disk */
		SerializeUtil.saveObjectToDisk(movieList, DATAFILE_MOVIES);
	}


	
	/***************************
	 * 
	 * 		OPTIMIZATIONS
	 * 
	 ***************************/

	public void optimizeMovieCasts() throws Exception {
		/* run optimization */
		mu.optimizeCasts(movieList, actorList);
	}
	
	
	private void removeExistingActorIds(List<String> actorIdList) {
		LOG.info("Removing existing ACTORS from list of IDs...");
		List<String> existingList = new ArrayList<String>();
		for (Star actor : actorList) {
			existingList.add(String.valueOf(actor.getStarId()));
		}
		actorIdList.removeAll(existingList);
	}
	
//	private void removeExistingMovieIds(Map<String, Integer> freshMovieIdMap) {
//		LOG.info("Removing existing MOVIES from list of IDs...");
//		Iterator<Map.Entry<String, Integer>> iter = freshMovieIdMap.entrySet().iterator();
//		while (iter.hasNext()) {
//			Map.Entry<String, Integer> entry = iter.next();
//			if (entry.getKey()) {
//				iter.remove();
//			}
//		}
//	}


	@SuppressWarnings("unchecked")
	private List<Star> getActorsFromDisk() throws Exception {
		return (List<Star>) SerializeUtil.loadObjectFromDisk(DATAFILE_ACTORS);
	}

	@SuppressWarnings("unchecked")
	private List<Movie> getMoviesFromDisk() throws Exception {
		return (List<Movie>) SerializeUtil.loadObjectFromDisk(DATAFILE_MOVIES);
	}



	private static void setupLogger() {
		 LOG.setUseParentHandlers(false);
		    Handler conHdlr = new ConsoleHandler();
		    conHdlr.setFormatter(new Formatter() {
		      public String format(LogRecord record) {
		        return record.getLevel() + "|"
		            + record.getSourceClassName() + "|"
		            + record.getMessage() + "\n";
		      }
		    });
		    LOG.addHandler(conHdlr);
		    LOG.setLevel(LOG_LEVEL);
	}
}
