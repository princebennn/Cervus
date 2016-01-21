package com.zegaout.ui;

import java.util.ArrayList;
import com.zegaout.gameworld.GameWorld;

public class Menu {

	protected GameWorld world;
	protected ArrayList<MenuButton> buttons = new ArrayList<MenuButton>();
	
	public Menu(GameWorld world) {
		this.world = world;
		initMenu();
	}
	
	public Boolean isClicked() {
		for(int i = 0; i < buttons.size(); i++) {
			MenuButton btn = buttons.get(i);
			if (btn.isClicked()) {
				btn.getAction();
				btn.setClicked(false);
				return (true);
			}
		}
		return (false);
	}
	
	public Boolean isTouchDown(float screenX, float screenY) {
		for(int i = 0; i < buttons.size(); i++) {
			MenuButton btn = buttons.get(i);
			if (btn.isTouchDown(screenX, screenY)) {
				return (true);
			}
		}
		return (false);
	}
	
	public Boolean isTouchUp(float screenX, float screenY) {
		for(int i = 0; i < buttons.size(); i++) {
			MenuButton btn = buttons.get(i);
			if (btn.isTouchUp(screenX, screenY)) {
				return (true);
			}
		}
		return (false);
	}
	
	protected void initMenu() {
		
	}
	
	public ArrayList<MenuButton> getButtons() {
		return (buttons);
	}
}
