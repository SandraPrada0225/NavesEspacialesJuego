package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import main.Window;
import math.Vector2D;
import states.GameState;

public class Meteoro extends MovingObject{
	
	private Size size;

	public Meteoro(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState, Size size) {
		super(position, velocity, maxVel, texture, gameState);
		this.size= size;
		this.velocity= velocity.scale(maxVel);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		position= position.add(velocity);
		if(position.getX()>Window.WIDTH) {
			position.setX(-width);	
		}
		if(position.getY()>Window.HEIGHT) {
			position.setY(-height);
		}
		
		if(position.getX()<-width) {
			position.setX(Window.WIDTH);
		}
		if(position.getY()<-height) {
			position.setY(Window.HEIGHT);
		}
		
		angle+= Constantes.DELTAANGLE/2;
	}
	
    @Override
	protected void Destroy() {
	gameState.diveMeteroros(this);
	gameState.addAcore(Constantes.METEORO_SCORE,position);
	super.Destroy();
		
	}
    
 

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2d= (Graphics2D)g;
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle,width/2,height/2);
	
		g2d.drawImage(texture, at, null);
	}

	public Size getSize() {
		return size;
	}

	
	
}
