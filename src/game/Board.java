package game;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Board is a JPanel which implements component and mouse event listeners and is
 * a Runnable class. It is composed of grid of cells and has all actions which
 * can be performed with the cells in the panel.
 * 
 * @version 1.0
 */
public class Board extends JPanel implements ComponentListener, MouseListener,
        MouseMotionListener, Runnable {

    private static int size = 15;
    private Dimension boardSize = null;
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> liveCells = new ArrayList<Point>(0);

    /**
     * Board constructor, adds commponent and mouse listeners to the Panel.
     */
    public Board() {
        setBackground(CommonConstants.BACKGROUND_COLOR);
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /**
     * Add or remove cells to the grid on board resize
     */
    private void updateArraySize() {
        ArrayList<Point> removeList = new ArrayList<Point>(0);
        for (Point current : liveCells) {
            if ((current.x > boardSize.width - 1)
                    || (current.y > boardSize.height - 1)) {
                removeList.add(current);
            }
        }
        liveCells.removeAll(removeList);
        repaint();
    }

    /**
     * Remove live cell from the board
     * 
     * @param x X coordinate on the JPanel
     * @param y Y coordinate on the JPanel
     */
    public void deletePoint(int x, int y) {
        liveCells.remove(new Point(x, y));
        repaint();
    }

    /**
     * Add point to existing live cells. Calls repaint only when new point is
     * added.
     * 
     * @param event
     */
    public void appendPoint(MouseEvent event) {
        int x = event.getPoint().x / size - 1;
        int y = event.getPoint().y / size - 1;
        if ((x >= 0) && (x < boardSize.width) && (y >= 0)
                && (y < boardSize.height)) {
            if (!liveCells.contains(new Point(x, y))) {
                liveCells.add(new Point(x, y));
                repaint();
            }
        }
    }

    /**
     * Remove all live cells
     */
    public void resetBoard() {
        liveCells.clear();
        repaint();
    }

    /**
     * Draws grid lines
     * 
     * @param g
     */
    public void drawLines(Graphics g) {
        g.setColor(CommonConstants.LINE_COLOR);
        for (int i = 0; i <= boardSize.width; i++) {
            g.drawLine(((i * size) + size), size, (i * size) + size,
                    size + (size * boardSize.height));
        }
        for (int i = 0; i <= boardSize.height; i++) {
            g.drawLine(size, ((i * size) + size), size * (boardSize.width + 1),
                    ((i * size) + size));
        }
    }

    /**
     * Draws live cells on the board
     * 
     * @param g Graphics object
     */
    public void drawPoint(Graphics g) {
        try {
            for (Point newPoint : liveCells) {
                g.setColor(CommonConstants.LIVE_CELL_COLOR);
                g.fillRect(size + (size * newPoint.x),
                        size + (size * newPoint.y), size, size);
            }
        } catch (ConcurrentModificationException e) {
        }
    }

    /**
     * Checks boundaries of the board and whether cell is alive
     * 
     * @param i X coordinate
     * @param j Y Coordinate.
     */
    private int incrementIfNeighbour(int i, int j) {
        return (i >= 0 && j >= 0 && i < boardSize.width && j < boardSize.height
                && liveCells.contains(new Point(i, j))) ? 1 : 0;
    }

    /**
     * Returns the next set of points in the board that should be alive
     */
    private ArrayList<Point> getNextBoardState() {
        int neighboursCount;
        int[] neighbourDirections = new int[] { -1, 0, 1 };
        ArrayList<Point> nextBoardPoints = new ArrayList<>();
        for (int i = 0; i <= boardSize.width; i++) {
            for (int j = 0; j <= boardSize.height; j++) {
                neighboursCount = 0;
                for (int x = 0; x < neighbourDirections.length; x++) {
                    for (int y = 0; y < neighbourDirections.length; y++) {
                        if (neighbourDirections[x] == 0
                                && neighbourDirections[y] == 0)
                            continue;
                        neighboursCount += incrementIfNeighbour(
                                i + neighbourDirections[x],
                                j + neighbourDirections[y]);
                    }
                }
                if (neighboursCount == 3) {
                    nextBoardPoints.add(new Point(i, j));
                } else if (neighboursCount == 2
                        && liveCells.contains(new Point(i, j))) {
                    nextBoardPoints.add(new Point(i, j));
                }
            }
        }
        return nextBoardPoints;
    }

    /**
     * Paint function for drawing lines and points .
     * 
     * @param g Graphics object.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoint(g);
        drawLines(g);
    }

    /**
     * Resizes the JPanel on mouse drag.
     * 
     * @param e Component Event object.
     */
    @Override
    public void componentResized(ComponentEvent e) {
        boardSize = new Dimension(getWidth() / size - 2,
                getHeight() / size - 2);
        updateArraySize();
    }

    /**
     * Mouse Release action listener for JPanel. Toggles the cell betwen live
     * and dead.
     * 
     * @param e Mouse Event object.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getPoint().x / size - 1;
        int y = e.getPoint().y / size - 1;
        if (!liveCells.contains(new Point(x, y))) {
            appendPoint(e);
        } else {
            deletePoint(x, y);
        }
    }

    /**
     * Board Run method. Gets next generation of live cells and invokes repaint
     * of Board.
     */
    @Override
    public void run() {
        liveCells = getNextBoardState();
        repaint();
        try {
            Thread.sleep(1000);
            run();
        } catch (InterruptedException ie) {

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}