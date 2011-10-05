package com.niger.scripts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.niger.mobs.IMob;
import com.niger.mobs.Mob1;
import com.niger.mobs.Mob2;

public class Script1 implements IScript{
	
	private LinkedList<IMob> mobs;
	private LinkedList<Long> sleepBetweenNextMob;
	private Iterator<IMob> mobsIte;
	private Iterator<Long> sleepIte;
	private final long fullTime = 84L; 
	
	public Script1(){
		mobs = new LinkedList<IMob>();
		sleepBetweenNextMob = new LinkedList<Long>();
		
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(400L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(500L);
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(1000L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(300L);//45
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(400L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(500L);
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob2());
		sleepBetweenNextMob.add(300L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(200L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());
		sleepBetweenNextMob.add(100L);
		mobs.add(new Mob1());	
		sleepBetweenNextMob.add(200L);//84
		
		mobsIte = mobs.iterator();
		sleepIte = sleepBetweenNextMob.iterator();
	}

	@Override
	public long getNextSleepTime() {
		return sleepIte.next();
	}

	@Override
	public IMob nextMob() {
		return mobsIte.next();
	}

	@Override
	public int getMobsCount() {
		return mobs.size();
	}

	@Override
	public long getFulltime() {
		return fullTime;
	}
}
