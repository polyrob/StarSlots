package com.scheidt.slots.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.scheidt.slots.model.Star;
import com.scheidt.slots.util.LoadData;

public class TestPaybackPercentage {

//	private static final int COST_PER_SPIN = 15;
//	List<Star> people;
//	private static Random randomizer = new Random();
//
//
//	public void run(int iterations) {
//
//		int paidOut = 0;
//		int moneyTaken = 0;
//		int userStartBalance = 100;
//		
//		// load people
//		try {
//			people = LoadData.loadPeople();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		// loop for each iteration
//		for (int i = 0; i < iterations; i++) {
//			moneyTaken += userStartBalance;
//			Result result = spinOut(userStartBalance);
//			if (result.balance > 0) {
//				System.out.println("Iteration " + i + ": still going after 100 spins! Balance = " + result.balance);
//				paidOut += result.balance;
//			} else {
//				System.out.println("Iteration " + i + ": made it " + result.spins + " spins.");
//				// no money paid out
//			}
//		}
//		System.out.println("The calculated payback percentage is " + ((float)paidOut/(float)moneyTaken)*100);
//
//	}
//
//	protected Result spinOut(int startingBalance) {
//		//int balance = 100;
//		// loop for each spin while positive balance
//		int totalSpins = 0;
//		while (startingBalance > 0 && totalSpins < 100 ) {
//			startingBalance -= COST_PER_SPIN;
//			totalSpins++;
//			/* get new people */
//			Star a = people.get(randomizer.nextInt(people.size()));
//			Star b = people.get(randomizer.nextInt(people.size()));
//			Star c = people.get(randomizer.nextInt(people.size()));
//			/* do matches */
//			ArrayList<String> m = new ArrayList<String>();
//			ArrayList<String> ab = new ArrayList<String>();
//			ArrayList<String> ac = new ArrayList<String>();
//			ArrayList<String> bc = new ArrayList<String>();
//
//			m.addAll(a.getFilmIDs());
//			m.retainAll(b.getFilmIDs());
//			m.retainAll(c.getFilmIDs());
//
//			// build minor match lists, not matching on duplicates
//			if (!a.getName().equals(b.getName())) {
//				ab.addAll(a.getFilmIDs());
//				ab.retainAll(b.getFilmIDs());
//				ab.removeAll(m);
//			}
//
//			if (!a.getName().equals(c.getName())) {
//				ac.addAll(a.getFilmIDs());
//				ac.retainAll(c.getFilmIDs());
//				ac.removeAll(m);
//			}
//
//			if (!b.getName().equals(c.getName())) {
//				bc.addAll(b.getFilmIDs());
//				bc.retainAll(c.getFilmIDs());
//				bc.removeAll(m);
//			}
//
//			// check major bonus
//			startingBalance += processMatches(m, 50);
//			// check minor bonus
//			startingBalance += processMatches(ab, 10);
//			startingBalance += processMatches(ac, 10);
//			startingBalance += processMatches(bc, 10);
//
//		}
//		Result r = new Result(startingBalance, totalSpins);
//		return r;
//	}
//
//	protected int processMatches(ArrayList<String> matches, int value) {
//		if (!matches.isEmpty()) {
//			int win = 0;
//			for (String filmId : matches) {
//				win += value;
//			}
//			return win;
//		}
//		return 0;
//	}
//	
//	private class Result {
//		public int balance, spins;
//		public Result(int balance, int spins) {
//			this.balance = balance;
//			this.spins = spins;
//		}
//	}

}

