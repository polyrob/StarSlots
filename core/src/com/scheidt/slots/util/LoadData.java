package com.scheidt.slots.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class LoadData {

	// private final static String DATAFILE_ACTORS = "data/actors.data";
	private final static String DATAFILE_ACTORS = "data/actors.data";
	private final static String DATAFILE_MOVIES = "data/movies.data";

	@SuppressWarnings("unchecked")
	public static ArrayList<Star> loadSerializedPeople() throws Exception {

		FileHandle handle = Gdx.files.internal(DATAFILE_ACTORS);
		Gdx.app.log("LoadData", "Loading serialized actors from disk...");

		ObjectInputStream obj_in = new ObjectInputStream(handle.read());

		// Read an object
		Object obj = obj_in.readObject();
		obj_in.close();
		Gdx.app.log("LoadData", "Loaded.");

		if (obj instanceof ArrayList<?>) {
			return (ArrayList<Star>) obj;
		} else {
			Gdx.app.error("LoadData", "File could not be deserialized! " + handle.path());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Movie> loadSerializedMovies() throws Exception {

		FileHandle handle = Gdx.files.internal(DATAFILE_MOVIES);
		Gdx.app.log("LoadData", "Loading serialized movies from disk...");

		ObjectInputStream obj_in = new ObjectInputStream(handle.read());

		// Read an object
		Object obj = obj_in.readObject();
		obj_in.close();
		Gdx.app.log("LoadData", "Loaded.");

		if (obj instanceof ArrayList<?>) {
			return (ArrayList<Movie>) obj;
		} else {
			Gdx.app.error("LoadData", "File could not be deserialized! " + handle.path());
			return null;
		}

	}
	
	public static ArrayList<Star> loadPeople() throws Exception {

		File file = new File(DATAFILE_ACTORS);
		System.out.println(DATAFILE_ACTORS);

		// this is the data we want

		JSONObject jData;

		jData = getJSONForFile(file);
		JSONArray jRoot = jData.getJSONArray("data");

		ArrayList<Star> data = new ArrayList<Star>();

		for (int i = 0; i < jRoot.length(); i++) {
			String name = jRoot.getJSONObject(i).getString("name");
			String uriPhoto = jRoot.getJSONObject(i).getString("imgURI");
			JSONArray movies = jRoot.getJSONObject(i).getJSONArray("movies");

			List<String> filmography = new ArrayList<String>();
			for (int j = 0; j < movies.length(); j++) {
				String title = (String) movies.get(j);
				filmography.add(title);
			}

			Star p = new Star(name, uriPhoto, filmography);

			data.add(p);
		}

		return data;
	}

	private static JSONObject getJSONForFile(File f) throws Exception {

		FileHandle handle = Gdx.files.internal("data/data.json");
		String data = handle.readString();

		// try parse the string to a JSON object
		JSONObject jObj = new JSONObject(data);

		// return JSON String
		return jObj;
	}

}
