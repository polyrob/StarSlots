package com.scheidt.slots.reel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.scheidt.slots.model.Star;

/*
    Class to load new Stars and have them available for new spins
 */
public class StarProvider {

	private final int POOL_MAX = 36;
	private final int POOL_MIN = 12;

	private Random randomizer = new Random();

	private List<String> starIdList;
	private Map<String, Star> starMap;

	private Queue<Image> starPool;
	private boolean loading = false;

	public StarProvider(Map<String, Star> map) {
		starMap = map;
		starIdList = new ArrayList<String>();
		starIdList.addAll(starMap.keySet());
		starPool = new LinkedList<Image>();

		load();
	}

	// public void loadNewStars(Reel reel) {
	//
	// while (reel.getChildren().size < 8) {
	// String newStarId = starIdList.get(randomizer.nextInt(starIdList.size()));
	// Star star = starMap.get(newStarId);
	//
	// }
	// }

	public Image getStarFromPool() {
		return starPool.poll();
	}

	public void replenishPool() {
		if (starPool.size() < POOL_MIN) {
			Gdx.app.log("StarProvider", "Pool is below minimum, loading...");
			new Thread(new Runnable() {
				@Override
				public void run() {
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							int loaded = load();
							Gdx.app.log("StarProvider", loaded + " loaded.");
						}
					});
				}
			}).start();

		}

	}

	
	private int load() {
		int count = 0;
		while (starPool.size() < POOL_MAX) {
			count++;
			String newStarId = starIdList.get(randomizer.nextInt(starIdList.size()));
			Star star = starMap.get(newStarId);
			Image img = new Image(star.getTextureFromDisk());
			img.setName(star.getStarId());
			img.setX(-500); // make it off screen in case it is drawn before positioned
			starPool.add(img);
		}
		loading = false;
		
		return count;
	}

}
