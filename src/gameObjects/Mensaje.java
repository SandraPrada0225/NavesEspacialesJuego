package gameObjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.text.Position;
import javax.swing.text.TabExpander;

import graphics.Text;
import math.Vector2D;
import states.GameState;

public class Mensaje {

	
	
	private float alpha;
	private String text;
	private Vector2D posicion;
	private Color color;
	private boolean center;
	private boolean fade;
	private Font font;
	private final float deltaAlpha=0.01f;
	
	private boolean dead;
	
	public Mensaje( Vector2D posicion,boolean fade, String text, Color color,boolean center,  
			 Font font) {
		super();
		this.text = text;
		this.posicion = posicion;
		this.color = color;
		this.center = center;
		this.fade = fade;
		this.font = font;
		dead= false;
		if(fade)
			alpha=1;
		else
			alpha=0;
		
	}
	public void draw(Graphics2D g2d) {
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		Text.drawText(g2d, text, posicion, center, color, font);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
		posicion.setY(posicion.getY()-1);
		if(fade)
			alpha-= deltaAlpha;
		else
			alpha+= deltaAlpha;
		
		if(fade && alpha<0 || !fade && alpha>1) {
			dead= true;
		}
		
		
		
	}
	
	public boolean isDEad() {
		return dead;
	}
	
	

	
}
