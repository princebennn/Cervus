package com.zegaout.gameobjects;

import java.util.Random;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Scrollable {

	protected Vector2 position;
	protected Vector2 velocity;
	protected Rectangle rect;
	protected Rectangle bigRect;
	protected boolean isScrolledLeft;
	protected float startX;
	protected Boolean isTooNear;
	protected Vector2 rectCenter = new Vector2();
	
	protected Random r;
	
	public Scrollable(float x, float y, float width, float height) {
		isScrolledLeft = false;
		isTooNear = false;
		startX = x;
		rect = new Rectangle(x, y, width, height);
		rect.getCenter(rectCenter);
		bigRect = new Rectangle(rectCenter.x, rectCenter.y, width + 25, height + 25);
		bigRect.setCenter(rectCenter.x, rectCenter.y);
		velocity = new Vector2(-50, 0);
		position = new Vector2(x, y);
		r = new Random();
	}
	
	public void update(float delta) {
	position.add(velocity.cpy().scl(delta));
		
		if (position.x + rect.width < 0) {
			isScrolledLeft = true;
		}
	rect.setPosition(position.x, position.y);
	rect.getCenter(rectCenter);
	bigRect.setCenter(rectCenter.x, rectCenter.y);
	}
	
	public void reset() {
		rect.setSize(r.nextInt(50) + 10);
		position.set(startX, r.nextInt(180 - (int)rect.height) % (180 - rect.height));
		rect.setPosition(position.x, position.y);
		rect.getCenter(rectCenter);
		bigRect.setSize(rect.getHeight() + 25);
		bigRect.setCenter(rectCenter.x, rectCenter.y);
		velocity = new Vector2(-50, 0);
		isScrolledLeft = false;
	}
	
	public boolean collides(Rectangle rect) {
		return Intersector.overlaps(rect, this.rect);
	}
	
	public boolean collidesBigRect(Rectangle rect) {
		return Intersector.overlaps(rect, bigRect);
	}
	
	public boolean isScrolledLeft() {
		return isScrolledLeft;
	}
	
	public Boolean isActive() {
		return !isTooNear && !isScrolledLeft; 
	}
	
	public Random getRandom() {
		return r;
	}
	
	public void setIsTooNear(Boolean value) {
		isTooNear = value;
	}
	
	public Boolean isTooNear() {
		return (isTooNear);
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public Rectangle getBigRect() {
		return bigRect;
	}
	
	public void setScrollSpeed(float scrollSpeed) {
		velocity.x = scrollSpeed;
	}
}
