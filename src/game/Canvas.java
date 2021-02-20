package game;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * This class implements the JFrame for the 'Conways-Game-of-Life' and also
 * implements the game start and reset functionality.
 * 
 * @version 1.0
 */

public class Canvas extends JFrame implements MenuListener, ActionListener {

    private Thread game;
    private JMenuBar menuBar;
    private Board gameBoard;
    private static final long serialVersionUID = 1L;
    private JMenuItem startButton, stopButton, resetButton;

    /**
     * Canvas Constructor. Generates menubar, buttons and game board.
     */
    public Canvas() {

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        startButton = new JMenuItem("Start");
        menuBar.add(startButton);
        startButton.addActionListener(this);

        stopButton = new JMenuItem("Stop");
        menuBar.add(stopButton);
        stopButton.setEnabled(false);
        stopButton.addActionListener(this);

        resetButton = new JMenuItem("Reset");
        menuBar.add(resetButton);
        resetButton.addActionListener(this);

        gameBoard = new Board();
        add(gameBoard);
    }

    /*
     * Main Function. Creates Canvas and sets basic configurations for the
     * frame.
     * 
     * @param args[] arguement array for main.
     *
     */
    public static void main(String[] args) {
        JFrame main_frame = new Canvas();
        main_frame.setTitle(CommonConstants.GAMENAME);
        main_frame.setSize(CommonConstants.DEFAULT_WINDOW_SIZE);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setMinimumSize(CommonConstants.MINIMUM_WINDOW_SIZE);

        Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        main_frame.setLocation(screen_size.width - main_frame.getWidth() / 2,
                (screen_size.height - main_frame.getHeight()) / 2);

        main_frame.setVisible(true);
    }

    /**
     * This starts and stops the game and enable & disable the start and stop
     * button
     * 
     * @param startFlag indicates the state of the game
     */
    public void startGame(boolean startFlag) {
        if (startFlag) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            game = new Thread(gameBoard);
            game.start();
        } else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            game.interrupt();
        }
    }

    /**
     * This method captures events from stop, start and reset buttons to perform
     * actions
     * 
     * @param e Action event object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(stopButton)) {
            startGame(false);
        } else if (e.getSource().equals(startButton)) {
            startGame(true);
        } else if (e.getSource().equals(resetButton)) {
            gameBoard.resetBoard();
        }
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