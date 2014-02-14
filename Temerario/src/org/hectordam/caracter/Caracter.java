package org.hectordam.caracter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Caracter {

	public Vector2 posicion;
	public float speed;
	
	public Animation animacion;
	public TextureRegion currentFrame;
	public float stateTime;
	public Rectangle rect;
	
	
	public Caracter(Animation animacion, float x, float y, float velocidad){
		
		posicion = new Vector2(x, y);
		this.animacion = animacion;
		speed = velocidad;
		
	}
	
	public void mover(Vector2 mover){
		
		mover.scl(speed);
		posicion.add(mover);
		rect.x = posicion.x;
		rect.y = posicion.y;
	}

	public void recder(SpriteBatch batch){
		
		batch.draw(currentFrame, posicion.x, posicion.y);
	}
	
	public void update(float dt){
		
		stateTime += dt;
		currentFrame = animacion.getKeyFrame(stateTime, true);
		
		if(rect == null){
			rect = new Rectangle(posicion.x, posicion.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
		}
		
	}
	
}
