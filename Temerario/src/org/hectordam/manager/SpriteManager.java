package org.hectordam.manager;

import java.util.Iterator;

import org.hectordam.caracter.Objetos;
import org.hectordam.caracter.Personajes;
import org.hectordam.caracter.Vehiculos;
import org.hectordam.screen.GameOver;
import org.hectordam.screen.MenuPrincipal;
import org.hectordam.temerario.Juego;
import org.hectordam.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.sun.corba.se.spi.orbutil.fsm.State;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

public class SpriteManager {

	Personajes personaje;
	Objetos objeto;
	Array<Vehiculos> vehiculos;
	
	private long ultimoVehiculo;
	private long ultimoTranvia;
	private long ultimoBonus;
	private long tiempoCoche = 900;
	
	private float velocidad = 150f;
	private float velocidadPersonaje = 100f;
	
	private int cont = 0;
	
	private boolean bonus;
	
	Juego juego;
	private Texture fondo;
	Music musica;
	Sound sonidoFreno;
	Sound sonidoPito;
	Sound sonidoBonus;
	
	public SpriteManager(Juego juego){
		
		this.juego = juego;
		generarPersonaje();
		vehiculos = new Array<Vehiculos>();
		
		fondo = new Texture(Gdx.files.internal("Carretera.png"));
		musica = Gdx.audio.newMusic(Gdx.files.internal("musicafondo.mp3"));
		sonidoFreno = Gdx.audio.newSound(Gdx.files.internal("freno.wav"));
		sonidoPito = Gdx.audio.newSound(Gdx.files.internal("pito.wav"));
		sonidoBonus = Gdx.audio.newSound(Gdx.files.internal("bonus.wav"));
		
		musica.setLooping(true);
		musica.play();
	}
	
	public void render(SpriteBatch batch) {
		
		batch.begin();
		
		juego.batch.draw(fondo, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		juego.font.draw(juego.batch, "Salvados: " + juego.salvados, 10, 710);
		juego.font.draw(juego.batch, "Perdidos: " + juego.perdidos, 10, 690);
		juego.font.draw(juego.batch, "Puntos: " + juego.puntos, 260, 700);
		juego.font.draw(juego.batch, "Tiempo: " + juego.tiempo, 500, 700);
		
		if(bonus){
			objeto.render(batch);
		}
		
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
		
		if(TimeUtils.millis() - ultimoVehiculo > tiempoCoche){
			generarEnemigoAbajo();
			generarEnemigoArriba();
			
			ultimoVehiculo = TimeUtils.millis();
		}
		
		if(TimeUtils.millis() - ultimoTranvia > 1200){
			generarTranvia();
		}
		
		if(TimeUtils.millis() - ultimoBonus > 15000 && !bonus){
			generarObjetos();
		}
		
		if(personaje.posicion.y > 690){
			
			juego.salvados += 1;
			juego.puntos += 50;
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
				juego.puntos -= 20;
				generarPersonaje();
				cont += 1;
				if(vehiculo.isTranvia()){
					sonidoPito.play();
				}
				else{
					sonidoFreno.play();
				}
			}
		}
		
		if(personaje.rect.overlaps(objeto.rect) && bonus){
			sonidoBonus.play();
			juego.puntos += 10;
			switch(objeto.getTipo()){
			case RAYO:
				personaje.setTipo(objeto.getTipo());
				personaje.setSpeed(personaje.getSpeed() + 20);
				velocidadPersonaje += 20;
				break;
			case RELOJ:
				personaje.setTipo(objeto.getTipo());
				juego.tiempo += 15;
				break;
			case SEÑAL50:
				personaje.setTipo(objeto.getTipo());
				tiempoCoche += 80*cont;
				for(Vehiculos vehiculo: vehiculos){
					vehiculo.speed -= 25;
				}
				velocidad -= 25;
				break;
			case SEÑAL60:
				personaje.setTipo(objeto.getTipo());
				tiempoCoche += 60*cont;
				for(Vehiculos vehiculo: vehiculos){
					vehiculo.speed -= 20;
				}
				velocidad -= 20;
				break;
			case SEÑAL80:
				personaje.setTipo(objeto.getTipo());
				tiempoCoche += 30*cont;
				for(Vehiculos vehiculo: vehiculos){
					vehiculo.speed -= 10;
				}
				velocidad -= 10;
				break;
				default:
				break;
			}
			bonus = false;
			ultimoBonus = TimeUtils.millis();
		}
		
	}
	
