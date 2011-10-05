package com.niger.main;

import java.util.LinkedList;

import org.anddev.andengine.engine.handler.collision.CollisionHandler;
import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.modifier.IModifier;
import com.niger.guns.*;
import com.niger.logic.Algorithm2;
import com.niger.scripts.IScript;
import android.widget.Toast;


public class Map extends NigerActivity{
	 /*
	 * Graphic
	 */
	private BitmapTextureAtlas mMapTexture;
	private TextureRegion mMapTextureRegions;	
	private TextureRegion mMapTextureRegions2;
	private TextureRegion fireTextureRegion;
	private TextureRegion gun1TextureRegion;
	private TextureRegion gun2TextureRegion;
	private TextureRegion gun3TextureRegion;
	private TextureRegion gun4TextureRegion;
	private TextureRegion killer1TextureRegion;
	private TextureRegion killer2TextureRegion;
	private TextureRegion killer3TextureRegion;
	private TextureRegion killer4TextureRegion;
	private TextureRegion bulletTextureRegion;
	private TextureRegion runTextureRegion;
	private TextureRegion waveTextureRegion;
	private TextureRegion runWaveTextureRegion;
	
	/* 
	 * Sprites
	 */
	public static Scene scene2;
	public static Sprite map;
	public static Sprite map2;
    public static Sprite fire;
    public static Sprite gun1;
    public static Sprite gun2;
    public static Sprite gun3;
    public static Sprite gun4;
    public static Sprite killer_pistol;
    public static Sprite killer_AK47;
    public static Sprite killer_M4;
    public static Sprite killer_bazuka;
    public static Sprite run;
    public static Sprite wave;
    public static Sprite runWave;
	public static final float killerSpeed = 2f;
	
	/*
	 * constants 
	 */
	private final int GUN1 = 1;
	private final int GUN2 = 2;
	private final int GUN3 = 3;
	private final int GUN4 = 4;
	
	/* 
	 * Modifiers & listeners
	 */
	public static IEntityModifierListener killerListener;
	private IEntityModifierListener bulletListener;
	private PathModifier bulletPass;
	static LinkedList<PathModifier> modifList = new LinkedList<PathModifier>();
	 
	/* 
	 * Coordinates
	 */
	private Boolean canFire = true;
	static float xKillerPos = NigerActivity.KILLER_POS_X;
	static float yKillerPos = NigerActivity.KILLER_POS_Y;
	static float xNewKillerPos = xKillerPos;
	static float yNewKillerPos = yKillerPos;
	
