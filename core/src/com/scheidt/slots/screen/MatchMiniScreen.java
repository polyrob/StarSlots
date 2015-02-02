package com.scheidt.slots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * This mini game will walk a tree of stars with users having answer a question
 * for the target star. It will be started with a target from a spin and each
 * level will be determined procedurally based on options for that new target star.
 * 
 * @author NewRob
 * 
 */

/*
 *   1. Starting with target star, randomly select a movie from their filmography.
 *   2. Randomly select 1 or 2 stars from that movies cast list.
 *   3. Randomly select 1 or 2 stars at random (verify they are NOT in movie with target, or select again).
 *   4. 
 *   
 */

public class MatchMiniScreen extends AbstractScreen {

	// StarProvider sp;
	private List<String> starIdList;
	private Map<String, Star> starMap;
	private Image target;
	private Image a, b, c;
	
	private Map<String, Star> castMap;
	private Map<String, Movie> movieMap;
	
	private String correctAnswer;

	private Random randomizer = new Random();
	

	public MatchMiniScreen(final GdxSlots game, final AbstractScreen screen) {
		super(game);

		// sp = game.getStarProvider();
		starMap = game.getCastMap();
		starIdList = new ArrayList<String>();
		starIdList.addAll(starMap.keySet());
		castMap = game.getCastMap();
		movieMap = game.getMovieMap();
		// Table table = super.getTable();
		// // table.add("Match Mini Game Screen").spaceBottom(50);
		// // table.row();
		//
		// VerticalGroup gV = new VerticalGroup().space(5).pad(5);
		// HorizontalGroup gH = new HorizontalGroup().space(5).pad(5);
		//
		// AtlasRegion splashRegion = getAtlas().findRegion("mask_happy");
		// Drawable splashDrawable = new TextureRegionDrawable(splashRegion);
		// target = new Image(splashDrawable);
		// // gV.addActor(target);
		//
		// AtlasRegion splashRegion2 = getAtlas().findRegion("mask_sad");
		// Drawable splashDrawable2 = new TextureRegionDrawable(splashRegion2);
		// a = new Image(splashDrawable2);
		// b = new Image(splashDrawable2);
		// c = new Image(splashDrawable2);
		// gH.addActor(a);
		// gH.addActor(b);
		// gH.addActor(c);
		// gV.addActor(gH);
		// table.add(gV);

		setStars();
		layoutGameObjects();

		stage.addActor(target);
		stage.addActor(a);
		stage.addActor(b);
		stage.addActor(c);

		// register the button "start game"
		TextButton startGameButton = new TextButton("go back", getSkin());
		startGameButton.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(this.getClass().getSimpleName(), "Touch Down");
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.setScreen(screen);
				dispose();
			}

		});

		stage.addActor(startGameButton);

		// gV.addActor(startGameButton); //.uniform().fill().spaceBottom(10);

	}

	private void layoutGameObjects() {
		target.setPosition(Gdx.graphics.getWidth() / 2 - target.getWidth() / 2, Gdx.graphics.getHeight() / 2 - target.getHeight() / 2 + 150);
		a.setPosition(Gdx.graphics.getWidth() / 2 - target.getWidth() / 2 - 300, Gdx.graphics.getHeight() / 3 - target.getHeight() / 2);
		b.setPosition(Gdx.graphics.getWidth() / 2 - target.getWidth() / 2, Gdx.graphics.getHeight() / 3 - target.getHeight() / 2);
		c.setPosition(Gdx.graphics.getWidth() / 2 - target.getWidth() / 2 + 300, Gdx.graphics.getHeight() / 3 - target.getHeight() / 2);
	}

	public void setStars() {
		// Star star = starMap.get("10297");
		// Drawable targetDrawable = new TextureRegionDrawable(new
		// TextureRegion(star.getTextureFromDisk()));
		// target.setDrawable(targetDrawable);
		target = getStar(); // this needs to be starting person from main game screen.
		
		/* pick random movie from target */
		Star star = castMap.get(target.getName());
		List<String> starMovies = star.getFilms();
		
		String tryMovie = starMovies.get(randomizer.nextInt(starMovies.size()));
		Movie m = movieMap.get(tryMovie);
		

	
		int winner = 0;
		do {
			winner =  m.getCast().get(randomizer.nextInt(m.getCast().size()));
		} while (String.valueOf(winner).equals(star.getStarId()));
		


		
		a = getStar();
		b = getStar();
		c = getStar();
	}

	private Image getStar() {
		String starStr = starIdList.get(randomizer.nextInt(starIdList.size()));
		Star star = starMap.get(starStr);
		final Image img = new Image(star.getTextureFromDisk());
		img.setName(star.getStarId());
		img.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log(this.getClass().getSimpleName(), "Touch Down, " + img.getName());
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

			}

		});
		return img;
	}

	private void setChoicesForTarget() {
		
	}

	public void setTarget(String starId) {
		// Star star = starMap.get(starId);
		// Image img = new Image(star.getTextureFromDisk());
		// img.setName(star.getStarId());
		// img.setX(target.getX());
		// img.setY(target.getY());
		// target = img;
	}

}
