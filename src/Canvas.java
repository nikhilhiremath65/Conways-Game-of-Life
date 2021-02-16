import java.awt.Toolkit;
import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Canvas extends JFrame implements MenuListener {

	private Thread game;
	private JMenuBar menu_bar;
	private Board game_board;
	private static final long serialVersionUID = 1L;
	private JMenu start_button, stop_button, reset_button;


	public Canvas() {

		menu_bar = new JMenuBar();
		setJMenuBar(menu_bar);

		start_button = new JMenu("Start");
		menu_bar.add(start_button);
		start_button.addMenuListener(this);

		stop_button = new JMenu("Stop");
		menu_bar.add(stop_button);
		stop_button.setEnabled(false);
		stop_button.addMenuListener(this);

		reset_button = new JMenu("Reset");
		menu_bar.add(reset_button);
		reset_button.addMenuListener(this);

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


	public void startGame(boolean start_flag) {
		if (start_flag) {
			game = new Thread(game_board);
			game.start();
		} else {
			game.interrupt();
		}
	}
	

	@Override
	public void menuSelected(MenuEvent e) {
		if (e.getSource().equals(reset_button)) {
			game_board.resetBoard();
			game_board.repaint();		
		} else if (e.getSource().equals(start_button)) {
			startGame(true);
		} else if (e.getSource().equals(stop_button)) {
			startGame(false);
		} 	
	}


	@Override
	public void menuDeselected(MenuEvent e) {
	}


	@Override
	public void menuCanceled(MenuEvent e) {
	}
}