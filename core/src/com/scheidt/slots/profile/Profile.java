package com.scheidt.slots.profile;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.OrderedMap;


public class Profile implements Serializable {
	private int currentScore;
	private Map<Integer, Integer> highScores;

	
	public Profile() {
		highScores = new HashMap<Integer, Integer>();
	}
	
	// Serializable implementation

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		// read the some basic properties
		currentScore = json.readValue("currentScore", Integer.class, jsonData);

		// libgdx handles the keys of JSON formatted HashMaps as Strings, but we
		// want it to be an integer instead (levelId)
		Map<String, Integer> highScores = json.readValue("highScores", HashMap.class, Integer.class, jsonData);
		for (String highScoreStr : highScores.keySet()) {
			int score = Integer.valueOf(highScoreStr);
			Integer highScore = highScores.get(highScoreStr);
			this.highScores.put(score, highScore);
		}

	}

	@Override
	public void write(Json json) {
		json.writeValue("currentScore", currentScore);
		json.writeValue("highScores", highScores);
	}

}