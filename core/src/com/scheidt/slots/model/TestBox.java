package com.scheidt.slots.model;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TestBox extends Image {
	
	public TestBox() {
		super(getPixMap());
	}
	
	public static Texture getPixMap() {
		Pixmap pixmap = new Pixmap( 64, 64, Format.RGBA8888 );
		pixmap.setColor( 0, 1, 0, 0.75f );
		pixmap.fillCircle( 32, 32, 32 );
		Texture pixmaptex = new Texture( pixmap );
		pixmap.dispose();
		return pixmaptex;
	}
	

}
