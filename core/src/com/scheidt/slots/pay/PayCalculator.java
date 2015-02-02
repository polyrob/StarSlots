package com.scheidt.slots.pay;

import com.badlogic.gdx.Gdx;
import com.scheidt.slots.model.Movie;

public class PayCalculator {

	public static int getPay(Movie m, int value) {
		float multiplier = 0.2f;

		/* popularity bonus */
		if (m.getPopularity() > 10) {
			multiplier *= 1.5f;
			Gdx.app.log("PayCalculator " + m.getTitle(), "Popularity bonus, x" + 1.5f);
		}

		/* rating bonus */
		if (m.getVoteAverage() > 7.0f) {
			multiplier *= 1.5f;
			Gdx.app.log("PayCalculator " + m.getTitle(), "Good rating bonus, x" + 1.5f);
		}

		/* big budget bonus */
		if (m.getBudget() > 100000000L) {
			multiplier *= 1.5f;
			Gdx.app.log("PayCalculator " + m.getTitle(), "Big Budget bonus, x" + 1.5f);
		}

		/* starry bonus */
		if (m.getCast().size() > 5) {
			multiplier *= 1.5f;
			Gdx.app.log("PayCalculator " + m.getTitle(), "Starry bonus, x" + 1.5f);
		}

		if (m.getRevenue() > 0) {
			float roi = ((float) m.getRevenue() / (float) m.getBudget());

			if (roi > 20) {
				/* ROI bonus */
				multiplier *= 1.5f;
				Gdx.app.log("PayCalculator " + m.getTitle(), "ROI bonus x" + 1.5f);
			} else if (roi < 0.5f) {
				/* ROI penalty */
				multiplier *= 0.5f; // a-loo, za-herr
				Gdx.app.log("PayCalculator " + m.getTitle(), "ROI penalty 50%");
			}
		}

		return (int) (value * multiplier);
	}

}
