package com.zegaout.ui;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class MenuButton {

	private Rectangle hitbox;
	private Color color;
	private Color printColor;
	private Boolean isClicked = false;
	private String name;
	private Callable<Void> action;
	
	public MenuButton(float x, float y, float width, float height, Color color, Callable<Void> func, String name) {
		hitbox = new Rectangle(x, y, width, height);
		this.color = color;
		printColor = color;
		this.name = name;
		this.action = func;
	}
	
	public Boolean isTouchDown(float screenX, float screenY) {
		if (hitbox.contains(screenX, screenY)) {
			printColor = color.cpy().mul(0.5f);
			return (true);
		}
		return (false);
	}
	
	public Boolean isTouchUp(float screenX, float screenY) {
		if (hitbox.contains(screenX, screenY)) {
			printColor = color;
			isClicked = true;
			return (true);
		}
		printColor = color;
		return (false);
	}
	
	public Color getColor() {
		return (printColor);
	}
	
	public String getName() {
		return (name);
	}
	
	public Rectangle getRect() {
		return (hitbox);
	}
	
	public Boolean isClicked() {
		return (isClicked);
	}
	
	public void setClicked(Boolean isClicked) {
		this.isClicked = isClicked;
	}
	
	public void getAction() {
		try {
			action.call();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
