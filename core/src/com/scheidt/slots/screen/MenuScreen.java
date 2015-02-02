package com.scheidt.slots.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.sound.SlotSound;

public class MenuScreen extends AbstractScreen {
	public MenuScreen(GdxSlots game) {
		super(game);
	}

	@Override
	public void show() {
		super.show();
		Image background = new Image(new Texture (Gdx.files.internal("textures/backgrounds/bkgd_menu.png")));
		background.setFillParent(true);
		stage.addActor(background);
		background.toBack();

		// retrieve the default table actor
		Table table = super.getTable();
		table.add("Hollywood Slots for Android!").spaceBottom(50);
		table.row();

		// register the button "start game"
		TextButton startGameButton = new TextButton("Start game", getSkin());
		startGameButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Gdx.app.log( this.getClass().getSimpleName(), "Touch Down" );
		        return true;
		    }

		    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
		        System.out.println("Touch Up");

		        game.setScreen( new GameScreen( game ) );
		        dispose();
		    }
		    
		    
			
		});

		table.add(startGameButton).uniform().fill().spaceBottom(10);
		table.row();

		// register the button "options"
		TextButton optionsButton = new TextButton("Options", getSkin());
//		optionsButton.addListener(new DefaultActorListener() {
//			@Override
//			public void touchUp(ActorEvent event, float x, float y, int pointer, int button) {
//				super.touchUp(event, x, y, pointer, button);
//				game.getSoundManager().play(TyrianSound.CLICK);
//				game.setScreen(new OptionsScreen(game));
//			}
//		});
		table.add(optionsButton).uniform().fill().spaceBottom(10);
		table.row();

		// register the button "high scores"
		TextButton highScoresButton = new TextButton("High Scores", getSkin());
//		highScoresButton.addListener(new DefaultActorListener() {
//			@Override
//			public void touchUp(ActorEvent event, float x, float y, int pointer, int button) {
//				super.touchUp(event, x, y, pointer, button);
//				game.getSoundManager().play(TyrianSound.CLICK);
//				game.setScreen(new HighScoresScreen(game));
//			}
//		});
		table.add(highScoresButton).uniform().fill();
		
		game.getSoundManager().play(SlotSound.SUSPENSE);
	}
}