package graphics;

import java.awt.image.BufferedImage;

import math.Vector2D;

public class Animacion {

	private BufferedImage[] frames;
	private int velocidad;
	private int index;
	private boolean running;
	private Vector2D position;
	
	private long time, lastTime;

	public Animacion(BufferedImage[] frames, int velocidad, Vector2D position) {
		this.frames = frames;
		this.velocidad = velocidad;
		this.position = position;
		index=0;
		running= true;
		time=0;
		lastTime=System.currentTimeMillis();	
	}
	
	public void update() {
		time+=System.currentTimeMillis()-lastTime;
		lastTime=System.currentTimeMillis();
		
		if(time>velocidad) {
			time= 0;
			index++;
			
			if(index>=frames.length) {
				running=false;
			}
		}
	}

	public BufferedImage getCurrentFrames() {
		return frames[index];
	}

	public boolean isRunning() {
		return running;
	}

	public Vector2D getPosition() {
		return position;
	}
	
	
	
	
	
	
}
