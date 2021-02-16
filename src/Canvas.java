import java.awt.Toolkit;
import javax.swing.JMenu;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class Canvas extends JFrame {

	private Thread game;
	private JMenuBar menu_bar;
	private Board game_board;
	private static final long serialVersionUID = 1L;	
	private JMenu start_button, stop_button;


	public Canvas() {

		menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);

		start_button = new JMenu("Start");
		menu_bar.add(start_button);
	

		stop_button = new JMenu("Stop");
		menu_bar.add(stop_button);
		stop_button.setEnabled(false);

		
		game_board = new Board();
		add(game_board);
	}


	public static void main(String[] args) {
		JFrame main_frame = new Canvas();
		main_frame.setTitle(CommonConstants.GAMENAME);
		main_frame.setSize(CommonConstants.DEFAULT_WINDOW_SIZE);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setMinimumSize(CommonConstants.MINIMUM_WINDOW_SIZE);
		main_frame.getContentPane().setBackground(Color.BLUE);

		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		main_frame.setLocation(screen_size.width - main_frame.getWidth()/2, 
				(screen_size.height - main_frame.getHeight())/2);

		main_frame.setVisible(true);
	}


	public void startGame(boolean start_flag) {
		if (start_flag) {
			start_button.setEnabled(false);
			stop_button.setEnabled(true);
			game = new Thread(game_board);
			game.start();
		} else {
			start_button.setEnabled(true);
			stop_button.setEnabled(false);
			game.interrupt();
		}
	}
}