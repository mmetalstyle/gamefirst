package com.niger.scripts;

import com.niger.mobs.IMob;

public interface IScript {
	public long getNextSleepTime();
	public IMob nextMob();
	public int getMobsCount();
	public long getFulltime(); // seconds
}
