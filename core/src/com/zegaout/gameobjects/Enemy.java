package com.zegaout.gameobjects;

public class Enemy extends Scrollable{

	private Boolean isEat;
	
	public Enemy(float x, float y, float width, float height) {
		super(x, y, width, height);
		isEat = false;
	}

	@Override
	public Boolean isActive() {
		return !isTooNear() && !isScrolledLeft() && !isEat; 
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void reset() {
		rect.setSize(r.nextInt(30) + 10);
		position.set(startX, r.nextInt(180 - (int)rect.height) % (180 - rect.height));
		rect.setPosition(position.x, position.y);
		rect.getCenter(rectCenter);
		bigRect.setSize(rect.getHeight() + 25);
		bigRect.setCenter(rectCenter.x, rectCenter.y);
		isScrolledLeft = false;
		isEat = false;
	}
	
	public void setIsEat(Boolean isEat) {
		this.isEat = isEat;
	}
	
	public Boolean isEat() {
		return isEat;
	}
	
	public Boolean isEatable(float persoSize) {
		if (persoSize > rect.height) {
			return (true);
		}
		return (false);
	}
}
