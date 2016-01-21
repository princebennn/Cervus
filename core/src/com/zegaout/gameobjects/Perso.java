package com.zegaout.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.zegaout.screens.GameScreen;

public class Perso {

	private Vector2 position;
	private Vector2 destination;
	private Rectangle rect;
	private Color color;
	private Boolean isMoving;
	private float hungry;
	private Boolean isDead;
	private float resizing;
	
	private GameScreen screen;
	
	public Perso(GameScreen screen) {
		this.isMoving = false;
		this.screen = screen;
		hungry = 80;
		resizing = 0;
		isDead = false;
		//float size = 40 * (50 / 100);
		this.position = new Vector2(screen.getGameX() / 2, screen.getGameY() / 2);
		this.destination = new Vector2(screen.getGameX() - (screen.getGameX() / 4), position.y);
		rect = new Rectangle(position.x, position.y, 0, 0);
		updateSize();
		position.set(position.x - rect.getHeight() / 2, position.y - rect.getHeight() / 2);
		rect.setPosition(position.x, position.y);
		color = new Color(108 / 255.0f, 90 / 255.0f, 163 / 255.0f, 1f);
	}
	
	public void update(float delta) {
		if (isMoving) {
			updateSize();
			updatePosition(delta);
		}
		hungry -= delta * 1.5f;
		if (hungry < 0)
			hungry = 0;
		else if (hungry > 100) {
			hungry = 100;
		}
	}
	
	public void updatePosition(float delta) {
		Vector2 v = new Vector2(0 , (this.destination.y - (this.position.y + (rect.getHeight() / 2))));
		float len_v = v.len();
		v.nor();
		v.scl(500 * delta);
		if (v.len() > len_v) {
			v.y = 0;
			this.position.y = destination.y - (rect.getHeight() / 2);
		} else {
			this.position.add(v);
		}
		if (position.y < 0) {
			position.y = 0;
		} else if (position.y + rect.height > screen.getGameY()) {
			position.y = screen.getGameY() - rect.height;
		}
		position.x = screen.getGameX() / 2 - (rect.getHeight() / 2);
		rect.setPosition(position.x, position.y);
	}
	
	public void updateSize() {
		float newSize = getConvertSize() + resizing;
		if (newSize > 100) {
			newSize = 100;
		} else if (newSize < 20) {
			newSize = 20;
		}
		resizing = 0;
		setConvertSize(newSize);
		/*float midPointX = gameX - (gameX / 4);
		float tier = midPointX / ((float) 3 / 2);
		float diff = this.destination.x - midPointX;
		if (diff > tier) {
			diff = tier;
		} else if (diff < -tier) {
			diff = -tier;
		}
		if (diff > 30)
			diff = 30;
		else if (diff < -30)
			diff = -30;
		setConvertSize(50 + diff);
		*/
	}
	
	public void reset() {
		this.isMoving = false;
		hungry = 80;
		resizing = 0;
		isDead = false;
		this.position = new Vector2(screen.getGameX() / 2, screen.getGameY() / 2);
		this.destination = new Vector2(screen.getGameX() - (screen.getGameX() / 4), position.y);
		rect = new Rectangle(position.x, position.y, 0, 0);
		updateSize();
		position.set(position.x - rect.getHeight() / 2, position.y - rect.getHeight() / 2);
		rect.setPosition(position.x, position.y);
		color = new Color(108 / 255.0f, 90 / 255.0f, 163 / 255.0f, 1f);
	}
	
	private void setConvertSize(float size) {
		rect.setSize(40 * (size / 100), 40 * (size / 100));
	}
	
	private float getConvertSize() {
		return ((rect.getHeight() * 100) / 40);
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	public float getSizeX() {
		return rect.getWidth();
	}
	
	public float getSizeY() {
		return rect.getHeight();
	}
	
	public void setSize(float size) {
		setConvertSize(size);
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
	
	public Boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(Boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public void setX(float positionX) {
		this.position.x = positionX;
		rect.setPosition(positionX, position.y);
	}
	
	public void setY(float positionY) {
		this.position.y = positionY;
		rect.setPosition(position.x, positionY);
	}
	
	public float getDestinationX() {
		return destination.x;
	}
	
	public float getDestinationY() {
		return destination.y;
	}
	
	public void setDestinationX(float destinationX) {
		this.destination.x = destinationX;
	}
	
	public void setDestinationY(float destinationY) {
		this.destination.y = destinationY;
	}
	
	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}
	
	public Boolean isDead() {
		return isDead;
	}
	
	public void setResizing(float resizing) {
		this.resizing = resizing;
	}
	
	public float getResizing() {
		return (resizing);
	}
	
	public float getHungry() {
		return hungry;
	}
	
	public void setHungry(float hungry) {
		this.hungry = hungry;
	}
}
