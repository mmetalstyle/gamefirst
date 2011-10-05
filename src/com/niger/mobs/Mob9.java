package com.niger.mobs;

public class Mob9 implements IMob{
	private float speed = 1;
	private final float colorRatio = 100;
	private int health = 100;
	
	@Override
	public float getSpeed() {
		return speed;
	}
	@Override
	public int getHealth() {
		return health;
	}	
	@Override
	public int setHealth(int hlth) {
		return health = hlth;
	}
	@Override
	public double getColorRatio() {
		double ratio = colorRatio / health;
		if(ratio < 50) return 2;
		else return 1;
	}
}