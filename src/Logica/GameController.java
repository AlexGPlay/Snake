package Logica;

import java.util.ArrayList;

public class GameController {

	private int actualX;
	private int actualY;

	private int fruitX;
	private int fruitY;

	private int puntos;
	private ArrayList<int[]> cola;
	private boolean lost;
	private int tics;
	private Data.DIRECTIONS lastDir;

	private Data.DIRECTIONS dir;

	public GameController() {
		actualX = Data.TABLERO_COLUMNAS/2;
		actualY = Data.TABLERO_FILAS/2;

		fruitX = (int)(Math.random() * Data.TABLERO_COLUMNAS);
		fruitY = (int)(Math.random() * Data.TABLERO_FILAS);

		puntos = 0;
		cola = new ArrayList<int[]>();
		lost = false;
		tics = Data.START_DELAY;
	}

	public boolean isLost() {
		return lost;
	}

	public int getX() {
		return actualX;
	}

	public int getY() {
		return actualY;
	}

	public int getFruitX() {
		return fruitX;
	}

	public int getFruitY() {
		return fruitY;
	}

	public void newDirection(Data.DIRECTIONS dir) {
		boolean contraria = false;

		if(lastDir == null)
			lastDir = dir;

		else {
			switch(lastDir) {
			case UP:
				if(dir == Data.DIRECTIONS.DOWN) 
					contraria = true;
				break;

			case DOWN:
				if(dir == Data.DIRECTIONS.UP)
					contraria = true;
				break;

			case LEFT:
				if(dir == Data.DIRECTIONS.RIGHT)
					contraria = true;
				break;

			case RIGHT:
				if(dir == Data.DIRECTIONS.LEFT)
					contraria = true;
				break;
			}
		}

		if(!contraria) {
			lastDir = dir;
			this.dir = dir;
		}
	}

	public void checkFruit() {

		if(actualX == fruitX && actualY == fruitY) {
			puntos++;

			do {
				fruitX = (int)(Math.random() * Data.TABLERO_COLUMNAS);
				fruitY = (int)(Math.random() * Data.TABLERO_FILAS);
			}while((fruitX==actualX && fruitY==actualY) || contains(fruitX, fruitY));

			if(tics>Data.MIN_DELAY)
				tics -= Data.DOWN_DELAY;

			int[] nuevo = {-1,-1};
			cola.add(nuevo);
		}

	}

	public void move() {
		checkLost();
		moveTail();

		switch(dir) {
		case UP:
			if(actualY>0)
				actualY--;
			else
				actualY = Data.TABLERO_FILAS-1;

			break;

		case DOWN:
			if(actualY<Data.TABLERO_FILAS-1)
				actualY++;

			else
				actualY = 0;

			break;

		case RIGHT:
			if(actualX<Data.TABLERO_COLUMNAS-1)
				actualX++;
			else
				actualX = 0;

			break;

		case LEFT:
			if(actualX>0)
				actualX--;
			else
				actualX = Data.TABLERO_COLUMNAS-1;
			break;

		}

		checkFruit();
	}

	public void moveTail() {

		if(puntos>0) {
			int[] temp;

			for(int i=puntos-1;i>0;i--) {
				temp = cola.get(i);

				temp[0] = cola.get(i-1)[0];
				temp[1] = cola.get(i-1)[1];

				cola.set(i, temp);
			}

			temp = cola.get(0);

			temp[0] = actualX;
			temp[1] = actualY;

			cola.set(0, temp);
		}
	}

	public ArrayList<int[]> getTail(){
		return cola;
	}

	public void checkLost() {
		if(contains(actualX,actualY))
			lost = true;
	}

	public boolean contains(int x, int y) {

		for(int[] temp : cola) {
			if(temp[0]==x && temp[1]==y)
				return true;
		}

		return false;
	}

	public int getTics() {
		return tics;
	}

	public int getPuntos() {
		return puntos;
	}

}
