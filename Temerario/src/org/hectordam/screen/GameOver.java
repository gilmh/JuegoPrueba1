package org.hectordam.screen;

import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class GameOver implements Screen{

	Juego juego;
	private Texture fondo1;
	private Texture fondo2;
	
	public GameOver(Juego juego){
		
		this.juego = juego;
		
		fondo1 = new Texture(Gdx.files.internal("fuegos.png"));
		fondo2 = new Texture(Gdx.files.internal("ambulancia.png"));
	}


	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		juego.batch.begin();
		if(juego.puntos > 0){
			juego.batch.draw(fondo1, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		else{
			juego.batch.draw(fondo2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		
		juego.font.draw(juego.batch, "Has conseguido " + juego.puntos + " puntos", 30, 70);
		juego.font.draw(juego.batch, "Has conseguido cruzar " + juego.salvados + " veces", 30, 50);
		juego.font.draw(juego.batch, "Te han pillado " + juego.perdidos + " veces", 30, 30);
		juego.font.draw(juego.batch, "Pulsa 'ENTER' para volver a jugar", 350, 60);
		juego.font.draw(juego.batch, "Pulsa 'ESCAPE' para salir", 350, 40);
		juego.batch.end();
		
		handleInput();
	}
	
	private void handleInput() {
		
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			juego.dispose();
			System.exit(0);
		}
		else if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			
			juego.tiempo = 100;
			juego.salvados = 0;
			juego.perdidos = 0;
			juego.puntos = 0;
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
