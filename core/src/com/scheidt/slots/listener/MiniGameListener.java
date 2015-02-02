package com.scheidt.slots.listener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.screen.AbstractScreen;
import com.scheidt.slots.screen.MatchMiniScreen;

public class MiniGameListener extends InputListener {

	GdxSlots game;
	AbstractScreen screen;
	
	public MiniGameListener(GdxSlots game, AbstractScreen screen) {
		super();
		this.game = game;
		this.screen = screen;
	}

	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }

    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
    	/* return to still active slot screen */
    	MatchMiniScreen minigame = new MatchMiniScreen(game, screen);
    	minigame.setTarget("10297"); // McConaughey
        game.setScreen( minigame );
    }

}
