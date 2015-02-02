package com.scheidt.slots.reel;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.scheidt.slots.model.Star;

public class NameManager extends Group {

	Map<String, Star> map;
	// NameLabel label1, label2, label3;
	List<NameLabel> nameList;

	public NameManager(Map<String, Star> map, Skin skin) {
		this.map = map;
		NameLabel label0 = new NameLabel("", skin, 448, 200);
		NameLabel label1 = new NameLabel("", skin, 710, 200);
		NameLabel label2 = new NameLabel("", skin, 980, 200);
		// NameLabel label0 = new NameLabel("", skin, 346, 200);
		// NameLabel label1 = new NameLabel("", skin, 610, 200);
		// NameLabel label2 = new NameLabel("", skin, 878, 200);

		nameList = new ArrayList<NameLabel>();
		nameList.add(label0);
		nameList.add(label1);
		nameList.add(label2);

		addActor(label0);
		addActor(label1);
		addActor(label2);
	}

	public void setStarName(String starId, int index) {
		String name = map.get(starId).getName();
		nameList.get(index).setText(name);

		// nameList.get(index).addAction(moveBy(0f, 200f, 0.2f));

		nameList.get(index).addAction(parallel(scaleTo(1, 1, 0.5f, elasticOut), fadeIn(0.5f)));

		// nameList.get(index).addAction(sequence(scaleTo(2f, 2f, 0.3f, pow2),
		// scaleTo(1, 1, 1f, elasticOut), run(new Runnable() {
		// @Override
		// public void run() {
		// Gdx.app.log("addAction", "Action complete");
		// }
		//
		// })));

		// nameList.get(index).addAction(parallel(fadeIn(0.5f, pow2), scaleTo(1,
		// 1, 0.3f, pow2), run(new Runnable() {
		// @Override
		// public void run() {
		// Gdx.app.log("addAction", "Action complete");
		// }
		//
		// })));

	}

	public void clearStarNames() {
		for (NameLabel label : nameList) {
			label.setText("");
			label.setScale(5f, 5f);
			label.setColor(1, 1, 1, 0);
			// label.setY(0);
		}
	}

	public void setMatchedNames(boolean a, boolean b, boolean c) {
		nameList.get(0).setColor(1, (a) ? 0 : 1, 1, 1);
		nameList.get(1).setColor(1, (b) ? 0 : 1, 1, 1);
		nameList.get(2).setColor(1, (c) ? 0 : 1, 1, 1);
		
		if (a) nameList.get(0).addAction(sequence(scaleTo(1.2f, 1.2f, 0.2f), scaleTo(1, 1, 0.5f, elasticOut)));
		if (b) nameList.get(1).addAction(sequence(scaleTo(1.2f, 1.2f, 0.2f), scaleTo(1, 1, 0.5f, elasticOut)));
		if (c) nameList.get(2).addAction(sequence(scaleTo(1.2f, 1.2f, 0.2f), scaleTo(1, 1, 0.5f, elasticOut)));

	}

}
