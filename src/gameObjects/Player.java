package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import main.Window;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Sound;
import input.KeyBoard;
import math.Vector2D;
import states.GameState;

public class Player extends MovingObject {
	
	private Vector2D heading;
	private Vector2D aceleracion;
	private final double ACC=0.02;
	private final double DeltaAngule= 0.1;
	private boolean acelerando= false;
	private Cronometro cronometro;
	
	private boolean spawing, visible;
	
	private Cronometro parpadeoTiempo, aparecerTiempo;
	
	private Sound disparar,gameOver, finalizar;
	

	public Player(Vector2D position, Vector2D velocity,double maxVel,  BufferedImage texture,GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);
		heading= new Vector2D(0,1);
		aceleracion= new Vector2D();
		cronometro= new Cronometro();
		aparecerTiempo= new Cronometro();
		parpadeoTiempo= new Cronometro();
		disparar= new Sound(Assets.laserPlayersonido);
		gameOver= new Sound(Assets.perder);
		finalizar= new Sound(Assets.gameOversonido);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		
		
		
		if(!aparecerTiempo.isRunning()){
			spawing=false;
			visible= true;
		}	
		
		if(spawing) {
			if(!parpadeoTiempo.isRunning()) {
				parpadeoTiempo.run(Constantes.PARPADEO_TIME);
				visible=!visible;
			}
		}
		
		if(KeyBoard.DISPARAR && !cronometro.isRunning() && !spawing) {
			gameState.getMovingObjects().add(0,new Laser(getCenter().add( heading.scale(width)),
					heading, 10, angle, Assets.laserrojo,gameState));
			cronometro.run(Constantes.FIRERATE);
			
			disparar.play(); 
		}
		
		if(disparar.getFrameProsition()>8500) {
			disparar.stop();
		}
		
		
		if(KeyBoard.RIGHT) {
			angle+=Math.PI/20;
		}if(KeyBoard.LEFT) {
			angle-=DeltaAngule;
			
		}
		if(KeyBoard.UP) {
			aceleracion=heading.scale(ACC);
			acelerando=true;
		}else {
			if(velocity.getMagnitude()!=0) {
				aceleracion= (velocity.scale(-1).normalizar()).scale(ACC/2);
				acelerando=false;
			}
		}
		
		velocity= velocity.add(aceleracion);
		velocity=velocity.limit(maxVel);
		
		heading= heading.setDirection(angle-Math.PI/2);//siempre se trabaja en radianes y no en grados 
	
		position=position.add(velocity);
		if(position.getX()>Window.WIDTH) {
			position.setX(0);	
		}
		if(position.getY()>Window.HEIGHT) {
			position.setY(0);
		}
		
		if(position.getX()<0) {
			position.setX(Window.WIDTH);
		}
		if(position.getY()<0) {
			position.setY(Window.HEIGHT);
		}
		
		
		cronometro.update();
		aparecerTiempo.update();
		parpadeoTiempo.update();
		
		colicion();
	}
	
  @Override
	protected void Destroy() {
		// TODO Auto-generated method stub
		spawing= true;
		aparecerTiempo.run(Constantes.APARECER_TIME);
		gameOver.play();
		
		if(!gameState.substractLife()) {
			finalizar.play();
			gameState.gameOver();
			super.Destroy();
		}
		resetValues();
	}
  
  private void resetValues() {
	  angle=0;
	  velocity= new Vector2D();
	  position= new Vector2D(Constantes.WIDTH/2-Assets.player.getWidth()/2,Constantes.WIDTH/2-Assets.player.getHeight()/2);
  }

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
		if(!visible) 
			return;
		
		Graphics2D g2d= (Graphics2D)g;
		
		AffineTransform at1= AffineTransform.getTranslateInstance(position.getX()+width/2+5, position.getY()+height/2+10);
		AffineTransform at2= AffineTransform.getTranslateInstance(position.getX()+5, position.getY()+height/2+10);
		at1.rotate(angle, -5, -10);
		at2.rotate(angle, width/2-5, -10);
		
		if(acelerando) {
			g2d.drawImage(Assets.speed, at1, null);
			g2d.drawImage(Assets.speed, at2, null);
		}
		
		at= AffineTransform.getTranslateInstance(position.getX(), position.getY());
		at.rotate(angle,width/2,height/2);
	
		g2d.drawImage(Assets.player, at, null);
		
	}

	public boolean isSpawing() {
		return spawing;
	}
	
	
	
	
}
