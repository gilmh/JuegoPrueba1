package org.hectordam.manager;

import java.util.Iterator;

import org.hectordam.caracter.Personajes;
import org.hectordam.caracter.Vehiculos;
import org.hectordam.screen.GameOver;
import org.hectordam.screen.MenuPrincipal;
import org.hectordam.temerario.Juego;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.corba.se.spi.orbutil.fsm.State;

public class SpriteManager {

	Personajes personaje;
	Array<Vehiculos> vehiculos;
	
	private long ultimoVehiculo;
	private long ultimoTranvia;
	
	Juego juego;
	Texture fondo;
	
	public SpriteManager(Juego juego){
		
		this.juego = juego;
		generarPersonaje();
		vehiculos = new Array<Vehiculos>();
		
		fondo = new Texture(Gdx.files.internal("Carretera.png"));
	}
	
	public void render(SpriteBatch batch) {
		
		batch.begin();
		
		juego.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		juego.font.draw(juego.batch, "Salvados: " + juego.salvados, 10, 710);
		juego.font.draw(juego.batch, "Perdidos: " + juego.perdidos, 10, 690);
		juego.font.draw(juego.batch, "Tiempo: " + juego.tiempo, 500, 700);
		
		personaje.render(batch);
		for (Vehiculos enemy : vehiculos)
			enemy.render(batch);
		batch.end();
	}
	
	public void update(float dt) {
		juego.tiempo -= Gdx.graphics.getDeltaTime();
		
		if(juego.tiempo <= 0){
			juego.setScreen(new GameOver(juego));
		}
		
		if(TimeUtils.millis() - ultimoVehiculo > 800){
			generarEnemigoAbajo();
			generarEnemigoArriba();
			
			ultimoVehiculo = TimeUtils.millis();
		}
		
		if(TimeUtils.millis() - ultimoTranvia > 1200){
			generarTranvia();
		}
		
		if(personaje.posicion.y > 690){
			
			juego.salvados += 1;
			generarPersonaje();
		}
		
		personaje.update(dt);
		
		for (int i = vehiculos.size - 1; i >= 0; i--){
			vehiculos.get(i).posicion.x += vehiculos.get(i).speed * Gdx.graphics.getDeltaTime();
			vehiculos.get(i).update(dt);
			
			if(vehiculos.get(i).posicion.x < 0 - vehiculos.get(i).currentFrame.getRegionWidth() || vehiculos.get(i).posicion.x > 600){
				vehiculos.removeIndex(i);
			}
			
		}
		
		teclado(dt);
		colisiones();
		
	}
	
	private void teclado(float dt){
		
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			personaje.state = personaje.state.LEFT;
			personaje.posicion.x += personaje.speed * -dt;
		}
		else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			personaje.state = personaje.state.RIGHT;
			personaje.posicion.x += personaje.speed * dt;
		}
		else if (Gdx.input.isKeyPressed(Keys.UP)) {
			personaje.state = personaje.state.UP;
			personaje.posicion.y += personaje.speed * dt;
		}
		else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			personaje.state = personaje.state.DOWN;
			personaje.posicion.y += personaje.speed * -dt;
		}
		else 
			personaje.state = personaje.state.IDLE;
		
	}
	
	private void colisiones(){
		
		for(Vehiculos vehiculo: vehiculos){
			if(vehiculo.rect.overlaps(personaje.rect)){
				juego.perdidos += 1;
				generarPersonaje();
			}
		}
		
	}
	
	private void generarPersonaje(){
		
		personaje = new Personajes(new Texture("abajo1.png"), 300 - 16, 10, 100);
	}
	
	private void generarEnemigoAbajo(){
		
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f - 64), 120f, 150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 120f, 150f);
			}
			break;
			
		case 2:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f - 64), 180f, 150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 180f, 150f);
			}
			break;
			
		case 3:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f -64), 240f, 150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 240f, 150f);
			}
			break;
		
		}
		
		vehiculos.add(vehiculo);
		
	}
	
	private void generarEnemigoArriba(){
		
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 490f, -150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 490f, -150f);
			}
			break;
			
		case 2:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 550f, -150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 550f, -150f);
			}
			break;
			
		case 3:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 610f, -150f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 610f, -150f);
			}
			break;
		
		}
		vehiculos.add(vehiculo);
		
	}

	private void generarTranvia(){
		
		int carril = (int) (Math.random()*2+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			vehiculo = new Vehiculos(new Texture("tranvia_derecha.png"), 0 - 256, 310f, 250f);
			break;
			
		case 2:
			vehiculo = new Vehiculos(new Texture("tranvia_izquierda.png"), 600, 430f, -250f);
			break;
		
		}
		
		vehiculos.add(vehiculo);
	
	
		ultimoTranvia = TimeUtils.millis();
	}

}
