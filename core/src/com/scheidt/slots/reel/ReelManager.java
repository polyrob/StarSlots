package com.scheidt.slots.reel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.scheidt.slots.GdxSlots;
import com.scheidt.slots.pay.PayManager;
import com.scheidt.slots.screen.MenuScreen;
import com.scheidt.slots.sound.SlotSound;
import com.scheidt.slots.sound.SoundManager;

import java.util.ArrayList;
import java.util.List;

public class ReelManager extends Group {

	private static final Rectangle LEFT_REEL = new Rectangle(350, -55, 180, 810);
	private static final Rectangle MID_REEL = new Rectangle(620, -55, 180, 810);
	private static final Rectangle RIGHT_REEL = new Rectangle(890, -55, 180, 810);

	private GdxSlots game;

	private Reel a, b, c;
	private List<Reel> reels;
	private PayManager pm;
	private SoundManager sm;
	private StarProvider sp;
	private NameManager nm;

	private boolean spinning = false;

	public ReelManager(GdxSlots game) {
		this.game = game;

		sp = game.getStarProvider();
		sm = game.getSoundManager();

		reels = new ArrayList<Reel>();
		a = new Reel(LEFT_REEL, sp);
		b = new Reel(MID_REEL, sp);
		c = new Reel(RIGHT_REEL, sp);
		reels.add(a);
		reels.add(b);
		reels.add(c);
		addActor(a);
		addActor(b);
		addActor(c);

		// pm = new PayManager(game.getCastMap(), game.getMovieMap(),
		// game.getSoundManager());
		// addActor(pm);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public boolean isSpinning() {
		return spinning;
	}

	public void processSpin() {
		spinning = true;

		sp.replenishPool();
		final int wager = pm.payForSpin();
		if (wager <= 0)
			return;
		sm.play(SlotSound.PULL);
		pm.clearScores();
		nm.clearStarNames();

		Thread thread = new Thread() {
			public void run() {

				/* start them spinning */
				for (Reel reel : reels) {
					reel.startSpin();
				}

				try {
					Thread.sleep(800);
					List<String> winners = new ArrayList<String>();

					for (int i = 0; i < reels.size(); i++) {
						Thread.sleep(400);
						sm.play(SlotSound.STOP);
						String winnerId = reels.get(i).stop();
						nm.setStarName(winnerId, i);
						winners.add(winnerId);

					}
					spinning = false;
					Thread.sleep(500);
					/* tell PayManager what's up, bitch */
					pm.calcPayouts(winners, wager);
					if (pm.getScore() <= 0) {
						Gdx.app.error("ReelManager", "Player looses. No money.");
						sm.play(SlotSound.EVIL_LAUGH);
						
						new Thread(new Runnable() {
							@Override
							public void run() {
								// post a Runnable to the rendering thread that
								// processes the result
								Gdx.app.postRunnable(new Runnable() {
									@Override
									public void run() {
										try {
											Thread.sleep(2500);
											game.setScreen(new MenuScreen(game));
										} catch (InterruptedException e) {
											e.printStackTrace();
										}

									}
								});
							}
						}).start();

					}

				} catch (Exception e) {
					Gdx.app.error("ReelManager", "Error processing spin. " + e.getStackTrace());
				}

			}
		};
		thread.start();

	}

	public void setPayManager(PayManager pm) {
		this.pm = pm;
	}

	public void setNameManager(NameManager nm) {
		this.nm = nm;
	}

}
