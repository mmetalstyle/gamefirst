package com.niger.guns;

public class GunAK47 implements IGun{
	
	public static final float bulletDuration = 1f;
	public static int bulletCount = -1;
	public static int gunSleep = 100;
	public static final String nigerImageWithGun="ololo.png";
	public static final String bulletImage = "oolo.png";

	@Override
	public float bulletDuration() {
		return bulletDuration;
	}

	@Override
	public int bulletCount() {
		return bulletCount;
	}

	@Override
	public String nigerImageWithGun() {
		return nigerImageWithGun;
	}
	
	@Override
	public int gunSleep() {
	return gunSleep;
	}

	@Override
	public String bulletImage() {
		// TODO Auto-generated method stub
		return bulletImage;
	}

}
