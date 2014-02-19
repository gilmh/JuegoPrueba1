package org.hectordam.caracter;

import org.hectordam.manager.ResourceManager;
import org.hectordam.util.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personajes extends Caracter{
	
	public static enum State{
		RIGHT, LEFT, UP, DOWN, IDLE
	}
	
	private Util.Tipo tipo;
	
	public State state = State.IDLE;
	
	Animation animationRight;
	Animation animationLeft;
	Animation animationUp;
	Animation animationDown;
	TextureRegion idleFrame;

	public Personajes(Texture texture, float x, float y, float velocidad) {
		super(texture, x, y, velocidad);
		
		animationRight = new Animation(0.25f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("derecha1.png"))), 
				new Sprite(new Texture(Gdx.files.internal("derecha2.png")))});
		
		animationLeft = new Animation(0.25f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("izquierda1.png"))), 
				new Sprite(new Texture(Gdx.files.internal("izquierda2.png")))});
		
		animationUp = new Animation(0.25f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("arriba1.png"))), 
				new Sprite(new Texture(Gdx.files.internal("arriba2.png")))});
		
		animationDown = new Animation(0.25f, new TextureRegion[]{
				new Sprite(new Texture(Gdx.files.internal("abajo1.png"))), 
				new Sprite(new Texture(Gdx.files.internal("abajo2.png")))});
			
		idleFrame = new TextureRegion(texture);
	}
	
	@Override
	public void update(float dt) {
		
		stateTime += dt;
		switch (state) {
		case RIGHT:
			currentFrame = animationRight.getKeyFrame(stateTime, true);
			break;
		case LEFT:
			currentFrame = animationLeft.getKeyFrame(stateTime, true);
			break;
		case UP:
			currentFrame = animationUp.getKeyFrame(stateTime, true);
			break;
		case DOWN:
			currentFrame = animationDown.getKeyFrame(stateTime, true);
			break;
		case IDLE:
			currentFrame = idleFrame;
			break;
		default:
			currentFrame = idleFrame;
		}
		
		if (posicion.x <= 0)
			posicion.x = 0;
		
		if ((posicion.x + currentFrame.getRegionWidth()) > 600)
			posicion.x = 600 - currentFrame.getRegionWidth();
		
		if(posicion.y <= 0){
			posicion.y = 0;
		}
		
		rect.x = posicion.x;
		rect.y = posicion.y;
	}
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Util.Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Util.Tipo tipo) {
		this.tipo = tipo;
	}
}
