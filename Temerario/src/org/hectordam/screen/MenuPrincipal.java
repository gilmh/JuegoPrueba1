package org.hectordam.screen;

import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

public class MenuPrincipal implements Screen{
	
	Juego juego;
	
	private Skin skin;
	private Stage stage;
	private Texture fondo;
	Music musica;
	
	public MenuPrincipal(Juego juego){
		
		this.juego = juego;
		fondo = new Texture(Gdx.files.internal("paramirapasa.png"));
		musica = Gdx.audio.newMusic(Gdx.files.internal("musicafondo.mp3"));
		
		musica.setLooping(true);
		musica.play();
	}


	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage = new Stage();
		
		Table tabla = new Table();
		tabla.setPosition(375, 40);
		tabla.setFillParent(true);
		tabla.setHeight(200);
		
		stage.addActor(tabla);
		
		TextButton boton = new TextButton("Jugar", getSkin());
		boton.setWidth(150);
		boton.setHeight(30);
		boton.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				juego.setScreen(new PantallaJuego(juego));
				dispose();
				
				return super.touchDown(event, x, y, pointer, button);
			}
			
		});
		tabla.addActor(boton);
		
		TextButton boton2 = new TextButton("Salir", getSkin());
		boton2.setPosition(boton.getOriginX(), boton.getOriginY() - 35);
		boton2.setWidth(150);
		boton2.setHeight(30);
		boton2.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				
				juego.dispose();
				System.exit(0);
				
				return super.touchDown(event, x, y, pointer, button);
			}
			
		});
		tabla.addActor(boton2);
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Gdx.input.setInputProcessor(stage);
		
		juego.batch.begin();
		juego.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		juego.font.draw(juego.batch, "Temerario por granvia", 25, 60);
		juego.font.draw(juego.batch, "Cruza la calle con el mayor numero de personas", 25, 40);
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
	
	protected Skin getSkin(){
		if(skin == null){
			skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
		}
		
		return skin;
	}
}
