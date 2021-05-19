package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoard implements KeyListener {
	
	private boolean[] keys= new boolean[256];
	
	public static boolean UP, LEFT, RIGHT, DISPARAR;

	public KeyBoard() {
		UP=false;
		LEFT= false;
		RIGHT= false;
		DISPARAR= false;
	}
	
	public void update() {
		UP=keys[KeyEvent.VK_UP];
		LEFT=keys[KeyEvent.VK_LEFT];
		RIGHT=keys[KeyEvent.VK_RIGHT];
		DISPARAR= keys[KeyEvent.VK_P];
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()]=true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()]=false;
	}
}
