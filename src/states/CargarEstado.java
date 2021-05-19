package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import gameObjects.Constantes;
import graphics.Assets;
import graphics.Loader;
import graphics.Text;
import math.Vector2D;

public class CargarEstado extends Estado {
	
	private Thread loadThread;
	
	private Font font;
	
	
	

	public CargarEstado(Thread loadThread) {
		this.loadThread = loadThread;
		this.loadThread.start();
		font=Loader.loadfont("/fonts/kenvector_future.ttf", 30);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(Assets.loaded) {
			Estado.cambiarEstado(new EstadoMenu());
			try {
				loadThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		GradientPaint GP= new GradientPaint(Constantes.WIDTH/2-Constantes.LOADING_BAR_WIDTH/2,
				Constantes.HEIGHT/2-Constantes.LOADING_BAR_HEIGTH/2,
				Color.WHITE,
				Constantes.WIDTH/2+Constantes.LOADING_BAR_WIDTH/2,
				Constantes.HEIGHT/2+Constantes.LOADING_BAR_HEIGTH/2,
				Color.BLUE
				);
		Graphics2D g2d= (Graphics2D)g;
		g2d.setPaint(GP);
		
		float porcentaje= Assets.count/Assets.MAXCOUNT;
		g2d.fillRect(Constantes.WIDTH/2-Constantes.LOADING_BAR_WIDTH/2,
				Constantes.HEIGHT/2-Constantes.LOADING_BAR_HEIGTH/2, (int)(Constantes.LOADING_BAR_WIDTH/2+ porcentaje), Constantes.LOADING_BAR_HEIGTH/2);
		
		g2d.drawRect(Constantes.WIDTH/2-Constantes.LOADING_BAR_WIDTH/2,
				Constantes.HEIGHT/2-Constantes.LOADING_BAR_HEIGTH/2, Constantes.LOADING_BAR_WIDTH/2, Constantes.LOADING_BAR_HEIGTH/2);
		
		Text.drawText(g2d, "Naves Espaciales", new Vector2D(Constantes.WIDTH/2-100,Constantes.HEIGHT/2-50), true, Color.WHITE, font);
		Text.drawText(g2d, "Cargando...", new Vector2D(Constantes.WIDTH/2-100,Constantes.HEIGHT/2+15), true, Color.WHITE, font);
	}

}
