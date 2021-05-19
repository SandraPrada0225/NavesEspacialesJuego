package graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

public class Assets {
	
	public static boolean loaded= false;
	public static float count=0;
	public static float MAXCOUNT=460;
	
	public static BufferedImage player;

	//efectos
	public static BufferedImage speed;
	
	//lasers
	public static BufferedImage laserazul,laserverde,laserrojo;
	
	//meteoros
	public static BufferedImage[] bigs= new BufferedImage[4];
	public static BufferedImage[] meds= new BufferedImage[2];
	public static BufferedImage[] smalls= new BufferedImage[2];
	public static BufferedImage[] tinies= new BufferedImage[2];
	
	// explosion 
	public static BufferedImage[] explosion= new BufferedImage[9];
	
	//UFO
	public static BufferedImage ufo;
	
	//Puntaje
	public static BufferedImage[] puntaje= new BufferedImage[11];
	
	//vida
	public static BufferedImage vida;
	
	//fonts
	public static Font font;
	public static Font fontMed;
	
	//sonidos 
	public static Clip musicafondo, laserPlayersonido,laserUfosonido, explosionsonido, gameOversonido, perder;  
	
	//botones
	public static BufferedImage botonAzul, botonVerde;
	
	
	public static void init() {
	player= loadImage("/ships/nave2.png");
	speed= loadImage("/efectos/fire08.png");
	laserazul= loadImage("/lasers/laserBlue16.png");
	laserverde= loadImage("/lasers/laserGreen10.png");
	laserrojo= loadImage("/lasers/laserRed16.png");
	
	for (int i = 0; i < bigs.length; i++) {
		bigs[i]= loadImage("/meteoros/meteorBrown_big"+(i+1)+".png");
	}
	for (int i = 0; i <meds.length; i++) {
		meds[i]= loadImage("/meteoros/meteorBrown_med"+(i+1)+".png");	
	}
	for (int i = 0; i < smalls.length; i++) {
		smalls[i]= loadImage("/meteoros/meteorBrown_small"+(i+1)+".png");
	}
	for (int i = 0; i < tinies.length; i++) {
		tinies[i]= loadImage("/meteoros/meteorBrown_tiny"+(i+1)+".png");	
	}
	for (int i = 0; i < explosion.length; i++) {
		explosion[i]= loadImage("/explosion/"+i+".png");		
	}
	ufo= loadImage("/ships/enemigo.png");
	
	for (int i = 0; i < puntaje.length; i++) {
		puntaje[i]= loadImage("/hud/numeral"+i+".png");		
	}
	vida= loadImage("/hud/playerLife2_green.png");
	
	font=loadFont("/fonts/kenvector_future.ttf",32);
	fontMed=loadFont("/fonts/kenvector_future.ttf",20);
	
	
	explosionsonido=loadclip("/sound/explosionCrunch_000.wav");
	gameOversonido=loadclip("/sound/game_over.wav");
	perder= loadclip("/sound/sfx_lose.wav");
	laserPlayersonido=loadclip("/sound/laserLarge_000.wav");
	laserUfosonido=loadclip("/sound/laserSmall_004.wav");
	musicafondo=loadclip("/sound/cyber_implant_in_my_butt.wav");
	
	botonAzul= loadImage("/iu/buttonBlue.png");
	botonVerde= loadImage("/iu/buttonGreen.png");
	
	
	loaded= true;
	}
	

	public static BufferedImage loadImage(String path) {
		count ++;
		return Loader.ImageLoader(path);	
	}
	public static Font loadFont(String path, int size) {
		count ++;
		return Loader.loadfont(path, size);	
	}
	public static Clip loadclip(String path) {
		count ++;
		return Loader.loadSound(path);	
	}
	
	

}
