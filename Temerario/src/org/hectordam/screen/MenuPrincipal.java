package org.hectordam.screen;

import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class MenuPrincipal implements Screen{
	
	Juego juego;
	
	private Texture fondo;
	
	public MenuPrincipal(Juego juego){
		
		this.juego = juego;
		fondo = new Texture(Gdx.files.internal("paramirapasa.png"));
	}


	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		juego.batch.begin();
		juego.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		juego.font.draw(juego.batch, "Temerario por granvia", 25, 60);
		juego.font.draw(juego.batch, "Cruza la calle con el mayor numero de personas", 25, 40);
		juego.font.draw(juego.batch, "Pulsa 'ENTER' para empezar", 375, 60);
		juego.font.draw(juego.batch, "Pulsa 'ESCAPE' para salir", 375, 40);
		juego.batch.end();
		
		handleInput();
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			juego.dispose();
			System.exit(0);
		}
		else if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			juego.setScreen(new PantallaJuego(juego));
		}
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
