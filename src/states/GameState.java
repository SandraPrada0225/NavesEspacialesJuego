package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import IO.JsonParser;
import IO.ScoreDate;
import gameObjects.Constantes;
import gameObjects.Cronometro;
import gameObjects.Mensaje;
import gameObjects.Meteoro;
import gameObjects.MovingObject;
import gameObjects.Player;
import gameObjects.Size;
import gameObjects.UFO;
import graphics.Animacion;
import graphics.Assets;
import graphics.Sound;
import graphics.Text;
import math.Vector2D;

public class GameState extends Estado {
	
	
	public static final Vector2D PLAYER_START_POSITION= new Vector2D(Constantes.WIDTH/2-Assets.player.getWidth()/2,Constantes.HEIGHT/2-Assets.player.getHeight()/2);
	
	private Player player;
	private ArrayList<MovingObject> movingObjects= new ArrayList<MovingObject>();
	private ArrayList<Animacion> animacions= new ArrayList<Animacion>();
	
	private int score=0;
	
	private int meteoros;
	private int lives=3;
	
	private int waves=1;
	
	private ArrayList<Mensaje> mensajes= new ArrayList<Mensaje>();
	
	private Sound musicaFondo;
	
	private Cronometro gameOverTimer;
	private boolean gameOver;
	
	private Cronometro ufoSpawaner;
	
	
	
	
	public GameState() {
		player= new Player(new Vector2D(400,300),new Vector2D(),7,Assets.player, this);
		gameOverTimer= new Cronometro();
		gameOver= false;
		
		movingObjects.add(player);
		
		meteoros=1;
		
		startWave();
		musicaFondo= new Sound(Assets.musicafondo);
		musicaFondo.loop();
		musicaFondo.changeVolume(-20.0f);
		
		ufoSpawaner= new Cronometro();
		ufoSpawaner.run(Constantes.UFO_SPAWN_RATE);
		
	}
	
	
	public void addAcore(int value, Vector2D position) {
		score+=value;
		mensajes.add(new Mensaje(position,true,"+ "+value+" score",Color.WHITE,false ,Assets.fontMed));
		
		
	}
	public void diveMeteroros(Meteoro meteoro) {
		Size size= meteoro.getSize();
		BufferedImage[] textures= size.texture;
		
		Size newsize =null;
		
		switch (size) {
		case BIG:
			newsize= Size.MED;
			break;
		case MED:
			newsize= Size.SMALL;
			break;
		case SMALL:
			newsize= Size.TINY;
			break;
		default:
			return;
		}
		
		for (int i = 0; i < size.quanity; i++) {
			movingObjects.add(new Meteoro(meteoro.getPosition(),
					new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
					Constantes.METEORO_VEL*Math.random()+1, 
					textures[(int)(Math.random()*textures.length)],
					this,
					newsize));
		}
	}
	
	private void startWave() {
		
		mensajes.add(new Mensaje(new Vector2D(Constantes.WIDTH/2, Constantes.HEIGHT/2),
				true,"Oleada "+waves,Color.WHITE,true ,Assets.font));
		
		double x,y;
		
		for (int i = 0; i < meteoros; i++) {
			x=i%2==0? Math.random()*Constantes.WIDTH:0;
			y=i%2==0? 0: Math.random()*Constantes.HEIGHT;
			
			BufferedImage texture= Assets.bigs[(int)(Math.random()*Assets.bigs.length) ];
			
			movingObjects.add(new Meteoro(new Vector2D(x,y),
					new Vector2D(0,1).setDirection(Math.random()*Math.PI*2),
					Constantes.METEORO_VEL*Math.random()+1, 
					texture, this, Size.BIG));
		}
	meteoros++;
	waves++;
	spawanUfo();
		
	}
	
	
	public void playAnimacionExplosion(Vector2D position) {
		animacions.add(new Animacion(
				Assets.explosion,
				50,
				position.subtract(new Vector2D(Assets.explosion[0].getWidth()/2,Assets.explosion[0].getHeight()/2))));
	}
	
