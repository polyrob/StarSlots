package com.scheidt.slots.api;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.scheidt.slots.model.Star;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbPeople;
import info.movito.themoviedbapi.model.Artwork;
import info.movito.themoviedbapi.model.people.PersonCredit;
import info.movito.themoviedbapi.model.people.PersonCredits;
import info.movito.themoviedbapi.model.people.PersonPeople;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

public class ActorUtil {

	private TmdbApi tmdb;

	private static final String IMG_BASE_URL = "http://image.tmdb.org/t/p/original";
	private static final int MIN_MATCHES = 1;

	private static Logger LOG = Logger.getLogger("com.hwslots");

	public ActorUtil(TmdbApi tmdb) {
		this.tmdb = tmdb;
	}

	public List<String> getActorIDsFromFile(String filename) throws Exception {
		List<String> actorIdList;

		/* read actorIDs from disk */
		File file = new File(filename);
		actorIdList = FileUtil.getListFromFile(file);
		LOG.info("Actors read from file = " + actorIdList.size());
		return actorIdList;
	}

	public void getActorListFromRemote(List<Star> actorList, List<String> actorIdList, int count) throws Exception {
		LOG.info("Getting actor data remotely... ");

		int max = 0;
		if (count < actorIdList.size() && count != 0) {
			max = count;
		} else {
			max = actorIdList.size();
		}

		for (int i = 0; i < max && i < count; i++) {
			if ((i + 1) % 10 == 0 && i != 0) {
				LOG.info("Processing... " + (i + 1) + " out of " + max);
			}

			actorList.add(getRemoteActorData(Integer.parseInt(actorIdList.get(i))));
		}
		LOG.info("Data obtained for " + actorList.size() + " actors.");

	}

	public Star getRemoteActorData(int id) throws Exception {
		TmdbPeople p = tmdb.getPeople();
		
		PersonPeople person = p.getPersonInfo(id);

		LOG.finest("Name: " + person.getName());

		PersonCredits pcs = tmdb.getPeople().getPersonCredits(id);
		List<PersonCredit> cast = pcs.getCast();

		List<String> filmography = new ArrayList<String>();
		for (PersonCredit personCredit : cast) {
			filmography.add(String.valueOf(personCredit.getMovieId()));
		}
		LOG.finest("Filmography: " + filmography.size() + " movies.");

		Star actor = new Star();
		actor.setName(person.getName());
		actor.setFilms(filmography);
		actor.setStarId(String.valueOf(id));
		String filename = person.getName().replaceAll("\\W+", "");
		
		String diskPath = "img/" + filename + ".jpg";
		
		actor.setImgLoc(diskPath);

		File imgFile = new File(diskPath);

		if (!imgFile.isFile()) {
			Artwork artwork = p.getPersonImages(id).get(0);
			URL imgUrl = new URL(IMG_BASE_URL + artwork.getFilePath());
			FileUtils.copyURLToFile(imgUrl, imgFile);
		} else {
			LOG.finest("Image already downloaded for " + person.getName() + ". Last modified: " + imgFile.lastModified());
		}

		return actor;
	}

	public Map<String, Integer> optimizeFilmographies(List<Star> actorList) {
		LOG.info("Starting Optimization for " + actorList.size() + " people.");
		int total = 0;
		int removes = 0;
		int matches = 0;
		Set<String> validMovieIdSet = new HashSet<String>();
		long startTime = System.currentTimeMillis();

		/* create HUGE set of ALL movies our actors are in */
		Map<String, Integer> creditsMap = new HashMap<String, Integer>();

		/* add everyone's films to this set */
		for (Star currentActor : actorList) {
			List<String> movies = currentActor.getFilms();

			for (String credit : movies) {
				Integer count = creditsMap.get(credit);
				if (count == null) {
					creditsMap.put(credit, 1);
				} else {
					creditsMap.put(credit, ++count);
				}
			}
		}
		LOG.info("Total credits for all actors: " + creditsMap.entrySet().size());

		{
			Iterator<Map.Entry<String, Integer>> iter = creditsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Integer> entry = iter.next();
				if (entry.getValue() < 2) {
					iter.remove();
				}
			}
		}
		
		/*
		 * Now with all movies, go through again and remove any without matches
		 * in the set
		 */
		Iterator<Star> actor_itr = actorList.iterator();
		while (actor_itr.hasNext()) { // loop for each actor
			Star currentActor = actor_itr.next();
			List<String> movies = currentActor.getFilms();

			Iterator<String> credits_itr = movies.iterator();
			while (credits_itr.hasNext()) { // loop for each movie credit in
											// current actor
				String curentMovieID = credits_itr.next();
				total++;

				if (creditsMap.get(curentMovieID) != null) {
					matches++;
				} else {
					removes++;
					credits_itr.remove();
				}

			}
		}

		/*
		 * if any actor has less than X matches, they are too rare. Remove the
		 * whole actor
		 */
		Iterator<Star> iter = actorList.iterator();
		while (iter.hasNext()) {
			Star actor = iter.next();
			if (actor.getFilms().size() < MIN_MATCHES) {
				LOG.info("  > " + actor.getName() + " has fewer than " + MIN_MATCHES + " common films, removing...");
				iter.remove();
			}
		}

		LOG.info("Optimization Completed.\n >Total credits processed: " + total + "\n >Matched:   " + matches + "\n >Removes:   " + removes
				+ "\n >Matchable movies:   " + creditsMap.size() + "\n >Ratio:     " + ((float) matches / (float) total) * 100 + "%"
				+ "\n >Runtime:   " + (System.currentTimeMillis() - startTime) + "ms.");

		return creditsMap;
	}

}
