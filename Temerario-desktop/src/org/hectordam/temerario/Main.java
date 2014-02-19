package org.hectordam.temerario;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Temerario por Granvia";
		cfg.useGL20 = false;
		cfg.width = 600;
		cfg.height = 720;
		
		new LwjglApplication(new Juego(), cfg);
	}
}
