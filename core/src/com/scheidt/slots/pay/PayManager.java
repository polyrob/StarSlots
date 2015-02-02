package com.scheidt.slots.pay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.scheidt.slots.font.FontManager;
import com.scheidt.slots.model.Movie;
import com.scheidt.slots.model.Star;
import com.scheidt.slots.reel.NameManager;
import com.scheidt.slots.sound.SlotSound;
import com.scheidt.slots.sound.SoundManager;

public class PayManager extends Actor {

	private static final int defaultBet = 20;

	private int score = 100;

	List<Payout> currentPayouts;

	private Map<String, Star> castMap;
	private Map<String, Movie> movieMap;
	private SoundManager soundManager;
	private NameManager nm;

	private long titleCycleTimer = 0;
	private int currentTitleIndex;
	private Payout p;

	private int won;

	private BitmapFont font_title;
	private BitmapFont font;

	private int consecutiveWins = 0;
	private int consecutiveLoss = 0;

	public PayManager(Map<String, Star> castMap, Map<String, Movie> movieMap, SoundManager soundManager) {
		this.castMap = castMap;
		this.movieMap = movieMap;
		this.soundManager = soundManager;

		// loadMovieMap(movies);
		currentPayouts = new ArrayList<Payout>();

		font_title = FontManager.font42_3D;
		font = FontManager.font36;
	}

	public void calcPayouts(List<String> starIDs, int wager) {
		currentPayouts.clear();
		List<Payout> payouts = new ArrayList<Payout>();

		Star a = castMap.get(starIDs.get(0));
		Star b = castMap.get(starIDs.get(1));
		Star c = castMap.get(starIDs.get(2));

		ArrayList<String> m = new ArrayList<String>();
		ArrayList<String> ab = new ArrayList<String>();
		ArrayList<String> ac = new ArrayList<String>();
		ArrayList<String> bc = new ArrayList<String>();

		m.addAll(a.getFilms());
		m.retainAll(b.getFilms());
		m.retainAll(c.getFilms());

		// build minor match lists, not matching on duplicates
		if (!a.getName().equals(b.getName())) {
			ab.addAll(a.getFilms());
			ab.retainAll(b.getFilms());
			ab.removeAll(m);
		}

		if (!a.getName().equals(c.getName())) {
			ac.addAll(a.getFilms());
			ac.retainAll(c.getFilms());
			ac.removeAll(m);
		}

		if (!b.getName().equals(c.getName())) {
			bc.addAll(b.getFilms());
			bc.retainAll(c.getFilms());
			bc.removeAll(m);
		}

		// check major bonus
		processMatches(payouts, wager*5, m, true, true, true);
		// check minor bonus
		processMatches(payouts, wager, ab, true, true, false);
		processMatches(payouts, wager, ac, true, false, true);
		processMatches(payouts, wager, bc, false, true, true);

		currentPayouts = payouts;
		won = 0;
		for (Payout p : currentPayouts) {
			won += p.value;
		}

		/* sound effects? */
		if (currentPayouts.size() > 0) {
			consecutiveWins++;
			consecutiveLoss = 0;

			if (currentPayouts.size() > 4 || won >= 100) {
				soundManager.play(SlotSound.RINGING);
			} else if (consecutiveWins % 5 == 0) {
				soundManager.play(SlotSound.SUSPENSE);
			} else {
				soundManager.play(SlotSound.HAPPY);
			}

		} else {
			consecutiveWins = 0;
			consecutiveLoss++;
		}

		if (consecutiveLoss >= 3) {
			soundManager.play(SlotSound.EVIL_LAUGH);
		}

	}

	private void processMatches(List<Payout> payouts, int wager, ArrayList<String> matches, boolean a, boolean b, boolean c) {
		if (!matches.isEmpty()) {
			for (String filmId : matches) {
				Movie m = movieMap.get(filmId);
				if (m != null) {

					int calcValue = PayCalculator.getPay(m, wager);
					Payout payout = new Payout();
					payout.name = m.getTitle() + " (" + m.getYear() + ") +" + calcValue;
					payout.value = calcValue;
					payout.a = a;
					payout.b = b;
					payout.c = c;
					score += calcValue;
					payouts.add(payout);
				} else {
					Payout payout = new Payout();
					payout.name = "UNKNOWN MOVIE - " + filmId;
					payout.value = wager;
					payout.a = a;
					payout.b = b;
					payout.c = c;
					score += wager;
					payouts.add(payout);
				}

			}
		}
		currentTitleIndex = 0;
	}
	
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		/* draw matches, if any */
		if (!currentPayouts.isEmpty()) {
			if (System.currentTimeMillis() > titleCycleTimer + 2000) {
				p = currentPayouts.get(currentTitleIndex);
				titleCycleTimer = System.currentTimeMillis();
				currentTitleIndex++;
				nm.setMatchedNames(p.a, p.b, p.c);
				if (currentTitleIndex >= currentPayouts.size())
					currentTitleIndex = 0;

			}

			if (p.name.length() < 34) {
				font_title.drawWrapped(batch, p.name, 350, 735, 740, HAlignment.CENTER);
			} else {
				font_title.drawWrapped(batch, p.name, 350, 750, 740, HAlignment.CENTER);
			}

			/* draw current payline value */
			// font.drawWrapped(batch, "Pays " + p.value, 20, 530, 100);
			font.drawWrapped(batch, "x" + currentPayouts.size() + " Pays!", 10, 600, 300, HAlignment.CENTER);
			/* draw total win for spin */
			font.drawWrapped(batch, won + " credits", 10, 260, 300, HAlignment.CENTER);
		}

		/* draw balance */
		font.draw(batch, String.valueOf(score), 140, 140);

	}


	public void clearScores() {
		currentPayouts.clear();
	}

	public int payForSpin() {
		if (score >= defaultBet){
			score -= defaultBet;
			return defaultBet;
		} else if (score >= 0) {
			int wager = score;
			score = 0;
			return wager;
		} else {
			return 0;
		}
		
	}
	
	public void setNameManager(NameManager nm) {
		this.nm = nm;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	
}