	/*
	 * STATIC
	 */
	public static final IGun [] WEAPON = 
		{
		null,
		new GunPistol(),
		new GunAK47(),
		new GunM4(),
		new GunBazuka()
		};
	public static IGun selectedWeapon = WEAPON[1] ;
	public static LinkedList<Sprite> bulletList = new LinkedList<Sprite>();
	public static Boolean isRunTouched = false;
	
Map(){
	/* 
	 * Map
	 */
	this.mMapTexture = new BitmapTextureAtlas(1024, 1024,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.mMapTextureRegions = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"map1.jpg", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);

	/*
	 * Killer Walk place
	 */
	this.mMapTexture = new BitmapTextureAtlas(1024, 1024,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.mMapTextureRegions2 = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"glass.jpg", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * Fire Button
	 */
	this.mMapTexture = new BitmapTextureAtlas(128, 64,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.fireTextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"fire1.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * Gun1 Button
	 */
	this.mMapTexture = new BitmapTextureAtlas(64, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.gun1TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"gun1.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * Gun2 Button
	 */
	this.mMapTexture = new BitmapTextureAtlas(64, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.gun2TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"gun2.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * Gun3 Button
	 */
	this.mMapTexture = new BitmapTextureAtlas(64, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.gun3TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"gun3.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);

	
	/*
	 * Gun4 Button
	 */
	this.mMapTexture = new BitmapTextureAtlas(64, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.gun4TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"gun4.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);

	
	/*
	 * Bullet Image
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.bulletTextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"red_tower.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * Killer Images
	 */
	
	/*
	 *  Killer pistol
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.killer1TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"killer_pistol.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * killer AK-47
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.killer2TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"killer_AK47.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * killer M4
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.killer3TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"killer_M4.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * killer bazuka
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.killer4TextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"killer_bazuka.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * run button
	 */
	this.mMapTexture = new BitmapTextureAtlas(64, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.runTextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"run.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * wave
	 */
	this.mMapTexture = new BitmapTextureAtlas(256, 64,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.waveTextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"waveline.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	/*
	 * run wave
	 */
	this.mMapTexture = new BitmapTextureAtlas(32, 32,
			TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	this.runWaveTextureRegion = BitmapTextureAtlasTextureRegionFactory
			.createFromAsset(this.mMapTexture, NigerActivity.gameActivity, 
					"blue_tower.png", 0, 0);
	NigerActivity.engine.getTextureManager().loadTexture(mMapTexture);
	
	
	/*
	  *  load all gun Button
	  */
	 gun1 = new Sprite(160,16, gun1TextureRegion){
		 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			 Toast.makeText(NigerActivity.gameActivity, "GUN 1", Toast.LENGTH_SHORT).show();
			 changeKiller(GUN1, killer.getX(), killer.getY());
			 return true;
		 }
	 };
	 
	 
	 gun2 = new Sprite(240,16, gun2TextureRegion){
		 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			 Toast.makeText(NigerActivity.gameActivity, "GUN 2", Toast.LENGTH_SHORT).show();
			 changeKiller(GUN2, killer.getX(), killer.getY());
			 return true;
		 }
	 };
	 
	 gun3 = new Sprite(320,16, gun3TextureRegion){
		 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			 Toast.makeText(NigerActivity.gameActivity, "GUN 3", Toast.LENGTH_SHORT).show();
			 changeKiller(GUN3, killer.getX(), killer.getY());
			 return true;
		 }
	 };

	 gun4 = new Sprite(400,16, gun4TextureRegion){
		 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			 Toast.makeText(NigerActivity.gameActivity, "GUN 4", Toast.LENGTH_SHORT).show();
			 changeKiller(GUN4, killer.getX(), killer.getY());
			 return true;
		 }
	 };
	
	 /*
	  * Gun sprites
	  */
	 killer_pistol = new Sprite(0,0, killer1TextureRegion);
	 killer_AK47   = new Sprite(0,0, killer2TextureRegion);
	 killer_M4     = new Sprite(0,0, killer3TextureRegion);
	 killer_bazuka = new Sprite(0,0, killer4TextureRegion);
	 
	 /*
	  * wave sprites
	  */
	 wave = new Sprite(0,0, waveTextureRegion);
	 runWave = new Sprite(0,0, runWaveTextureRegion);


	killerListener=new IEntityModifierListener(){
		@Override
		public void onModifierFinished(IModifier<IEntity> arg0,
				IEntity arg1) {
		
		}

		@Override
		public void onModifierStarted(IModifier<IEntity> arg0,
				IEntity arg1) {
			// TODO Auto-generated method stub			
		}		
	};
	
	

	
	map = new Sprite(0, 0, mMapTextureRegions) {
		@Override
		public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
				final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        /*
    	   xKillerPos=NigerActivity.killer.getX();
    	   yKillerPos=NigerActivity.killer.getY();
    	   yNewKillerPos=pTouchAreaLocalY-16;
    	   if(yKillerPos==yNewKillerPos)yKillerPos++;
			final Path path = new Path(2).to(xKillerPos, yKillerPos).to(NigerActivity.CAMERA_HEIGHT,yNewKillerPos);
            
			float len=  Math.abs(yKillerPos-yNewKillerPos);
			//xKillerPos=KILLER_POS_X;
			//yKillerPos=pTouchAreaLocalY;
			float duration;
			duration=(len * killerSpeed) / NigerActivity.CAMERA_HEIGHT;
			if(duration < 1) duration = 0.5f;
			PathModifier killerPass=new PathModifier(duration , path, killerListener );
			killerPass.setRemoveWhenFinished(true);
			NigerActivity.killer.registerEntityModifier(killerPass); 
			modifList.addLast(killerPass);
			
			while(modifList.size() > 1){
				killer.unregisterEntityModifier(modifList.getFirst());
				modifList.removeFirst();
			}
			
			*/
			return true;
		}
};


map2=new Sprite(480, 0, mMapTextureRegions2)
{
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			
				/*
				 * new position calculations
				 */
				
	    	   xKillerPos=NigerActivity.killer.getX();
	    	   yKillerPos=NigerActivity.killer.getY();
	    	   xNewKillerPos=pTouchAreaLocalX-16+480;
	    	   yNewKillerPos=pTouchAreaLocalY-16;
	    	   if(xNewKillerPos < 480)xNewKillerPos =  480;
	    	   if(yKillerPos==yNewKillerPos)yKillerPos++;
				final Path path = new Path(2).to(xKillerPos, yKillerPos).to(xNewKillerPos,yNewKillerPos);
	          
				
				/*
				 *  duration calculations
				 */
				float lenY=  Math.abs(yKillerPos-yNewKillerPos);
				float lenX=  Math.abs(xKillerPos-xNewKillerPos);
				float durationY;
				durationY=(lenY * killerSpeed) / NigerActivity.CAMERA_HEIGHT;
				float durationX;
				durationX=(lenX * killerSpeed) / (NigerActivity.CAMERA_WIDTH - NigerActivity.CAMERA_HEIGHT);
				if(durationX==0)durationX=0.1f;
				if(durationY==0)durationY=0.1f;
				
				/*
				 *  set path
				 */
				float fullDuration =(float) Math.sqrt((double)Math.pow(durationX, 2) + (double)Math.pow(durationY, 2));
				PathModifier killerPass=new PathModifier(fullDuration , path, killerListener );
				killerPass.setRemoveWhenFinished(true);
				killer.registerEntityModifier(killerPass); 
				modifList.addLast(killerPass);
				
				while(modifList.size() > 1){
					killer.unregisterEntityModifier(modifList.getFirst());
					modifList.removeFirst();
				}

				return true;
			}

};
 mMapTextureRegions2.setTexturePosition(0, 0);
 
 final RunnableHandler runnableRemoveHandler = new RunnableHandler();
 scene.registerUpdateHandler(runnableRemoveHandler);
 
 fire = new Sprite(0,0, fireTextureRegion){
	 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
         if(canFire){
        	 canFire = false;
		 final Sprite bullet = new Sprite (killer.getX(), killer.getY(), bulletTextureRegion);
		 scene.attachChild(bullet);
		 final Path path = new Path(2).to(killer.getX(), killer.getY()).to(0,killer.getY());
		 final PathModifier bM= new PathModifier(selectedWeapon.bulletDuration(), path, new IEntityModifierListener(){
						     		@Override
									public void onModifierFinished(IModifier<IEntity> arg0,
											IEntity arg1) {
						     			synchronized (bulletList) {
											 bulletList.remove(bullet);
											 }
										runOnUiThread(new Runnable() {
											@Override
											public void run() {
												runnableRemoveHandler.postRunnable(new Runnable() {
													@Override
													public void run() {
														/* Now it is save to remove the entity! */
														scene.detachChild(bullet);
													}
												});
											}});
									}

									@Override
									public void onModifierStarted(IModifier<IEntity> arg0,
											IEntity arg1) {
										new Thread(new Runnable() {
											
											@Override
											public void run() {
												/*
												 * add to collision detection
												 */
												 synchronized (bulletList) {
													 bulletList.add(bullet);
													 }
												try {
													Thread.currentThread().sleep(selectedWeapon.gunSleep());
													canFire=true;
												} catch (InterruptedException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}).start();
									}
							   }
		 );
                         
                              /*
                               *  Start flying bullet
                               */
								 bullet.registerEntityModifier(bM);											
         }
		 return true;
						
	 };
 };
 
 
 run = new Sprite(500,16, runTextureRegion){
	 public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		 
		 synchronized (isRunTouched) {
			if(!isRunTouched){
				isRunTouched = true;
			    if(NigerActivity.scrIter.hasNext()){
			    	scene.attachChild(wave);
			    	wave.setPosition(150, 56);
			    	scene.attachChild(runWave);
			    	runWave.setPosition(150, 64);
			    	wave();
			    	Toast.makeText(NigerActivity.gameActivity, "run", Toast.LENGTH_SHORT).show();
			    }
			    else Toast.makeText(NigerActivity.gameActivity, "Game Over", Toast.LENGTH_SHORT).show();
		 }
		}		 
		 return true;
	 }
 };
 
/*
 * register and attach all pict
 */
 
scene.attachChild(map);
//map.setColor(0, 0, 0);
scene.attachChild(map2);
scene.attachChild(fire);
scene.attachChild(gun1);
scene.attachChild(gun2);
scene.attachChild(gun3);
scene.attachChild(gun4);
scene.attachChild(run);


scene.registerTouchArea(fire);
scene.registerTouchArea(run);
scene.registerTouchArea(gun1);
scene.registerTouchArea(gun2);
scene.registerTouchArea(gun3);
scene.registerTouchArea(gun4);
scene.registerTouchArea(map2);

}

/*
 * Change Killer Sprite to selected GunButton
 */
public void changeKiller(int Id, float xK, float yK){
	scene.detachChild(killer);
	stopMiving();
		switch(Id){
		   
		case GUN1:
			selectedWeapon = WEAPON [1];
			killer_pistol.setPosition(xK, yK);
			killer=killer_pistol;
			scene.attachChild(killer);
			break;
			
		case GUN2:
			selectedWeapon = WEAPON [2];
			killer_AK47.setPosition(xK, yK);
			killer=killer_AK47;
			scene.attachChild(killer);
			break;
			
		case GUN3:
			selectedWeapon = WEAPON [3];
			killer_M4.setPosition(xK, yK);
			killer=killer_M4;
			scene.attachChild(killer);
			break;
			
		case GUN4:
			selectedWeapon = WEAPON [4];
			killer_bazuka.setPosition(xK, yK);
			killer=killer_bazuka;
			scene.attachChild(killer);
			break;
				}
}

public void stopMiving(){
	while(modifList.size() > 0){
		killer.unregisterEntityModifier(modifList.getFirst());
		modifList.removeFirst();
	}
}

/*
 *  run new mobs wawe
 */
 boolean wave(){
	new TargetWaves(NigerActivity.scrIter.next());
	return true;
}
}
