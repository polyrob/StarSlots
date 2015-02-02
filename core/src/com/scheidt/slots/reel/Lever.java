package com.scheidt.slots.reel;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.scheidt.slots.model.TestBox;

public class Lever extends Group {

	public Lever() {
		super();
		TestBox box = new TestBox();
		box.setPosition(1120, 600);
		this.addActor(box);
		
	}
	
}
