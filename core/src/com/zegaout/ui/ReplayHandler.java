package com.zegaout.ui;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.zegaout.gameworld.GameWorld;
import com.zegaout.gameworld.GameWorld.GameState;

public class ReplayHandler extends Menu{

	public ReplayHandler(GameWorld world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initMenu() {
		Callable<Void> func = new Callable<Void>() {
			public Void call() {
				world.setState(GameState.RUNNING);
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() / 4 - 38, 60, 80, 80, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "Replay"));
		func = new Callable<Void>() {
			public Void call() {
				world.setState(GameState.MENU);
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() - (world.getScreen().getGameX() / 4) - 38, 60, 80, 80, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "Menu"));
	}	
}
