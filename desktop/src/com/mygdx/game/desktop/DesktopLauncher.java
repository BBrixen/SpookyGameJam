package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Client_Display.ClientGame;
import com.mygdx.game.Networking.Client_Side.ClientNetworker;

import java.io.IOException;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.height = 1080;
//		config.width = 1920;
		new LwjglApplication(new ClientGame(), config);
	}
}
