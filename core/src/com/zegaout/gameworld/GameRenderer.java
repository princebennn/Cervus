package com.zegaout.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.zegaout.gameobjects.Enemy;
import com.zegaout.gameobjects.Obstacle;
import com.zegaout.gameworld.GameWorld.GameState;
import com.zegaout.helpers.AssetLoader;
import com.zegaout.ui.MenuButton;

public class GameRenderer {

	private ShapeRenderer shapeRenderer;
	private OrthographicCamera cam;
	private GameWorld myWorld;
	private float gameWidth;
	private float gameHeight;
	private SpriteBatch batcher;
	private ArrayList<TextureRegion> helpImages;
	
	public GameRenderer(GameWorld world, float gameWidth, float gameHeight) {
		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		this.myWorld = world;
		
        cam = new OrthographicCamera(gameWidth, 180);
        cam.setToOrtho(true, gameWidth, 180);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initAssets();
    }
	
	public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (myWorld.getCurrentState() == GameState.MENU) {
        	renderMenu(delta, runTime);
        } else if (myWorld.getCurrentState() == GameState.RUNNING) {
        	renderRunning(delta, runTime);
        } else if (myWorld.getCurrentState() == GameState.REPLAY) {
        	renderReplay(delta, runTime);
        } else if (myWorld.getCurrentState() == GameState.HELP) {
        	renderHelp(delta);
        }
	}
	
	private void drawObstacle() {
		if (myWorld.getCurrentState() == GameState.MENU || myWorld.getCurrentState() == GameState.REPLAY) {
			Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
	        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		}
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor( 255 / 255.0f, 241 / 255.0f, 171 / 255.0f, 1f);
		shapeRenderer.rect(0, 0, this.gameWidth, this.gameHeight);
		
        shapeRenderer.setColor(178 / 255.0f, 113 / 255.0f, 73 / 255.0f, 0.2f);
        ArrayList<Obstacle> objs = myWorld.getScrollHandler().getObstacles();
        Rectangle r;
        for(int i = 0; i < objs.size(); i++) {
			Obstacle obj = objs.get(i);
			r = obj.getRect();
			shapeRenderer.rect(r.x, r.y, r.width, r.height);
		}
        shapeRenderer.end();
		
		if (myWorld.getCurrentState() == GameState.MENU || myWorld.getCurrentState() == GameState.REPLAY) {
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}
	
	private void drawMenu() {
		shapeRenderer.begin(ShapeType.Filled);
		ArrayList<MenuButton> btns = null;
		if (myWorld.getCurrentState() == GameState.MENU) {
			btns = myWorld.getMenuHandler().getButtons();
		} else if (myWorld.getCurrentState() == GameState.REPLAY){
			btns = myWorld.getReplayHandler().getButtons();
		}
        Rectangle r;
		for(int i = 0; i < btns.size(); i++) {
			MenuButton obj = btns.get(i);
			r = obj.getRect();
			shapeRenderer.setColor(obj.getColor());
			shapeRenderer.rect(r.x, r.y, r.width, r.height);
			
		}
        shapeRenderer.end();
        batcher.begin();
        AssetLoader.shadow.setScale(0.25f, 0.25f);
        AssetLoader.shadow.draw(batcher, "Cervus", gameWidth / 2 - (3 * 17), 12);
        AssetLoader.font.setScale(0.15f, 0.15f);
        for(int i = 0; i < btns.size(); i++) {
			MenuButton obj = btns.get(i);
			r = obj.getRect();
			AssetLoader.font.draw(batcher, obj.getName(), r.x + (r.width / 2) - (obj.getName().length() / 2f * 11), r.y + (r.height / 2) - 8);
		}
        batcher.end();
	}
	
	private void drawEnemy() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(167 / 255.0f, 216 / 255.0f, 107 / 255.0f, 1f);
        ArrayList<Enemy> enemies = myWorld.getScrollHandler().getEnemies();
        for(int i = 0; i < enemies.size(); i++) {
			Enemy obj = enemies.get(i);
			Rectangle r = obj.getRect();
			shapeRenderer.rect(r.x, r.y, r.width, r.height);
		}
        shapeRenderer.end();
	}
	
	private void drawPerso() {
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(myWorld.getPerso().getColor());
        shapeRenderer.rect(this.myWorld.getPerso().getX(), this.myWorld.getPerso().getY(), this.myWorld.getPerso().getSizeX(), this.myWorld.getPerso().getSizeY());
        shapeRenderer.rect(this.myWorld.getPerso().getHungry() / 100 * gameWidth, 0, gameWidth, 2);
        shapeRenderer.end();
	}
	
	public void drawScore() {
		batcher.begin();
        AssetLoader.font.setScale(0.15f, 0.15f);
        AssetLoader.font.draw(batcher, myWorld.getScore().toString(), (gameWidth / 2) - (myWorld.getScore().toString().length() / 2f * 11), 10 - 8);
        batcher.end();
	}
	
	public void drawNewHighScore() {
		if (myWorld.isHighScore()) {
			batcher.begin();
	        AssetLoader.shadow.setScale(0.15f, 0.15f);
	        String text = "New HighScore";
	        AssetLoader.shadow.draw(batcher, text, (gameWidth / 2) - (text.length() / 2f * 11), 78 - 8);
	        AssetLoader.shadow.draw(batcher, myWorld.getScore().toString(), (gameWidth / 2) - (myWorld.getScore().toString().length() / 2f * 11), 102 - 8);
	        batcher.end();
		}
	}
	
	private void initAssets() {
        helpImages = AssetLoader.helpImages;
    }
	
	public void renderHelp(float delta) {
		batcher.begin();
		float helpRuntime = myWorld.getHelpRuntime();
		if (helpRuntime < 0.25f) {
			batcher.draw(helpImages.get(0), 0, 0, gameWidth, gameHeight);
		} else if (helpRuntime > 0.25f && helpRuntime < 0.65f) {
			batcher.draw(helpImages.get(1), 0, 0, gameWidth, gameHeight);
		} else if (helpRuntime > 0.65f && helpRuntime < 0.90f) {
			batcher.draw(helpImages.get(2), 0, 0, gameWidth, gameHeight);
		} else if (helpRuntime > 0.90f && helpRuntime < 1f) {
			batcher.draw(helpImages.get(3), 0, 0, gameWidth, gameHeight);
		}
		batcher.end();
	}
	
	public void renderReplay(float delta, float runTime) {
		drawObstacle();
		drawMenu();
	}
	
	public void renderMenu(float delta, float runTime) {
		drawObstacle();
        drawMenu();
	}
	
	public void renderRunning(float delta, float runTime) {
		drawObstacle();
		drawEnemy();
		drawPerso();
		drawScore();
		drawNewHighScore();
	}
}
