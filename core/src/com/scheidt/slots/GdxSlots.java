package com.scheidt.slots;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;
import com.scheidt.slots.reel.StarProvider;
import com.scheidt.slots.screen.SplashScreen;
import com.scheidt.slots.sound.SoundManager;

import java.util.HashMap;
import java.util.Map;

public class GdxSlots extends Game {
//	SpriteBatch batch;
//	Texture img;
//
//	@Override
//	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//	}
//
//	@Override
//	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
//	}

	// constant useful for logging
	public static final String LOG = GdxSlots.class.getSimpleName();

	// whether we are in development mode
	public static final boolean DEV_MODE = true;

	private Map<String, Star> castMap;
	private Map<String, Movie> movieMap;
	private StarProvider sp;


	// services
	// private PreferencesManager preferencesManager;
	// private ProfileManager profileManager;
	// private LevelManager levelManager;
	// private MusicManager musicManager;
	private SoundManager soundManager;

	public GdxSlots() {
		castMap = new HashMap<String, Star>();
		movieMap = new HashMap<String, Movie>();
	}

	// Services' getters
	// public PreferencesManager getPreferencesManager()
	// {
	// return preferencesManager;
	// }
	//
	// public ProfileManager getProfileManager()
	// {
	// return profileManager;
	// }
	//
	// public LevelManager getLevelManager()
	// {
	// return levelManager;
	// }
	//
	// public MusicManager getMusicManager()
	// {
	// return musicManager;
	// }
	//
	public SoundManager getSoundManager() {
		return soundManager;
	}

	// Game-related methods

	@Override
	public void create() {
		Gdx.app.log(GdxSlots.LOG, "Creating game on " + Gdx.app.getType());

		// // create the preferences manager
		// preferencesManager = new PreferencesManager();
		//
		// // create the music manager
		// musicManager = new MusicManager();
		// musicManager.setVolume( preferencesManager.getVolume() );
		// musicManager.setEnabled( preferencesManager.isMusicEnabled() );
		//
		// create the sound manager
		soundManager = new SoundManager();
//		 soundManager.setVolume( preferencesManager.getVolume() );
//		 soundManager.setEnabled( preferencesManager.isSoundEnabled() );
		soundManager.setVolume(1f);
		soundManager.setEnabled(true);
		//
		// // create the profile manager
		// profileManager = new ProfileManager();
		// profileManager.retrieveProfile();
		//
		// // create the level manager
		// levelManager = new LevelManager();

		// create the helper objects
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		Gdx.app.log(GdxSlots.LOG, "Resizing game to: " + width + " x " + height);

		// show the splash screen when the game is resized for the first time;
		// this approach avoids calling the screen's resize method repeatedly
		if (getScreen() == null) {
			setScreen(new SplashScreen(this));
		}
	}

	// @Override
	// public void pause() {
	// super.pause();
	// Gdx.app.log(GdxSlots.LOG, "Pausing game");
	//
	// // persist the profile, because we don't know if the player will come
	// // back to the game
	// // profileManager.persist();
	// }

	// @Override
	// public void resume() {
	// super.resume();
	// Gdx.app.log(GdxSlots.LOG, "Resuming game");
	// }

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
		Gdx.app.log(GdxSlots.LOG, "Setting screen: " + screen.getClass().getSimpleName());
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(GdxSlots.LOG, "Disposing game");

		// dipose some services
		// musicManager.dispose();
		// soundManager.dispose();
	}

	public Map<String, Star> getCastMap() {
		return castMap;
	}

	public Map<String, Movie> getMovieMap() {
		return movieMap;
	}

	public StarProvider getStarProvider() {
		return sp;
	}

	public void setStarProvider(StarProvider sp) {
		this.sp = sp;
	}


}
