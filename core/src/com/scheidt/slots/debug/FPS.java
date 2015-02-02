package com.scheidt.slots.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class FPS extends Group {
	
	private Label fpsLabel;

	public FPS(Skin skin) {
		super();
		
		fpsLabel = new Label("FPS", skin);
//		fpsLabel.setAlignment(Align.left);
		addActor(fpsLabel);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		fpsLabel.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
	}
	
	public Label getFPSLabel() {
		return fpsLabel;
	}
	

}

