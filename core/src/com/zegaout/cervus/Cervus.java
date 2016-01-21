package com.zegaout.cervus;

import com.badlogic.gdx.Game;
import com.zegaout.helpers.AssetLoader;
import com.zegaout.helpers.IGoogleServices;
import com.zegaout.screens.GameScreen;

public class Cervus extends Game {
	
	public static IGoogleServices googleServices;
	
	public Cervus(IGoogleServices googleServices) {
		super();
		Cervus.googleServices = googleServices;
	}
	
	@Override
	public void create() {
		AssetLoader.load();
		setScreen(new GameScreen());
	}
	
	@Override
	public void dispose() {
		super.dispose();
		AssetLoader.dispose();
	}
}
