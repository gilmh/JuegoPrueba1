package org.hectordam.screen;

import org.hectordam.manager.ResourceManager;
import org.hectordam.manager.SpriteManager;
import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class PantallaJuego implements Screen{

	Juego juego;
	SpriteManager spriteManager;
	
	Texture fondo;
	
	public PantallaJuego(Juego juego){
		
		this.juego = juego;
		
		fondo = new Texture(Gdx.files.internal("Carretera.png"));
		
		ResourceManager.cargarTodosRecursos();
		
		spriteManager = new SpriteManager();
		
		
	}

	@Override
	public void render(float dt) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
		juego.batch.begin();
		juego.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		juego.batch.end();
		
		spriteManager.update(dt);
		spriteManager.render(juego.batch);
		
	}
	
	@Override
	public void dispose() {
		
		
	}
	
	@Override
	public void show() {
		
		
	}

	@Override
	public void resize(int width, int height) {
	}

	

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}
