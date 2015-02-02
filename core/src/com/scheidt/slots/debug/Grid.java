package com.scheidt.slots.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Grid extends Actor {
	
	private ShapeRenderer sr;
	
	private int width, height;
	
	public Grid (ShapeRenderer sr) {
		this.sr = sr;
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
	}

	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		// TODO Auto-generated method stub
//		super.draw(batch, parentAlpha);
		batch.end();
		
		sr.begin(ShapeType.Line);
		for (int x = 0; x < width; x += 100) {
			sr.line(x, 0, x, height);
		}
		for (int y = 0; y < height; y += 100) {
			sr.line(0, y, width, y);
		}
		sr.end();
		
		batch.begin();
	}

	
	
}
