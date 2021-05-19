package states;

import java.awt.Graphics;
import java.awt.Taskbar.State;

public abstract class Estado {

	private static Estado estadoActual= null;

	public abstract void update();
	public abstract void draw(Graphics g);
	
	public static void cambiarEstado(Estado estado) {
		estadoActual= estado;
	}
	public static Estado getEstadoActual() {
		return estadoActual;
	}
	
	

}
