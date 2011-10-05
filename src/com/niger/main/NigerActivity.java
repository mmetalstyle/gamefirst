package com.niger.main;

import java.util.Iterator;
import java.util.LinkedList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.niger.logic.Algorithm2;
import com.niger.scripts.*;


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class NigerActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	public static final int KILLER_POS_Y = CAMERA_HEIGHT/2-32;
	public static final int KILLER_POS_X = 480;
	
	
	// ===========================================================
	// 
	// ===========================================================
      
	public static  LinkedList<IScript> scriptList = new LinkedList<IScript>();
	public static  Iterator<IScript> scrIter; 
		

	// ===========================================================
	// Fields
	// ===========================================================

	/* Global */
	public static Camera mCamera;
	public static Scene scene;
	protected static Engine engine;
	protected static NigerActivity gameActivity;
	static Sprite killer;

	/* texture for test object */
	public static TextureRegion mFaceTextureRegion;
	public static TextureRegion mFaceTextureRegion2;


	@Override
	public Engine onLoadEngine() {
		Log.i("onLoadEngine", "onLoadEngine");

		/* GET SCREEN SIZE */
		/*
		 * WindowManager w = getWindowManager(); Display d =
		 * w.getDefaultDisplay(); DisplayMetrics metrics = new DisplayMetrics();
		 * d.getMetrics(metrics); CAMERA_WIDTH = d.getWidth(); CAMERA_HEIGHT =
		 * d.getHeight(); Log.i("DisplayMetrics", CAMERA_WIDTH + " | " +
		 * CAMERA_HEIGHT);
		 */

		gameActivity = this;

		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions options = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						getScreenResolutionRatio()), mCamera);
		
		
		
		return new Engine(options);
	}

	@Override
	public void onLoadResources() {
		Log.i("onLoadResources", "onLoadResources");
		engine = this.mEngine;
		
		BitmapTextureAtlas mBitmapTextureAtlas;
		/* texture for test object */
		mBitmapTextureAtlas = new BitmapTextureAtlas(64, 64,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this,
						"face_box_menu.png", 0, 0);
		engine.getTextureManager().loadTexture(mBitmapTextureAtlas);
		
		mBitmapTextureAtlas = new BitmapTextureAtlas(32, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFaceTextureRegion2 = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mBitmapTextureAtlas, this,
						"killer_pistol.png", 0, 0);
		engine.getTextureManager().loadTexture(mBitmapTextureAtlas);

	}

	@SuppressWarnings("deprecation")
	@Override
	public Scene onLoadScene() {
		/*
		 * load maps
		 */
		scriptList.add(new Script1());
		scriptList.add(new Script2());
		scriptList.add(new Script3());
		scrIter = scriptList.iterator();
		
		/* FPSLogger in console */
		this.mEngine.registerUpdateHandler(new FPSLogger());

		/* Create Global Scene */
		scene = new Scene(3);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f,
				0.8784f));
		
        /*
		 *  Attach gameZone
		 *  */
		new Map();
		/* 
		 * Attach killer
		 */
		killer = new Sprite(KILLER_POS_X, KILLER_POS_Y, this.mFaceTextureRegion2);
		scene.attachChild(killer);

		/*
		 * activate collision algorithm
		 */
		new Algorithm2();
		
		Toast.makeText(this, "Click \"Run \" button for start game", Toast.LENGTH_SHORT).show();
		
		return scene;
	}

	@Override
	public void onLoadComplete() {
		Log.i("onLoadComplete", "onLoadComplete");
		initGameZone();
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private void initGameZone() {
		scene.setTouchAreaBindingEnabled(true);
	}

	private float getScreenResolutionRatio() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		return ((float) dm.widthPixels) / ((float) dm.heightPixels);
	}
}