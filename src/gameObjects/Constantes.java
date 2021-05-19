package gameObjects;

import javax.swing.filechooser.FileSystemView;

public class Constantes {
	
	//dimensiones del frame
	public static final int WIDTH=1000;
	public static final int HEIGHT=600;
	// propiedades del jugador
	
	public static final int FIRERATE=300;
	public static double DELTAANGLE= 0.1, ACC=0.2,PLAYER_MAX_LEVEL=7.0;
	
    // propiedades del laser
	
	public static final double LASER_VEL= 15.0;
	
	//Propiedades del meteorito
	
	public static final double METEORO_VEL=2.0;
	public static final int METEORO_SCORE= 20;
	
	
	// Propiedades enemigo
	
	public static final int NODO_RADIUS=160;
	public static final double MASA_UFO=60;
	public static final int UFO_MAX_VEL=3;
	public static final long UFO_FIRE_RATE=1000;
	public static final double UFO_ANGLE_RATE=Math.PI/2;
	public static final int UFO_SCORE=40;
	public static final long APARECER_TIME=3000;
	public static final long PARPADEO_TIME=200;
	public static final long UFO_SPAWN_RATE=10000;
	
	
	//propiedades Menu
	public static final String PLAY="JUGAR";
	public static final String EXIT="SALIR";
	
	
	//Game Over
	public static final long GAME_OVER_TIME=10000;
	
	// propiedades barra de carga
	
	public static final int LOADING_BAR_WIDTH=500;
	public static final int LOADING_BAR_HEIGTH=50;
	
	
	//
	public static final String RETURN="RETURN";
	public static final String PUNTAJES_ALTOS="PUNTAJES ALTOS";
	public static final String PUNTAJE="PUNTAJES";
	public static final String FECHA="FECHA";
	public static final String SCOREPATH= FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\Naves_Espaciales_Juego\\data.json";
	
	
	
	
	

}
