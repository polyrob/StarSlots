package com.scheidt.slots.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1835258448476243676L;
	private String movieId;
	private String title;
	private float popularity;
	private float userRating;
	private float voteAverage;
	private long budget;
	private long revenue;
	private String status;
	private String releaseDate;
	private List<Integer> cast;
	private Map<String, Integer> bonuses;

	public Movie() {
		bonuses = new HashMap<String, Integer>();
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public float getPopularity() {
		return popularity;
	}

	public void setPopularity(float popularity) {
		this.popularity = popularity;
	}

	public long getBudget() {
		return budget;
	}

	public void setBudget(long budget) {
		this.budget = budget;
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Integer> getCast() {
		return cast;
	}

	public void setCast(List<Integer> cast) {
		this.cast = cast;
	}

	public Map<String, Integer> getBonuses() {
		return bonuses;
	}

	public void setBonuses(Map<String, Integer> bonuses) {
		this.bonuses = bonuses;
	}

	public float getUserRating() {
		return userRating;
	}

	public void setUserRating(float userRating) {
		this.userRating = userRating;
	}

	public float getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(float voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getYear() {
		return releaseDate.substring(0, 4);
	}

}
