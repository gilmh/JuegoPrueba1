package org.hectordam.caracter;

import org.hectordam.manager.ResourceManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personajes extends Caracter{
	
	public static enum State{
		RIGHT, LEFT, UP, DOWN, IDLE
	}
	
	public State state = State.IDLE;
	
	Animation animationRight;
	Animation animationLeft;
	Animation animationUp;
	Animation animationDown;
	TextureRegion idleFrame;

	public Personajes(Texture texture, float x, float y, float velocidad) {
		super(texture, x, y, velocidad);
		
		idleFrame = new TextureRegion(texture);
		animationRight = ResourceManager.getAnimation("derecha");
		animationLeft = ResourceManager.getAnimation("izquierda");
		animationUp = ResourceManager.getAnimation("arriba");
		animationDown = ResourceManager.getAnimation("abajo");
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
	}
	
}
