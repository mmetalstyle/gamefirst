package com.niger.logic;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import org.anddev.andengine.engine.handler.runnable.RunnableHandler;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;

import android.widget.Toast;

import com.niger.*;
import com.niger.main.Map;
import com.niger.main.NigerActivity;
import com.niger.main.TargetWaves;
import com.niger.mobs.IMob;

public class Algorithm2 extends NigerActivity{
	public static boolean isFinding = false;
	//LinkedList<Sprite> target;
	HashMap<Sprite, IMob> target;
	LinkedList<Sprite> bullet;
	LinkedList<Sprite> toDeliteTarg = new LinkedList<Sprite>();
	LinkedList<Sprite> toDeliteBull = new LinkedList<Sprite>();

	float xB;
	float yB;
	float xT;
	float yT;
	double distance;
	final RunnableHandler runnableRemoveHandler = new RunnableHandler();
	public Algorithm2() {
		
		scene.registerUpdateHandler(runnableRemoveHandler);
		//target = TargetWaves.targets;
		target = TargetWaves.mobsData;
		bullet = Map.bulletList;
		run();
	}
	void run(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					synchronized(bullet){
						synchronized (target) {
							for(Sprite bull : bullet)
							{
								Set<Entry<Sprite,IMob>> set = target.entrySet();
								for(Entry<Sprite,IMob> targ : target.entrySet())
								{
									xB = bull.getX();
									yB = bull.getY()-16;
									xT = targ.getKey().getX();
									yT = targ.getKey().getY();
									distance = Math.sqrt(Math.pow(xB - xT ,2) + Math.pow(yB - yT, 2));
									
									if(distance < 32)
									{
										targ.getValue().setHealth(targ.getValue().getHealth() - 49);
										if(targ.getValue().getHealth() < 0)
											toDeliteTarg.add(targ.getKey());
										else 
											targ.getKey().setColor(0xff,(int)targ.getValue().getColorRatio() , 1);
										toDeliteBull.add(bull);
									}
								}
							}
							for(final Sprite delT : toDeliteTarg)
							{						
									//del.setColor(54, 343, 122);
									target.remove(delT);
									runOnUiThread(new Runnable() {
										@Override
										public void run() {
											runnableRemoveHandler.postRunnable(new Runnable() {
												@Override
												public void run() {
													/* Now it is save to remove the entity! */
													scene.detachChild(delT);
												}
											});
										}});
								
								
							}
							for(final Sprite delB : toDeliteBull)
							{
								bullet.remove(delB);
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										runnableRemoveHandler.postRunnable(new Runnable() {
											@Override
											public void run() {
												/* Now it is save to remove the entity! */
												scene.detachChild(delB);
											}
										});
									}});
							}
							toDeliteTarg.clear();
							toDeliteBull.clear();
						}
					}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
		
	}
   
}
