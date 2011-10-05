package com.niger.guns;

public class GunPistol implements IGun{
	
	public static final float bulletDuration = 2f;
	public static int bulletCount = -1;
	public static int gunSleep = 300;
	public static final String nigerImageWithGun="ololo.png";
	public static final String bulletImage ="ololo.png";

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
		return bulletImage;
	}

	
}
