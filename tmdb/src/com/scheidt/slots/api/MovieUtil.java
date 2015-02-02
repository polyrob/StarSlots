package com.scheidt.slots.api;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.Person;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;

public class MovieUtil {

	private TmdbApi tmdb;
	private String language = "en";

	private static Logger LOG = Logger.getLogger("com.hwslots");

	public MovieUtil(TmdbApi tmdb, String language) {
		this.tmdb = tmdb;
		this.language = language;
	}
	
//	
//	public List<String> getMovieIDsFromFile(String filename) throws Exception {
//		List<String> movieIdList;
//
//		/* read actorIDs from disk */
//		File file = new File(filename);
//		movieIdList = FileUtil.getListFromFile(file);
//		LOG.info("Movies read from file = " + movieIdList.size());
//		return movieIdList;
//	}
	
	
	public void getMovieListFromRemote(List<Movie> movieList, Map<String, Integer> movieIdMap, List<String> actorIdList) throws Exception {
		LOG.info("Getting MOVIE data remotely... ");
		
		Set<String> set = movieIdMap.keySet();
		Iterator<String> iter = set.iterator();
		int index = 0;
		while (iter.hasNext()) {
			index++;
			if (index % 10 == 0 && index != 0) {
				LOG.info("Processing... " + index + " out of " + set.size());
			}
			String movieId = iter.next();
			Movie m = getMovieRemote(movieId, actorIdList);
			if (m != null) movieList.add(m);
		}
		
		
		LOG.info("Data obtained for " + movieList.size() + " movies.");
	}
	

	public Movie getMovieRemote(String id, List<String> actorIdList) {
		MovieDb result = tmdb.getMovies().getMovie(Integer.parseInt(id), language);
//		if (result.getRuntime() < 45) return null; // if its a short lets not use it
		Movie m = new Movie();
		m.setMovieId(id);
		m.setTitle(result.getTitle());
		m.setPopularity(result.getPopularity());
		m.setBudget(result.getBudget());
		m.setRevenue(result.getRevenue());
		m.setStatus(result.getStatus());
		m.setReleaseDate(result.getReleaseDate());
		m.setUserRating(result.getUserRating());
		m.setVoteAverage(result.getVoteAverage());

		Credits people = tmdb.getMovies().getCredits(Integer.parseInt(id));
		List<Integer> castIdList = new ArrayList<Integer>();
		for (Person person : people.getAll()) {
			if (actorIdList.contains(String.valueOf(person.getId()))) {
				castIdList.add(person.getId());
			}
		}
		m.setCast(castIdList);

		return m;
	}
	
	
	public Set<Integer> optimizeCasts(List<Movie> movieLIst, List<Star> actorList) {
		return null;
	}
	
	
	
	public void outputMovies(Set<Integer> movieIdSet) {
		for(Integer i : movieIdSet) {
			LOG.info( String.valueOf(i) );
		}
	}

}
