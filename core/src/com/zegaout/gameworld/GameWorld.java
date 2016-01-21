package com.zegaout.gameworld;

import com.zegaout.cervus.Cervus;
import com.zegaout.gameobjects.Perso;
import com.zegaout.gameobjects.ScrollHandler;
import com.zegaout.screens.GameScreen;
import com.zegaout.ui.MenuHandler;
import com.zegaout.ui.ReplayHandler;

public class GameWorld {

	private GameScreen screen;
	private ScrollHandler scrollHandler;
	private GameState currentState;
	private MenuHandler menuHandler;
	private ReplayHandler replayHandler;
	private float score = 0;
	private float periodTime = 0;
	private int nbPeriodParty = 0;
	private float countdown = 0.5f;
	private float highScore = -1;
	private Boolean isHighScore = false;
	private float helpRunTime = 0;
	
	private Perso perso;
	
	public GameWorld(GameScreen screen) {
		this.screen = screen;
		this.perso = new Perso(this.screen);
		this.scrollHandler = new ScrollHandler(this);
		scrollHandler.setScrollSpeed(-10);
		currentState = GameState.MENU;
		this.menuHandler = new MenuHandler(this);
		this.replayHandler = new ReplayHandler(this);
		Cervus.googleServices.getScore(this);
	}
	
	public enum GameState {
        MENU, RUNNING, REPLAY, HELP
    }
	
	public void update(float delta, float runTime) {
		switch (currentState) {
        case MENU:
            updateMenu(delta, runTime);
            break;

        case RUNNING:
            updateRunning(delta, runTime);
            break;
            
        case REPLAY:
        	updateReplay(delta);
        	break;
            
        case HELP:
        	updateHelp(delta);
        	break;
        	
        default:
            break;
        }
	}
	
	private void updateHelp(float delta) {
		helpRunTime += (delta / 10);
		if (helpRunTime > 1) {
			setState(GameState.RUNNING);
			helpRunTime = 0;
		}
	}

	public void updateMenu(float delta, float runTime) {
		scrollHandler.setScrollSpeed(-10);
		scrollHandler.update(delta, runTime);
		menuHandler.isClicked();
	}
	
	public void updateRunning(float delta, float runTime) {
		if (!perso.isDead()) {
           	score += delta;
           	periodTime += delta;
			perso.update(delta);
			scrollHandler.setScrollSpeed(-260 + (getPerso().getHungry() * 1.8f));
			scrollHandler.update(delta, runTime);
			if (scrollHandler.collides() || scrollHandler.collidesEnemies()) {
				perso.setIsDead(true);
				nbPeriodParty += 1;
				if (periodTime < 30 || nbPeriodParty < 3) {
					if (highScore < 0 || highScore > score) {
						reset();
					} else {
						countdown = 2f;
						isHighScore = true;
					}
				}
			}
		} else {
			countdown -= delta;
			if (countdown < 0) {
				periodTime = 0;
				nbPeriodParty = 0;
				isHighScore = false;
				Cervus.googleServices.submitScore((int) score);
				setState(GameState.REPLAY);
			}
		}
	}
	
	public void updateReplay(float delta) {
		replayHandler.isClicked();
	}
	
	private void reset() {
		if (highScore < 0 || highScore > score)
			Cervus.googleServices.submitScore((int) score);
		perso.reset();
		scrollHandler.reset();
		score = 0;
		countdown = 0.5f;
		Cervus.googleServices.getScore(this);
	}
	
	public void setState(GameState state) {
		currentState = state;
		switch (currentState) {
        case MENU:
            break;

        case RUNNING:
            reset();
            break;
            
        default:
            break;
        }
	}
	
	public Perso getPerso() {
		return this.perso;
	}
	
	public GameState getCurrentState() {
		return (currentState);
	}
	
	public GameScreen getScreen() {
		return screen;
	}
	
	public MenuHandler getMenuHandler() {
		return (menuHandler);
	}
	
	public ReplayHandler getReplayHandler() {
		return (replayHandler);
	}
	
	public Integer getScore() {
		return (int) score;
	}
	
	public ScrollHandler getScrollHandler() {
		return scrollHandler;
	}
	
	public float getHighScore() {
		return (highScore);
	}
	
	public Boolean isHighScore() {
		return (isHighScore);
	}
	
	public float getHelpRuntime() {
		return (this.helpRunTime);
	}
	
	public void setHighScore(float highScore) {
		this.highScore = highScore;
	}
}
