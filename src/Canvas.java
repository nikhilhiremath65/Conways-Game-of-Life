import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import javax.swing.*;

public class Canvas extends JFrame {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(600, 600);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(400, 400);
    Board gameBoard = new Board();
    Canvas(){
        setTitle("Game of Life");
        setSize(DEFAULT_WINDOW_SIZE);
        setMinimumSize(MINIMUM_WINDOW_SIZE);
        add(gameBoard);
        setVisible(true);
    }

    public static  void main(String a[]){
        Canvas game = new Canvas();
    }
}
