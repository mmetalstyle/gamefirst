package com.niger.main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.PathModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.modifier.PathModifier.Path;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.util.modifier.IModifier;

import com.niger.logic.Algorithm2;
import com.niger.mobs.IMob;
import com.niger.scripts.IScript;
import com.niger.scripts.Script1;

import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.widget.Toast;

public class TargetWaves extends NigerActivity{
	IEntityModifierListener modListener;
	IScript currentScript;
	/*
	 * Targets List
	 */
	public static HashMap<Sprite, IMob> mobsData = new HashMap<Sprite, IMob>();
	//public static LinkedList<Sprite> targets = new LinkedList<Sprite>();

	
  public TargetWaves(IScript script) {
    currentScript = script;
	final RunnableHandler runnableRemoveHandler = new RunnableHandler();
		scene.registerUpdateHandler(runnableRemoveHandler);
		
/*
 *  start targets
 */
	  new Thread(new Runnable() {
		@Override
		public void run() {
			  for(int i=0;i<currentScript.getMobsCount();i++){
				  Random rd=new Random();
				     final float koef=(float)rd.nextFloat()%(float)0.6;
			final Sprite newface = new Sprite(0-64,CAMERA_HEIGHT/2, NigerActivity.mFaceTextureRegion){
				@Override
				public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
						final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
					final Path path = new Path(2).to(killer.getX(), killer.getY()).to(killer.getX(),CAMERA_HEIGHT*koef + 128 + 16);
					float len=  Math.abs(killer.getY()-CAMERA_HEIGHT*koef + 128 + 16);
					float duration;
					duration=(len * Map.killerSpeed) / NigerActivity.CAMERA_HEIGHT;
					if(duration < 1) duration = 0.5f;
					PathModifier killerPass=new PathModifier(duration , path, new IEntityModifierListener(){
						@Override
						public void onModifierFinished(IModifier<IEntity> arg0,
								IEntity arg1) {
						
						}

						@Override
						public void onModifierStarted(IModifier<IEntity> arg0,
								IEntity arg1) {
							// TODO Auto-generated method stub			
						}		
					} );
					killerPass.setRemoveWhenFinished(true); 
					killer.registerEntityModifier(killerPass); 
					Map.modifList.addLast(killerPass);
					
					while(Map.modifList.size() > 1){
						killer.unregisterEntityModifier(Map.modifList.getFirst());
						Map.modifList.removeFirst();
					}

					//killer.setPosition(killer.getX(), CAMERA_HEIGHT*koef + 128);
					return true;
				}
			};
			
			
			
			//register target on display
			scene.attachChild(newface);
			scene.registerTouchArea(newface);
			//add target to list
							
			 
		     final Path path = new Path(2).to(0-64,CAMERA_HEIGHT*koef + 128).to(CAMERA_WIDTH+64, CAMERA_HEIGHT*koef + 128);
		     PathModifier pM=new PathModifier(10, path, new IEntityModifierListener(){
		     		@Override
					public void onModifierFinished(IModifier<IEntity> arg0,
							IEntity arg1) {
		     			synchronized (mobsData) {
		     				mobsData.remove(newface);
							//targets.remove(newface);
						}
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								runnableRemoveHandler.postRunnable(new Runnable() {
									@Override
									public void run() {
										scene.detachChild(newface);
									}
								});
							}});
					}

					@Override
					public void onModifierStarted(IModifier<IEntity> arg0,
							IEntity arg1) {
						synchronized (mobsData) {
							 mobsData.put(newface, currentScript.nextMob());
					    	 //targets.addLast(newface);
						}
					}
					
				} 
		);
		     /* 
		      * switch on replacement by path
		      * remove path object after finish
		      */
		     newface.registerEntityModifier(pM);
		     pM.setRemoveWhenFinished(true);
		     
		     try {
				Thread.currentThread().sleep(currentScript.getNextSleepTime());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			  }
			  
			  /*
			   *  sleep before next wave
			   */
			  new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {}	
					Map.isRunTouched = false;
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							runnableRemoveHandler.postRunnable(new Runnable() {
								@Override
								public void run() {
									scene.detachChild(Map.wave);
									scene.detachChild(Map.runWave);
								}
							});
						}});
				}
			}).start();
		}
	}).start();
	  
	  /*
	   * start waveline
	   */
	new Thread(new Runnable() {
		float step = (256-32) / currentScript.getFulltime();
		@Override
		public void run() {
			for(int i=0;i<currentScript.getFulltime();i++)
			{
				try {
					Thread.sleep(1000);
					Map.runWave.setPosition(Map.runWave.getX()+step, Map.runWave.getY());
				} catch (InterruptedException e) {}
				
			}
		}
	}).start();
	 
  }
}