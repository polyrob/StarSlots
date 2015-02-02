package com.scheidt.slots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.debug.FPS;
import com.scheidt.slots.debug.Grid;
import com.scheidt.slots.debug.Memory;
import com.scheidt.slots.font.FontManager;
import com.scheidt.slots.listener.MiniGameListener;
import com.scheidt.slots.model.TestBox;
import com.scheidt.slots.pay.PayManager;
import com.scheidt.slots.reel.NameManager;
import com.scheidt.slots.reel.ReelManager;
import com.scheidt.slots.reel.StarProvider;

public class GameScreen extends AbstractScreen {

	ReelManager rm;

	/* Debug */
	private static final boolean DEBUG = true;
	private static final boolean DEBUG_GRID = false;

	Runtime runtime;
	long memory;

	public GameScreen(final GdxSlots game) {
		super(game);

		FontManager.init();
		game.setStarProvider(new StarProvider(game.getCastMap()));

		Image background = new Image(new Texture(Gdx.files.internal("textures/backgrounds/bkgd_game.png")));
		background.setFillParent(true);
		stage.addActor(background);
		background.toBack();

		rm = new ReelManager(game);
		stage.addActor(rm);
		Image frame = new Image(new Texture(Gdx.files.internal("textures/game_frame.png")));
		frame.setFillParent(true);
		stage.addActor(frame);

		final PayManager pm = new PayManager(game.getCastMap(), game.getMovieMap(), game.getSoundManager());
		stage.addActor(pm);
		rm.setPayManager(pm);

		final TestBox lever = new TestBox();
		lever.setPosition(1120, 600);
		lever.addListener(new DragListener() {

			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				if (event.getStageY() < 300) {
					lever.setY(268);
				} else {
					lever.setY(Math.round((event.getStageY() + 50 / 2) / 50) * 50 - 32);
				}

			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				super.dragStop(event, x, y, pointer);
				if (!rm.isSpinning() && event.getStageY() < 300) {
					rm.processSpin();
				}
				lever.setPosition(1120, 600);
			}
		});

		stage.addActor(lever);

		NameManager nm = new NameManager(game.getCastMap(), getSkin());
		stage.addActor(nm);
		rm.setNameManager(nm);
		pm.setNameManager(nm);

		TextButton startGameButton = new TextButton("Go to MiniGame Screen", getSkin());
		startGameButton.addListener(new MiniGameListener(game, this));

		getTable().add(startGameButton).uniform().fill().spaceBottom(10);
		getTable().row();
		
//		{
//			Label label = new Label("My Dialog", getSkin());
//			label.setWrap(true);
//			label.setFontScale(.8f);
//			label.setAlignment(Align.center);
//			Dialog dialog = new Dialog("", getSkin(), "dialog") {
//				protected void result(Object object) {
//					System.out.println("Chosen: " + object);
//				}
//			};
//
//			dialog.padTop(100).padBottom(50);
//			dialog.getContentTable().add(label).width(400).row();
//			dialog.getButtonTable().padTop(100);
//
//			TextButton dbutton = new TextButton("Yes", getSkin(), "default");
//			dialog.button(dbutton, true);
//
//			dbutton = new TextButton("No", getSkin(), "default");
//			dialog.button(dbutton, false);
//			dialog.key(Keys.ENTER, true).key(Keys.ESCAPE, false);
//			dialog.invalidateHierarchy();
//			dialog.invalidate();
//			dialog.layout();
//			dialog.show(stage);
//		}

		if (DEBUG_GRID) {
			Grid grid = new Grid(getShapeRenderer());
			stage.addActor(grid);
		}

		if (DEBUG) {
			Memory memory = new Memory(getSkin());
			FPS fps = new FPS(getSkin());

			getTable().add(fps.getFPSLabel()).left();
			getTable().row();
			getTable().add(memory.getMemoryLabel()).left();
			getTable().row();

			stage.addActor(fps);
			stage.addActor(memory);
			getTable().left().bottom();
		}

	}

}
