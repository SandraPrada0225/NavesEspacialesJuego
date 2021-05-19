package graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	private Clip clip;
	
	private FloatControl volumen;

	public Sound(Clip clip) {
		this.clip = clip;
		volumen=(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		
	}
	
	public void play() {
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop() {
		clip.stop();
	}
	
	public int getFrameProsition() {
	return clip.getFramePosition();	
	}
	
	public void loop() {
	    clip.setFramePosition(0);	
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void changeVolume(float value) {
		volumen.setValue(value);
	}
	

}
