package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseInput extends MouseAdapter {

	public static int x, y;
	public static boolean MLB;	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()== MouseEvent.BUTTON1) {
			MLB= true;
		}	
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()== MouseEvent.BUTTON1) {
			MLB= false;
		}	
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		x= e.getX();
		y= e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x= e.getX();
		y= e.getY();
	}

	
}
