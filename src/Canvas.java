import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


/**
 * This class implements the JFrame for the 'Conways-Game-of-Life' 
 * and also implements the game start and reset functionality. 
 * @author Nikhil Hiremath, Idant 
 * @version 1.0
 */


public class Canvas extends JFrame implements MenuListener, ActionListener {

	private Thread game;
	private JMenuBar menu_bar;
	private Board game_board;
	private static final long serialVersionUID = 1L;
	private JMenuItem start_button, stop_button, reset_button;

	
	/**
	 * Create the Start, Stop and Reset buton and intialize the game_board
	 */
	public Canvas() {

		menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);

		start_button = new JMenuItem("Start");
		menu_bar.add(start_button);
		start_button.addActionListener(this);

		stop_button = new JMenuItem("Stop");
		menu_bar.add(stop_button);
		stop_button.setEnabled(false);
		stop_button.addActionListener(this);

		reset_button = new JMenuItem("Reset");
		menu_bar.add(reset_button);
		reset_button.addActionListener(this);

		game_board = new Board();
		add(game_board);
	}

	
	public static void main(String[] args) {
		JFrame main_frame = new Canvas();
		main_frame.setTitle(CommonConstants.GAMENAME);
		main_frame.setSize(CommonConstants.DEFAULT_WINDOW_SIZE);
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main_frame.setMinimumSize(CommonConstants.MINIMUM_WINDOW_SIZE);

		Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		main_frame.setLocation(screen_size.width - main_frame.getWidth()/2, 
				(screen_size.height - main_frame.getHeight())/2);

		main_frame.setVisible(true);
	}

	
	/**
	 * This starts and stops the game and enable & dissable the start and stop button
	 * @param start_flag indicates the state of the game
	 */
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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(stop_button)) {
            startGame(false);
		}
		else if(e.getSource().equals(start_button))
			startGame(true);
		else if(e.getSource().equals(reset_button))
			game_board.resetBoard();
	}

	@Override
	public void menuDeselected(MenuEvent e) {
	}


	@Override
	public void menuCanceled(MenuEvent e) {
	}


	@Override
	public void menuSelected(MenuEvent e) {
	}


	
}