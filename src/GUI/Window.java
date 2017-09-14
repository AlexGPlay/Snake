package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Logica.Data;
import Logica.GameController;

public class Window extends JFrame {

	private static final long serialVersionUID = 7813054481987747538L;
	private JPanel contentPane;
	private GamePanel pnJuego;
	private GameController control;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window frame = new Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Window() {
		control = new GameController();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPnJuego(), BorderLayout.CENTER);
		setResizable(false);

		this.setSize(Data.TABLERO_COLUMNAS * Data.TABLERO_CASILLA, (Data.TABLERO_FILAS + 1) * Data.TABLERO_CASILLA);
	}

	private GamePanel getPnJuego() {
		if (pnJuego == null) {
			pnJuego = new GamePanel(this, control);
		}
		return pnJuego;
	}

}
