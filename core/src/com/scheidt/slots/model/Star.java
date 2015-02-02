package com.scheidt.slots.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Star implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528378322159663308L;
	private String 	name;
	private String	starId;
	private String 	imgLoc;
	private List<String> films;
	private Map<String, Integer> bonuses;

	/* Empty constructor for reel init */
	public Star() {
		bonuses = new HashMap<String, Integer>();
	}

	public Star(String name, String imgFile, List<String> films) {
		super();
		this.name = name;
		this.imgLoc = imgFile;
		this.films = films;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStarId() {
		return starId;
	}

	public void setStarId(String starId) {
		this.starId = starId;
	}

	public String getImgLoc() {
		return imgLoc;
	}

	public void setImgLoc(String imgLoc) {
		this.imgLoc = imgLoc;
	}

	public List<String> getFilms() {
		return films;
	}

	/**
	 * @param films
	 *            the films to set
	 */
	public void setFilms(List<String> films) {
		this.films = films;
	}
	
	public Map<String, Integer> getBonuses() {
		return bonuses;
	}
	public void setBonuses(Map<String, Integer> bonuses) {
		this.bonuses = bonuses;
	}

	public Texture getTextureFromDisk() {
		try {
			Texture fromDisk = new Texture(Gdx.files.internal(imgLoc));
			return fromDisk;
		} catch (Exception e) {
			System.out.println("ERROR! " + getImgLoc());
		}
		return null;
	}
}
