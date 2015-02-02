package com.scheidt.slots.reel;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.scheidt.slots.model.Star;

public class ReelItem extends Sprite {

	private Star person;
	
	public ReelItem(Texture texture) {
		super(texture);
		
	}

	public Star getPerson() {
		return person;
	}

	public void setPerson(Star person) {
		this.person = person;
	}
	
	

}
