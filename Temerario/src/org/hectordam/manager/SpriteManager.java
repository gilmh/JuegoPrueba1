package org.hectordam.manager;

import org.hectordam.caracter.Personajes;
import org.hectordam.caracter.Vehiculos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.corba.se.spi.orbutil.fsm.State;

public class SpriteManager {

	Personajes personaje;
	Array<Vehiculos> vehiculos;
	
	private long ultimoVehiculo;
	private long ultimoTranvia;
	
	public SpriteManager(){
		
		vehiculos = new Array<Vehiculos>();
	}
	
	public void render(SpriteBatch batch) {
		
		batch.begin();
		
		for (Vehiculos enemy : vehiculos)
			enemy.render(batch);
		batch.end();
	}
	
	public void update(float dt) {
		
		if(TimeUtils.millis() - ultimoVehiculo > 550){
			generarEnemigoAbajo();
			generarEnemigoArriba();
			
			ultimoVehiculo = TimeUtils.millis();
		}
		if(TimeUtils.millis() - ultimoTranvia > 1050){
			generarTranvia();
		}
		
		for (int i = vehiculos.size - 1; i >= 0; i--){
			vehiculos.get(i).posicion.x += vehiculos.get(i).speed * Gdx.graphics.getDeltaTime();
			vehiculos.get(i).update(dt);
			
			if(vehiculos.get(i).posicion.x < 0 - vehiculos.get(i).currentFrame.getRegionWidth() || vehiculos.get(i).posicion.x > 600){
				vehiculos.removeIndex(i);
			}
		}
		
	}
	
	public void generarEnemigoAbajo(){
		
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f - 64), 120f, 200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 120f, 200f);
			}
			break;
			
		case 2:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f - 64), 180f, 200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 180f, 200f);
			}
			break;
			
		case 3:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_derecha.png"), (0f -64), 240f, 200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 32), 240f, 200f);
			}
			break;
		
		}
		
		vehiculos.add(vehiculo);
		
	}
	
	public void generarEnemigoArriba(){
		
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 490f, -200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 490f, -200f);
			}
			break;
			
		case 2:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 550f, -200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 550f, -200f);
			}
			break;
			
		case 3:
			if(num < 20){
				vehiculo = new Vehiculos(new Texture("autobus_izquierda.png"), 600, 610f, -200f);
			}
			else{
				vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, 610f, -200f);
			}
			break;
		
		}
		vehiculos.add(vehiculo);
		
	}

	public void generarTranvia(){
		
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
