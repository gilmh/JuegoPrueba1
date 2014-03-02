package org.hectordam.temerario;

import org.hectordam.screen.MenuPrincipal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	OrthographicCamera camera;
	
	public int salvados = 0;
	public int perdidos = 0;
	public float tiempo = 5;
	public int puntos = 0;
	
	@Override
	public void create() {		
		
		batch = new SpriteBatch();
		font = new BitmapFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(true, 600, 720);
		camera.update();
		
		Texture.setEnforcePotImages(false);
		
		setScreen(new MenuPrincipal(this));
	}

	@Override
	public void dispose() {
		
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
