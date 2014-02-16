package org.hectordam.screen;

import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;

public class MenuPrincipal implements Screen{
	
	Juego juego;
	
	
	public MenuPrincipal(Juego juego){
		
		this.juego = juego;
	}


	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		juego.batch.begin();
		juego.font.draw(juego.batch, "Juego de Temerario", 100, 100);
		juego.font.draw(juego.batch, "Pulsa 'ENTER' para empezar", 100, 70);
		juego.font.draw(juego.batch, "Pulsa 'ESCAPE' para salir", 100, 40);
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
