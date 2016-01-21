package com.zegaout.cervus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zegaout.cervus.Cervus;
import com.zegaout.helpers.DesktopGoogleServices;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cervus";
		config.width = 320;
		config.height = 180;
		new LwjglApplication(new Cervus(new DesktopGoogleServices()), config);
	}
}
