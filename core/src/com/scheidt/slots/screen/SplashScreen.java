package com.scheidt.slots.screen;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.font.FontManager;
import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;
import com.scheidt.slots.reel.StarProvider;
import com.scheidt.slots.util.LoadData;

/**
 * Shows a splash image and moves on to the next screen.
 */
public class SplashScreen extends AbstractScreen {
	private Image splashImage;

	public SplashScreen(final GdxSlots game) {
		super(game);
		
		/* load data in seperate thread */
		Thread thread = new Thread() {
			public void run() {
				try {
					/* load map data */
					List<Star> people = LoadData.loadSerializedPeople();
					List<Movie> movies = LoadData.loadSerializedMovies();
					
					for (Star cast : people) {
						game.getCastMap().put(cast.getStarId(), cast);
					}
					Gdx.app.log("Splash load thread", "CAST map loaded successfully.");
					
					for (Movie movie : movies) {
						game.getMovieMap().put(movie.getMovieId(), movie);
					}
					Gdx.app.log("Splash load thread", "MOVIE map loaded successfully.");
					
				} catch (Exception e) {
					Gdx.app.error("Splash load thread", "Error loading data files!");
					System.exit(0);
				}
			}
		};
		thread.start();
    	
	}

	@Override
	public void show() {
		super.show();

		// start playing the menu music
//		game.getMusicManager().play(TyrianMusic.MENU);

		// retrieve the splash image's region from the atlas
		AtlasRegion splashRegion = getAtlas().findRegion("splash_title");

		Drawable splashDrawable = new TextureRegionDrawable(splashRegion);
		splashImage = new Image(splashDrawable, Scaling.stretch);
		Table table = super.getTable();
		table.add(splashImage).center();
		table.row();

		// configure the fade-in/out effect on the splash image
		splashImage.getColor().a = 0f;
		splashImage.addAction(sequence(fadeIn(0.75f), delay(0.75f), fadeOut(0.75f), new Action() {
			@Override
			public boolean act(float delta) {
				// the last action will move to the next screen
				game.setScreen(new MenuScreen(game));
				dispose();
				return true;
			}
		}));

	}
}