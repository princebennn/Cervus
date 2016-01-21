package com.zegaout.gameobjects;

import java.util.ArrayList;
import java.util.Random;

import com.zegaout.gameworld.GameWorld;

public class ScrollHandler {

	private static final int OBSTACLE_NB = 30;
	private static final int ENEMY_NB = 1;
	
	private GameWorld world;
	private ArrayList<Obstacle> objs = new ArrayList<Obstacle>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Integer activeObstacleNb = 0;
	private float partyRunTime = 0;
	
	public ScrollHandler(GameWorld world) {
		this.world = world;
		initObstacles(OBSTACLE_NB);
		initEnemies(ENEMY_NB);
	}
	
	public void update(float delta, float runTime) {
		partyRunTime += delta;
		if (activeObstacleNb < objs.size()) {
			activeObstacleNb = (int) (( 0.05f + partyRunTime / 20f) * objs.size());
		}
		if (activeObstacleNb > objs.size()) {
			activeObstacleNb = objs.size();
		}
		System.out.println(activeObstacleNb);
		for(int i = 0; i < enemies.size(); i++) {
			Enemy obj = enemies.get(i);
			obj.update(delta);
			if (!obj.isActive()) {
				obj.reset();
				int j = -1;
				while (isTooNear(obj) && ++j < 5)
				{
					obj.reset();
				}
				if (isTooNear(obj)) {
					obj.setIsTooNear(true);
				}
				else {
					obj.setIsTooNear(false);
				}
			}
		}
		for(int i = 0; i < activeObstacleNb; i++) {
			Obstacle obj = objs.get(i);
			obj.update(delta);
			if (!obj.isActive()) {
				obj.reset();
				if (isTooNear(obj)) {
					obj.setIsTooNear(true);
				}
				else {
					obj.setIsTooNear(false);
				}
			}
		}
	}

	public void reset() {
		objs = new ArrayList<Obstacle>();
		enemies = new ArrayList<Enemy>();
		initObstacles(OBSTACLE_NB);
		initEnemies(ENEMY_NB);
		activeObstacleNb = 0;
		partyRunTime = 0;
	}
	
	private void initObstacles(int nbObstacles) {
		Random r;
		float gameX = this.world.getScreen().getGameX();
		float size;
		float startY;
		
		for(int i = 0; i < nbObstacles; i++) {
			r = new Random();
			size = r.nextInt(40) + 10;
			startY = r.nextInt(180 - (int)size) % (180 - size);
			objs.add(new Obstacle(gameX + size, startY, size, size));
			if (isTooNear(objs.get(i))) {
				objs.get(i).setIsTooNear(true);
			}
		}
	}
	
	private void initEnemies(int nbEnnemies) {
		Random r;
		float gameX = this.world.getScreen().getGameX();
		float size;
		float startY;
		
		for(int i = 0; i < nbEnnemies; i++) {
			r = new Random();
			size = r.nextInt(30) + 10;
			startY = r.nextInt(180 - (int)size) % (180 - size);
			enemies.add(new Enemy(gameX + size, startY, size, size));
			if (isTooNear(enemies.get(i))) {
				enemies.get(i).setIsTooNear(true);
			}
		}
	}
	
	private Boolean isTooNear(Scrollable ref) {
		for(int i = 0; i < objs.size(); i++) {
			Obstacle obj = objs.get(i);
			if (!obj.equals(ref) && obj.isActive() && obj.collidesBigRect(ref.getBigRect())) {
				return true;
			}
		}
		for(int i = 0; i < enemies.size(); i++) {
			Enemy obj = enemies.get(i);
			if (!obj.equals(ref) && obj.isActive() && obj.collidesBigRect(ref.getBigRect())) {
				return true;
			}
		}
		return false;
	}
	
	public void setScrollSpeed(float scrollSpeed) {
		for(int i = 0; i < objs.size(); i++) {
			Obstacle obj = objs.get(i);
			obj.setScrollSpeed(scrollSpeed);
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			Enemy obj = enemies.get(i);
			obj.setScrollSpeed(scrollSpeed);
		}
	}
	
	public Boolean collides() {
		for(int i = 0; i < objs.size(); i++) {
			Obstacle obj = objs.get(i);
			if (obj.collides(world.getPerso().getRect())) {
				return (true);
			}
		}
		return (false);
	}

	public Boolean collidesEnemies() {
		for(int i = 0; i < enemies.size(); i++) {
			Enemy obj = enemies.get(i);
			if (obj.collides(world.getPerso().getRect())) {
				if (!obj.isEatable(world.getPerso().getRect().getHeight())) {
					return (true);
				} else {
					obj.setIsEat(true);
					world.getPerso().setHungry(world.getPerso().getHungry() + obj.getRect().height / 2);
				}
			}
		}
		return (false);
	}
	
	public ArrayList<Obstacle> getObstacles() {
		return (objs);
	}
	
	public ArrayList<Enemy> getEnemies() {
		return (enemies);
	}
}
