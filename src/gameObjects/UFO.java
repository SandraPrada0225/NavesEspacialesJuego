package gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.Vector;

import graphics.Assets;
import graphics.Sound;
import math.Vector2D;
import states.GameState;

public class UFO extends MovingObject{
	
	private ArrayList<Vector2D> path;// arreglo que contendra los nodos que formen el camino
	private Vector2D nodoActual;
	private int index;// camino
	private boolean following;
	private Sound disparar;
	
	private Cronometro cronometro;

	public UFO(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture,ArrayList<Vector2D> path, GameState gameState) {
		super(position, velocity, maxVel, texture, gameState);
		this.path= path;
		index=0;
		following= true;
		cronometro= new Cronometro();
		cronometro.run(Constantes.UFO_FIRE_RATE);
		disparar= new Sound(Assets.laserUfosonido);
		// TODO Auto-generated constructor stub
	}
	
	private Vector2D camino() {
		nodoActual= path.get(index);
		double distanciaNodo= nodoActual.subtract(getCenter()).getMagnitude();
		
		if(distanciaNodo<Constantes.NODO_RADIUS) {
			index++;
			if(index>=path.size()) {
				following= false;
			}
		}
		
		return seekForce(nodoActual);
	}
	
	private Vector2D seekForce(Vector2D target) {
		Vector2D velocidadDeseada= target.subtract(getCenter());
		velocidadDeseada= velocidadDeseada.normalizar().scale(maxVel);
		return velocidadDeseada.subtract(velocity);
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		Vector2D pathFollowing;
		if(following)
			pathFollowing=camino();
		else
			pathFollowing= new Vector2D();
		
		pathFollowing= pathFollowing.scale(1/Constantes.MASA_UFO);
		
		velocity= velocity.add(pathFollowing);
		velocity=velocity.limit(maxVel);
		position= position.add(velocity);
		
		if(position.getX()>Constantes.WIDTH||position.getX()<0|| position.getY()<0 || position.getY()> Constantes.HEIGHT) 
				Destroy();
		
		//Disparar
		if(!(cronometro.isRunning())) {
			Vector2D toPlayer= gameState.getPlayer().getCenter().subtract(getCenter());
			toPlayer= toPlayer.normalizar();
			double anguloActual= toPlayer.getAngle();
			anguloActual+=Math.random()*Constantes.UFO_ANGLE_RATE-Constantes.UFO_ANGLE_RATE/2;
			if(toPlayer.getX()<0)
				anguloActual=-anguloActual+Math.PI;
			toPlayer= toPlayer.setDirection(anguloActual);
			Laser laser = new Laser(getCenter().add(toPlayer.scale(width)),
					toPlayer,
					Constantes.LASER_VEL,
					anguloActual+Math.PI/2,
					Assets.laserrojo,
					gameState);
			gameState.getMovingObjects().add(0,laser);
			cronometro.run(Constantes.UFO_FIRE_RATE);
			disparar.play(); 
		}

		if(disparar.getFrameProsition()>8500) {
			disparar.stop();
		}
		
		
		
		angle +=0.5;
		colicion();
		cronometro.update();
	}
	
    @Override
	protected void Destroy() {
		// TODO Auto-generated method stub
		gameState.addAcore(Constantes.UFO_SCORE, position);
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

}
