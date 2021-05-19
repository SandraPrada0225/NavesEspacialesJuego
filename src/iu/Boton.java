package iu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import graphics.Assets;
import graphics.Text;
import input.Action;
import input.MouseInput;
import math.Vector2D;

public class Boton {

	private BufferedImage mouseOutImg,mouseInImg;
	private boolean mouseIn;
	private Rectangle box;
	private String text;
	private Action action;
	
	
	public Boton(BufferedImage mouseOutImg, BufferedImage mouseInImg, int x,int y, String text, Action action) {
		super();
		this.mouseOutImg = mouseOutImg;
		this.mouseInImg = mouseInImg;
		this.mouseIn = mouseIn;
		this.text= text;
		this.action = action;
		box= new Rectangle(x,y,mouseInImg.getWidth(),mouseInImg.getHeight());
		
	}
	
	
	public void update() {
		if(box.contains(MouseInput.x, MouseInput.y)) {
			mouseIn= true;
		}else {
			mouseIn= false;
		}
		
		if(mouseIn==true && MouseInput.MLB) {
			action.doAction();
		}
	}
	
	public void draw(Graphics g) {
		
		if(mouseIn) {
			g.drawImage(mouseInImg, box.x, box.y,null);
		}else {
			g.drawImage(mouseOutImg, box.x, box.y,null);
		}
		
		Text.drawText(g, text, new Vector2D(
				box.getX()+box.getWidth()/2,
				box.getY()+box.getHeight()), true, Color.BLACK, Assets.fontMed);			
	}	
	
	
	
}
