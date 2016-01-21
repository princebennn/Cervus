package com.zegaout.ui;

import java.util.concurrent.Callable;

import com.badlogic.gdx.graphics.Color;
import com.zegaout.cervus.Cervus;
import com.zegaout.gameworld.GameWorld;
import com.zegaout.gameworld.GameWorld.GameState;
import com.zegaout.helpers.AssetLoader;


public class MenuHandler extends Menu{

	public MenuHandler(GameWorld world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initMenu() {
		Callable<Void> func = new Callable<Void>() {
			public Void call() {
				if (AssetLoader.isFirstLaunch) {
					world.setState(GameState.HELP);
					AssetLoader.prefs.putBoolean("isFirstLaunch", false);
					AssetLoader.prefs.flush();
					AssetLoader.isFirstLaunch = false;
				} else {
					world.setState(GameState.RUNNING);
				}
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() / 2 - 38, 60, 80, 80, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "Play"));
		func = new Callable<Void>() {
			public Void call() {
				Cervus.googleServices.showScores();
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() / 3 - 75, 100, 60, 60, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "Rank"));
		func = new Callable<Void>() {
			public Void call() {
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() - (world.getScreen().getGameX() / 3) + 17, 100, 60, 60, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "About"));
		func = new Callable<Void>() {
			public Void call() {
				world.setState(GameState.HELP);
				return null;
			}
		};
		buttons.add(new MenuButton(world.getScreen().getGameX() - 25, 0, 25, 25, new Color(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 1f), func, "?"));
	}
	
}
