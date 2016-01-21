package com.zegaout.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.zegaout.gameworld.GameRenderer;
import com.zegaout.gameworld.GameWorld;
import com.zegaout.helpers.InputHandler;

public class GameScreen implements Screen{

	private GameWorld world;
    private GameRenderer renderer;
    private float runTime;
    private float screenX;
    private float screenY;
    private float gameX;
    private float gameY;
    
    public GameScreen() {
    	this.screenX = Gdx.graphics.getWidth();
        this.screenY = Gdx.graphics.getHeight();
        this.gameY = 180;
        this.gameX = this.screenX / (this.screenY / this.gameY);
        this.world = new GameWorld(this);
        Gdx.input.setInputProcessor(new InputHandler(world, this.screenX / this.gameX, this.screenY / this.gameY, gameX));
        this.renderer = new GameRenderer(world, this.gameX, this.gameY);
    }
	
	@Override
	public void render(float delta) {
		runTime += delta;
        world.update(delta, runTime);
        renderer.render(delta, runTime);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public float getScreenX() {
		return this.screenX;
	}
	
	public float getScreenY() {
		return this.screenY;
	}
	
	public float getGameX() {
		return this.gameX;
	}
	
	public float getGameY() {
		return this.gameY;
	}
}
