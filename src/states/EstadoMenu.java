package states;

import java.awt.Graphics;
import java.util.ArrayList;

import gameObjects.Constantes;
import graphics.Assets;
import input.Action;
import iu.Boton;

public class EstadoMenu extends Estado{
	
	private ArrayList<Boton> botones;
	
	

	public EstadoMenu() {
		botones= new ArrayList<Boton>();
		
		botones.add(new Boton(Assets.botonVerde, Assets.botonAzul,
				Constantes.WIDTH/2-Assets.botonVerde.getWidth()/2,
				Constantes.HEIGHT/2-Assets.botonVerde.getHeight()*2,
				Constantes.PLAY, new Action() {
					
					@Override
					public void doAction() {
						// TODO Auto-generated method stub
						Estado.cambiarEstado(new GameState());
						
					}
				}));
		
		

		botones.add(new Boton(Assets.botonVerde, Assets.botonAzul,
				Constantes.WIDTH/2-Assets.botonVerde.getWidth()/2,
				Constantes.HEIGHT/2+Assets.botonVerde.getHeight()*2,
				Constantes.EXIT, new Action() {
					
					@Override
					public void doAction() {
						// TODO Auto-generated method stub
						System.exit(0);
						
					}
				}));
		
		
		
		botones.add(new Boton(Assets.botonVerde, Assets.botonAzul,
				Constantes.WIDTH/2-Assets.botonVerde.getWidth()/2,
				Constantes.HEIGHT/2,
				Constantes.PUNTAJES_ALTOS, new Action() {
					
					@Override
					public void doAction() {
						// TODO Auto-generated method stub
						Estado.cambiarEstado(new ScoreEstado());
						
					}
				}));
		
	}
	

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for (Boton boton : botones) {
			boton.update();
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		for (Boton boton : botones) {
			boton.draw(g);
		}
	}
	

}
