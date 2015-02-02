package com.scheidt.slots.reel;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class NameLabel extends Label {
	
	private static final int WIDTH = 200;
	
	private float permX;


	public NameLabel(CharSequence text, Skin skin, float x, float y) {
		super(text, skin);
		setX(x);
		setY(y);
		permX = x;
		this.setWidth(WIDTH);
		this.setAlignment(Align.center);
		this.setWrap(true);
	}


	@Override
	public void setScale(float scaleX, float scaleY) {
		// TODO Auto-generated method stub
		super.setScale(scaleX, scaleY);
		setWidth(WIDTH*scaleX);
		setX(permX - (WIDTH*scaleX)/2 );
		setFontScale(scaleX, scaleY);
	}


}
