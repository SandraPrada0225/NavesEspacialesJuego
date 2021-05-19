package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import math.Vector2D;

public class Text {

	public static void drawText(Graphics g, String text, Vector2D pos, boolean center,Color color, Font font) {
		g.setColor(color);
		g.setFont(font);
		Vector2D posicion= new Vector2D(pos.getX(), pos.getY());
		if(center) {
			FontMetrics fm= g.getFontMetrics();
			posicion.setX(posicion.getX()-fm.stringWidth(text)/2);
			posicion.setY(posicion.getY()-fm.getHeight()/2);
		}
		g.drawString(text, (int )posicion.getX(),(int) posicion.getY());
	}
}
