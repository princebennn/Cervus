package com.zegaout.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.zegaout.gameworld.GameWorld;
import com.zegaout.gameworld.GameWorld.GameState;

public class InputHandler implements InputProcessor{

    private float scaleFactorX;
    private float scaleFactorY;
    private GameWorld myWorld;
    private float midGameX;
    private float lastX;
    private float lastY;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY, float gameX) {
        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        this.myWorld = world;
        this.midGameX = gameX / 2;
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        
        
        if (myWorld.getCurrentState() == GameState.MENU || myWorld.getCurrentState() == GameState.REPLAY) {
        	touchDownMenu(screenX, screenY);
        } else if (myWorld.getCurrentState() == GameState.RUNNING) {
        	touchDownRunning(screenX, screenY);
        }
        return true;
    }
    
    private void touchDownMenu(int screenX, int screenY) {
    	if (myWorld.getCurrentState() == GameState.MENU) {
    		myWorld.getMenuHandler().isTouchDown(screenX, screenY);
    	} else if (myWorld.getCurrentState() == GameState.REPLAY) {
    		myWorld.getReplayHandler().isTouchDown(screenX, screenY);
    	}
    }

    private void touchDownRunning(int screenX, int screenY) {
    	myWorld.getPerso().setIsMoving(true);
        if (screenX > midGameX) {
        	//Because its side of growing
        	lastX = screenX;
        }
        else {
        	lastY = screenY;
        	//myWorld.getPerso().setDestinationY(screenY);
        }
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	
    	if (myWorld.getCurrentState() == GameState.MENU || myWorld.getCurrentState() == GameState.REPLAY) {
    		screenX = scaleX(screenX);
            screenY = scaleY(screenY);
        	touchUpMenu(screenX, screenY);
        } else if (myWorld.getCurrentState() == GameState.RUNNING) {
        	touchUpRunning(screenX, screenY);
        }
    	
        return false;
    }

    private void touchUpMenu(int screenX, int screenY) {
    	if (myWorld.getCurrentState() == GameState.MENU) {
    		myWorld.getMenuHandler().isTouchUp(screenX, screenY);
    	} else if (myWorld.getCurrentState() == GameState.REPLAY) {
    		myWorld.getReplayHandler().isTouchUp(screenX, screenY);
    	}
    }
    
    private void touchUpRunning(int screenX, int screenY) {
    	myWorld.getPerso().setIsMoving(false);
    	//myWorld.getPerso().setDestinationY(myWorld.getPerso().getY());
    }
    
    @Override
    public boolean keyDown(int keycode) {
    	if(keycode == Keys.BACK){
            System.out.println("back");
            if (myWorld.getCurrentState() == GameState.MENU) {
            	Gdx.app.exit();
            } else {
            	myWorld.setState(GameState.MENU);
            }
         }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    	
    	if (myWorld.getCurrentState() == GameState.RUNNING) {
    		screenX = scaleX(screenX);
            screenY = scaleY(screenY);

    		touchDraggedRunning(screenX, screenY);
        }
    	
        return false;
    }

    private void touchDraggedRunning(int screenX, int screenY) {
    	myWorld.getPerso().setIsMoving(true);
        if (screenX > midGameX) {
        	//Because its side of growing
        	myWorld.getPerso().setResizing(myWorld.getPerso().getResizing() + (screenX - lastX));
        	lastX = screenX;
        }
        else {
        	System.out.println("dragged !!!!");
        	myWorld.getPerso().setDestinationY(myWorld.getPerso().getDestinationY() + ((float)screenY - lastY) * 1.2f);
        	float midSize = myWorld.getPerso().getSizeY() / 2;
        	if (myWorld.getPerso().getDestinationY() > myWorld.getScreen().getGameY() - midSize) {
        		myWorld.getPerso().setDestinationY(myWorld.getScreen().getGameY() - midSize);
        	} else if (myWorld.getPerso().getDestinationY() < 0 + midSize) {
        		myWorld.getPerso().setDestinationY(0 + midSize);
        	}
        	lastY = screenY;
        }
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }
}