	public void update() {
		
		for (int i = 0; i < movingObjects.size(); i++) {
			MovingObject mo=movingObjects.get(i);
			mo.update();
			if(mo.idDEad()){
				movingObjects.remove(i);
				i--;
			}
			
			
		}
		
		for (int i = 0; i < animacions.size(); i++) {
			Animacion anim= animacions.get(i);
			anim.update();
			if(!anim.isRunning()) {
				animacions.remove(i);
			}
		}
		
		
		
		if(gameOver && ! gameOverTimer.isRunning()){
			
			try {
				ArrayList<ScoreDate> dataList= JsonParser.readfile();
				dataList.add(new ScoreDate(score));
				JsonParser.writeFile(dataList);
				
			} catch (IOException e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Estado.cambiarEstado(new EstadoMenu());
		}
		
		
			
		if(!ufoSpawaner.isRunning()) {
			ufoSpawaner.run(Constantes.UFO_SPAWN_RATE);
			spawanUfo();
		}
		gameOverTimer.update();
		ufoSpawaner.update();
		
		for (int i = 0; i < movingObjects.size(); i++) 
			if(movingObjects.get(i) instanceof Meteoro) 
				return;
				
			startWave(); 
			
			
		
		
	}
	public void draw(Graphics g) {
		
		Graphics2D g2d=(Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		for (int i = 0; i < mensajes.size(); i++) {
			mensajes.get(i).draw(g2d);
			if(mensajes.get(i).isDEad()) {
				mensajes.remove(i);
			}
		}
		for (int i = 0; i < movingObjects.size(); i++) {
			movingObjects.get(i).draw(g);
		}
		
		for (int i = 0; i < animacions.size(); i++) {
			Animacion anim= animacions.get(i);
			g2d.drawImage(anim.getCurrentFrames(),(int) anim.getPosition().getX(),(int) anim.getPosition().getY(),null);
	
		}
		drawScore(g);
		drawLives(g);
		
		
	}
	
	private void spawanUfo() {
		double x,y;
		int rand=(int)(Math.random()*2);
		x=rand==0? Math.random()*Constantes.WIDTH:0;
		y=rand==0? 0: Math.random()*Constantes.HEIGHT;
		ArrayList<Vector2D> path= new ArrayList<Vector2D>();
		
		double posX, posY;
		
		posX= Math.random()*Constantes.WIDTH/2;
		posY= Math.random()*Constantes.HEIGHT/2;
		path.add(new Vector2D(posX, posY));
		
		posX= Math.random()*(Constantes.WIDTH/2)+Constantes.WIDTH/2;
		posY= Math.random()*Constantes.HEIGHT/2;
		path.add(new Vector2D(posX, posY));
		
		posX= Math.random()*Constantes.WIDTH/2;
		posY= Math.random()*(Constantes.HEIGHT/2)+Constantes.HEIGHT/2;
		path.add(new Vector2D(posX, posY));
		
		posX= Math.random()*(Constantes.WIDTH/2)+Constantes.WIDTH/2;
		posY= Math.random()*(Constantes.HEIGHT/2)+Constantes.HEIGHT/2;
		path.add(new Vector2D(posX, posY));
		
		movingObjects.add(new UFO(
				new Vector2D(x,y),
				new Vector2D(),
				Constantes.UFO_MAX_VEL,
				Assets.ufo,
				path,
				this
				));
		
		
		
	}
	
	private void drawScore(Graphics g) {
		Vector2D pos=new  Vector2D(600, 25);
		String scoreToString= Integer.toString(score);
		
		for (int i = 0; i < scoreToString.length(); i++) {
			g.drawImage(Assets.puntaje[Integer.parseInt(scoreToString.substring(i,i+1))],
					(int) pos.getX(), (int)pos.getY(), null);
			pos.setX(pos.getX()+20);
		}
	}
	
	private void drawLives(Graphics g) {
		
		if(lives<1)
			return;
		
		Vector2D livePosition=new  Vector2D(25, 25);
		g.drawImage(Assets.vida,(int) livePosition.getX(),(int) livePosition.getY(),null);
		g.drawImage(Assets.puntaje[10],(int)livePosition.getX()+40, (int)livePosition.getY()+5, null);
		
		String livesToString= Integer.toString(lives);
		Vector2D pos=new  Vector2D(livePosition.getX(), livePosition.getY());
		
		for (int i = 0; i < livesToString.length(); i++) {
			int number= Integer.parseInt(livesToString.substring(i,i+1));
			
			if(number<0) 
				break;
			g.drawImage(Assets.puntaje[number],
					(int) pos.getX()+60,
					(int) pos.getY()+5, null);
			pos.setX(pos.getX()+20);
			
		}
		
	}
	
	
	public ArrayList<MovingObject> getMovingObjects() {
		return movingObjects;
	}

	public Player getPlayer() {
		return player;
	}
	
	
	public boolean substractLife() {
		lives--;
		return lives>0;
		
	}


	public ArrayList<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public void gameOver() {
		Mensaje gameOverM= new Mensaje(PLAYER_START_POSITION, true, "GAME OVER", Color.WHITE, true, Assets.font);
		this.mensajes.add(gameOverM);
		gameOverTimer.run(Constantes.GAME_OVER_TIME);
		gameOver= true;
	}
	
	

}
