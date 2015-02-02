package com.scheidt.slots.reel;

import static com.badlogic.gdx.math.Interpolation.pow2;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


/*
 *  The Reel manages the symbols that spin. Each reel will have 3 sprites that reference random people.
 *  During spin, each sprite will move down to simulate spinning motion.
 *  If the bottom sprite goes below the bottow view line, it will get a new person/texture
 *  and move back up to the top view line.
 */

public class Reel extends Group {

	StarProvider sp;

	private static Random randomizer = new Random();
	private static final float MAX_ROT_SPEED = 2000f + 100 * randomizer.nextFloat();
	private float currSpeed = 0f;
	private boolean spinning = false;

	/* main reel position */
	private Rectangle rect;

	private List<Image> items;
	private String currentWinnerId;

	// private float bounce;

	public Reel(Rectangle rect, StarProvider sp) {
		this.rect = rect;
		this.sp = sp;

		items = new ArrayList<Image>();

		/*
		 * get people loaded into reelItems. 3 are shown, but we need +1 for
		 * wrapping and x2 so the next spin is already loaded
		 */

		for (int i = 0; i < 4; i++) {
			Image item = sp.getStarFromPool();
			item.setPosition(rect.x, rect.y + (i * rect.height / 3));
			item.setSize(rect.width, rect.height / 3);
			items.add(item);
			addActor(item);
		}
		Gdx.app.log("Reel", "init load complete. Size is: " + items.size());
		currentWinnerId = items.get(1).getName();
	}

	public void drawNames(SpriteBatch batch) {
		if (!spinning) {
			// FontManager.font24.draw(batch,
			// reelItems.get(1).getPerson().getName(), reelItems.get(1).getX(),
			// reelItems.get(1).getY()+20);
			// FontManager.font24.draw(batch, currentWinner.getName(), rect.x,
			// rect.y+220);
			// FontManager.font_names_bk.drawWrapped(batch,
			// currentWinner.getName(), rect.x, 350+MathUtils.cos(bounce)*2,
			// 200, HAlignment.CENTER);
			// FontManager.font_names.drawWrapped(batch,
			// currentWinner.getName(), rect.x, 350+MathUtils.cos(bounce)*2,
			// 202, HAlignment.CENTER);

			// if (currentWinner.getName().length() > 14) {
			// FontManager.font_names.drawWrapped(batch,
			// currentWinner.getName(), rect.x-5, 360, 240, HAlignment.CENTER);
			// } else {
			// FontManager.font_names.drawWrapped(batch,
			// currentWinner.getName(), rect.x-5, 350, 220, HAlignment.CENTER);
			// }

		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (spinning) {
			if (currSpeed < MAX_ROT_SPEED)
				currSpeed += 50f;
			for (int i = 0; i < 4; i++) {
				Image item = items.get(i);
				// item.translateY(currSpeed * delta);
				item.moveBy(0, currSpeed * delta);
				if (item.getY() > rect.y + rect.height) {
					/*
					 * need to recycle and get new person. for now, just use the
					 * 4
					 */
					item.setY(rect.y - item.getHeight());
				}
			}
		}

	}

	public void startSpin() {
		spinning = true;
//		Gdx.app.log("Reel", "start spin... Size is: " + items.size());
//		spinStopTime = stopTime;
	}

	public String stop() {
		spinning = false;
		currSpeed = 0f;

		// // remove first 4 and add to disposal thread

		for (int i = 0; i < 4; i++) {
			Image img = sp.getStarFromPool();
			addActor(img);
			items.add(img);
			this.removeActor(items.remove(0));
			// the list will shift, its always the 0 person
			// disposeList.add(item);
		}

		// now 0-3 should be new people, set them in place
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setPosition(rect.x, rect.y + (i * rect.height / 3));
			items.get(i).setSize(rect.width, rect.height / 3);
		}
//		items.get(1).addAction(forever(sequence(scaleTo(1.1f, 1.1f, 1f), scaleTo(1, 1, 1f), delay(0.2f))));
//		items.get(1).addAction(forever(sequence(color(Color.RED, 1f, pow2 ), color(Color.WHITE, 1f, pow2 ))));
//		items.get(1).addAction(forever(sequence(com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(0, 2, 1, pow2), com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy(0, -2, 1, pow2))));
		/* payline person always in position 1 */
		currentWinnerId = items.get(1).getName();
		return currentWinnerId;
	}

	public List<Image> getItems() {
		return items;
	}


	public boolean isSpinning() {
		return spinning;
	}


}
