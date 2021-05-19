package gameObjects;

public class Cronometro {

	private long delta, lastTime;
	private long time;
	private boolean running;
	
	public Cronometro() {
		delta=0;
		lastTime=System.currentTimeMillis();
		running= false;
	}
	
	public void run(long time) {
		running= true;
		this.time=time;
	}
	
	public void update() {
		if(running) {
			delta+=System.currentTimeMillis()-lastTime;
		}
		if(delta>=time) {
			running= false;
			delta=0;
		}
		lastTime= System.currentTimeMillis();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	
}
