package com.scheidt.slots.debug;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Memory extends Group {
	
	private Label memoryLabel;
	private Runtime runtime;


	public Memory(Skin skin) {
		super();
		
		runtime = Runtime.getRuntime();
		memoryLabel = new Label("Memory", skin);
//		memoryLabel.setAlignment(Align.bottom | Align.left);

		addActor(memoryLabel);	
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		memoryLabel.setText("Memory: " + (runtime.totalMemory() - runtime.freeMemory()) / 1000 + "k");
	}

	public Label getMemoryLabel() {
		return memoryLabel;
	}
	
	

}

