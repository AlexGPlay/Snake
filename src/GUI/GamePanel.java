package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import Logica.Data;
import Logica.GameController;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = -2693863158529022511L;

	private Window parent;
	private GameController control;
	private Timer timer;
	private Data.DIRECTIONS direct;
	private boolean initGame;
	private boolean paused;

	public GamePanel(Window w, GameController control) {
		this.parent = w;
		this.control = control;

		setLayout(null);
		setBackground(Data.TABLERO_FONDO);
		setSize(Data.TABLERO_COLUMNAS * Data.TABLERO_CASILLA, Data.TABLERO_FILAS * Data.TABLERO_CASILLA);

		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "Left");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "Up");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "Down");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "Right");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(' '), "Replay");
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Pause");

		this.getActionMap().put("Left", new NewDirection(Data.DIRECTIONS.LEFT));
		this.getActionMap().put("Up", new NewDirection(Data.DIRECTIONS.UP));
		this.getActionMap().put("Down", new NewDirection(Data.DIRECTIONS.DOWN));
		this.getActionMap().put("Right", new NewDirection(Data.DIRECTIONS.RIGHT));
		this.getActionMap().put("Replay", new ReplayGame());
		this.getActionMap().put("Pause", new PauseGame());

		initGame = false;
		paused = false;
	}

	public Timer getTimer() {
		if(timer == null) {
			timer = new Timer(control.getTics(), new GameAction());
		}

		return timer;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Pintar serpiente
		g.setColor(Data.TABLERO_SERPIENTE);
		g.fillRect(control.getX()*Data.TABLERO_CASILLA, control.getY()*Data.TABLERO_CASILLA, 
				Data.TABLERO_CASILLA, Data.TABLERO_CASILLA);

		// Pintar cola
		g.setColor(Data.TABLERO_SERPIENTE);
		for(int[] temp : control.getTail()) 
			g.fillRect(temp[0]*Data.TABLERO_CASILLA, temp[1]*Data.TABLERO_CASILLA, 
					Data.TABLERO_CASILLA, Data.TABLERO_CASILLA);


		// Pintar fruta
		g.setColor(Data.TABLERO_FRUTA);
		g.fillRect(control.getFruitX()*Data.TABLERO_CASILLA, control.getFruitY()*Data.TABLERO_CASILLA, 
				Data.TABLERO_CASILLA, Data.TABLERO_CASILLA);

		if(control.isLost()) {
			g.setColor(Data.LETRAS_COLOR);
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.drawString("PUNTUACIÓN: " + control.getPuntos(), 
					Data.TABLERO_COLUMNAS*Data.TABLERO_CASILLA/4, Data.TABLERO_FILAS*Data.TABLERO_CASILLA/2);

			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("[SPACE] = JUGAR || [ESC] = SALIR", 
					Data.TABLERO_COLUMNAS*Data.TABLERO_CASILLA/10, Data.TABLERO_FILAS*Data.TABLERO_CASILLA/2+32);
		}

		if(paused) {
			g.setColor(Data.LETRAS_COLOR);
			g.setFont(new Font("Arial", Font.BOLD, 32));
			g.drawString("PAUSA", 
					Data.TABLERO_COLUMNAS*Data.TABLERO_CASILLA/4, Data.TABLERO_FILAS*Data.TABLERO_CASILLA/2);

			g.setFont(new Font("Arial", Font.BOLD, 16));
			g.drawString("[SPACE] = JUGAR || [ESC] = SALIR", 
					Data.TABLERO_COLUMNAS*Data.TABLERO_CASILLA/10, Data.TABLERO_FILAS*Data.TABLERO_CASILLA/2+32);
		}

	}

	public void gameAct() {
		if(control.isLost())
			timer.stop();

		control.newDirection(direct);
		control.move();
		timer.setDelay(control.getTics());
		this.repaint();
	}

	public Window getWindow() {
		return parent;
	}

	private class NewDirection extends AbstractAction {
		private static final long serialVersionUID = 5083645117895335534L;
		private Data.DIRECTIONS dir;

		public NewDirection(Data.DIRECTIONS dir) {
			this.dir = dir;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!paused && !control.isLost()) {
				if(!initGame) {
					getTimer().start();
					initGame = true;
				}

				direct = dir;
			}
		}

	}

	private class ReplayGame extends AbstractAction {
		private static final long serialVersionUID = 3005089922942231473L;

		@Override
		public void actionPerformed(ActionEvent e) {
			refreshGame();
		}

	}

	private class PauseGame extends AbstractAction {
		private static final long serialVersionUID = 5083645117895335534L;

		@Override
		public void actionPerformed(ActionEvent e) {
			pauseGame();
		}

	}

	private void pauseGame() {
		if(!control.isLost()) {
			if(!paused) {
				paused = true;
				getTimer().stop();
				this.repaint();
			}

			else
				System.exit(0);
		}
		
		else
			System.exit(0);
	}

	private void refreshGame() {
		if(control.isLost()) {
			control = new GameController();
			initGame = false;
			timer.setDelay(Data.START_DELAY);
			this.repaint();
		}

		if(paused) {
			paused = false;
			this.repaint();

			if(initGame)
				getTimer().start();
		}
	}

	private class GameAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			gameAct();
		}

	}

}
