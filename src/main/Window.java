package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gameObjects.Constantes;
import graphics.Assets;
import input.KeyBoard;
import input.MouseInput;
import states.CargarEstado;
import states.Estado;
import states.EstadoMenu;
import states.GameState;

public class Window extends JFrame implements Runnable {
	
	public static final int WIDTH=800,HEIGHT=600;
	private Canvas canvas;
	private Thread thread;
	private boolean running= false;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private final int FPS=60;// fotogramas por segundo
	private double TARGETTIME=1000000000/FPS;//tiempo en nanosegundos
	private double  delta=0;//tiempo que va pasando 
	private int AVERAGEFPS=FPS; // fotogramas por segundo promedio
	
	private KeyBoard keyBoard;
	private MouseInput mouseInput;
	
	
	public Window() {
		
		setTitle("Naves Espaciales Game");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		canvas= new Canvas();
		keyBoard= new KeyBoard();
		mouseInput= new MouseInput();
		
		canvas.setPreferredSize(new Dimension(Constantes.WIDTH,Constantes.HEIGHT));
		canvas.setMaximumSize(new Dimension(Constantes.WIDTH,Constantes.HEIGHT));
		canvas.setMinimumSize(new Dimension(Constantes.WIDTH,Constantes.HEIGHT));
		canvas.setFocusable(true);
		add(canvas);
		canvas.addKeyListener(keyBoard);
		canvas.addMouseListener(mouseInput);
		canvas.addMouseMotionListener(mouseInput);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Window().start();
	}
	
	

	private void update() {
		keyBoard.update();
		Estado.getEstadoActual().update();
	}
	
	private void draw() {
		
		bs= canvas.getBufferStrategy();
		if(bs==null) {
			canvas.createBufferStrategy(3);//el numero de buffer que utiliza un canvas
			return;
		}
		g=bs.getDrawGraphics();
		//----Dibujar-----
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		Estado.getEstadoActual().draw(g);
		g.drawString(""+AVERAGEFPS,10,10);
		
		
		
		//----------------
		g.dispose();
		bs.show();
	}

	
	private void init() {
		Thread loaThread= new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Assets.init();
			}
		});
		Estado.cambiarEstado(new CargarEstado(loaThread));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		long now=0;//Registro del tiempo
		long  lastTime=System.nanoTime();//hora actual en nanosegundos 
		int frames=0;
		long time=0;
		
		init();
		
		while (running) 
		{
			now=System.nanoTime();
			delta+=(now-lastTime)/TARGETTIME;
			time +=(now-lastTime);
			lastTime= now;
			if(delta>=1) {
				update();
				draw();
				delta--;
				frames++;
			}	
			
			if(time>=1000000000) {
				
				AVERAGEFPS=frames;
				frames=0;
				time=0;
			}
		}
		stop();
	}
	
	private void start() {
		thread= new Thread(this);
		thread.start();
		running= true;
	}
	
	private void stop() {
		try {
			thread.join();
			running= false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