	private void generarPersonaje(){
		
		personaje = new Personajes(new Texture("abajo1.png"), 300 - 16, 10, velocidadPersonaje);
	}
	
	private void generarObjetos(){
		
		int numbonus = (int) (Math.random()*3+1);
		
		float posx = (float) (Math.random()*460+50);
		float posy = (float) (Math.random()*480+130);
		
		switch(numbonus){
		case 1:
			objeto = new Objetos(new Texture("bonusRayo.png"), posx, posy, 0f);
			objeto.setTipo(Util.Tipo.RAYO);
			break;
		case 2:
			objeto = new Objetos(new Texture("bonusReloj.png"), posx, posy, 0f);
			objeto.setTipo(Util.Tipo.RELOJ);
			break;
		case 3:
			cont += 1;
			int num = (int) (Math.random()*3+1);
			
			switch(num){
			case 1:
				objeto = new Objetos(new Texture("senal50.png"), posx, posy, 0f);
				objeto.setTipo(Util.Tipo.SEÑAL50);
				break;
			case 2:
				objeto = new Objetos(new Texture("senal60.png"), posx, posy, 0f);
				objeto.setTipo(Util.Tipo.SEÑAL60);
				break;
			case 3:
				objeto = new Objetos(new Texture("senal80.png"), posx, posy, 0f);
				objeto.setTipo(Util.Tipo.SEÑAL80);
				break;
				default:
					break;
			}
			break;
			default:
				break;
		}
		bonus = true;
	}
	
	private void generarEnemigoAbajo(){
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			vehiculo = rellenarAbajo(num, 120f);
			break;
		case 2:
			vehiculo = rellenarAbajo(num, 180f);
			break;	
		case 3:
			vehiculo = rellenarAbajo(num, 240f);
			break;
		}
		vehiculo.setTranvia(false);
		vehiculos.add(vehiculo);
	}
	
	private Vehiculos rellenarAbajo(int num, float y){
		Vehiculos vehiculo = null;
		
		if(num < 15){
			vehiculo = new Vehiculos(new Texture("autobus2_derecha.png"), (0f - 64), y, velocidad);
			return vehiculo;
		}
		if(num < 50){
			vehiculo = new Vehiculos(new Texture("coche_derecha.png"), (0f - 64), y, velocidad);
			return vehiculo;
		}
		if(num < 75){
			vehiculo = new Vehiculos(new Texture("furgoneta_derecha.png"), (0f - 64), y, velocidad);
			return vehiculo;
		}
		if(num < 101){
			vehiculo = new Vehiculos(new Texture("Camion_derecha.png"), (0f - 64), y, velocidad);
			return vehiculo;
		}
		return vehiculo;
	}
	
	private void generarEnemigoArriba(){
		
		int carril = (int) (Math.random()*3+1);
		int num = (int) (Math.random()*100+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			vehiculo = rellenarArriba(num, 490f);
			break;
		case 2:
			vehiculo = rellenarArriba(num, 550f);
			break;
		case 3:
			vehiculo = rellenarArriba(num, 610f);
			break;
		}
		vehiculo.setTranvia(false);
		vehiculos.add(vehiculo);
		
	}
	
	private Vehiculos rellenarArriba(int num, float y){
		Vehiculos vehiculo = null;
		
		if(num < 15){
			vehiculo = new Vehiculos(new Texture("autobus2_izquierda.png"), 600, y, -velocidad);
			return vehiculo;
		}
		if(num < 50){
			vehiculo = new Vehiculos(new Texture("coche_izquierda.png"), 600, y, -velocidad);
			return vehiculo;
		}
		if(num < 75){
			vehiculo = new Vehiculos(new Texture("furgoneta_izquierda.png"), 600, y, -velocidad);
			return vehiculo;
		}
		if(num < 101){
			vehiculo = new Vehiculos(new Texture("Camion_izquierda.png"), 600, y, -velocidad);
			return vehiculo;
		}
		return null;
	}

	private void generarTranvia(){
		
		int carril = (int) (Math.random()*2+1);
		
		Vehiculos vehiculo = null;
		
		switch(carril){
		case 1:
			vehiculo = new Vehiculos(new Texture("tranvia_derecha.png"), 0 - 256, 300f, 250f);
			break;
			
		case 2:
			vehiculo = new Vehiculos(new Texture("tranvia_izquierda.png"), 600, 430f, -250f);
			break;
		}
		vehiculo.setTranvia(true);
		vehiculos.add(vehiculo);
	
		ultimoTranvia = TimeUtils.millis();
	}

}
