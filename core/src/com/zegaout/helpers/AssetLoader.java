package com.zegaout.helpers;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

	public static BitmapFont font, shadow;
	public static ArrayList<TextureRegion> helpImages = new ArrayList<TextureRegion>();
	public static Preferences prefs;
	public static Boolean isFirstLaunch = false;
	
	public static void load() {
		
		font = new BitmapFont(Gdx.files.internal("data/font.fnt"), true);
		shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"), true);
        font.setScale(0.25f, 0.25f);
        shadow.setScale(0.50f, 0.50f);
        
        helpImages.add(new TextureRegion(new Texture(Gdx.files.internal("data/1.png")), 1920, 1080));
        helpImages.add(new TextureRegion(new Texture(Gdx.files.internal("data/2.png")), 1920, 1080));
        helpImages.add(new TextureRegion(new Texture(Gdx.files.internal("data/3.png")), 1920, 1080));
        helpImages.add(new TextureRegion(new Texture(Gdx.files.internal("data/4.png")), 1920, 1080));
        helpImages.get(0).flip(false, true);
        helpImages.get(1).flip(false, true);
        helpImages.get(2).flip(false, true);
        helpImages.get(3).flip(false, true);
        
        prefs = Gdx.app.getPreferences("ZombieBird");

        if (!prefs.contains("isFirstLaunch")) {
           isFirstLaunch = true;
        }
	}
	
	public static void dispose() {
		font.dispose();
		shadow.dispose();
	}
}
