package com.mygdx.finalgangwar;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.finalgangwar.FinalGangWar;
import com.mygdx.finalgangwar.mariobros.MarioBros;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Final Gang War");
		config.setWindowedMode(1300, 624);
		new Lwjgl3Application(new MarioBros(), config);
	}
}
