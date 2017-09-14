package Logica;

import java.awt.Color;

public abstract class Data {

	// Datos tablero
	public static int TABLERO_CASILLA = 20;
	public static int TABLERO_FILAS = 20;
	public static int TABLERO_COLUMNAS = 20;
	public static Color TABLERO_FONDO = Color.BLACK;
	public static Color TABLERO_SERPIENTE = Color.WHITE;
	public static Color TABLERO_FRUTA = Color.RED;
	
	// Game data
	public static enum DIRECTIONS{ UP, DOWN, RIGHT, LEFT };
	public static int START_DELAY = 200;
	public static int MIN_DELAY = 50;
	public static int DOWN_DELAY = 5;
	public static Color LETRAS_COLOR = Color.BLUE;
	
}
