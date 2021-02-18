import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Board extends JPanel implements ComponentListener, MouseListener,
        MouseMotionListener, Runnable {

    private static int size = 15;
    private Dimension board_size = null;
    private static final long serialVersionUID = 1L;
    private ArrayList<Point> live_cells = new ArrayList<Point>(0);

    /**
     * Adding listeners to capture events
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
        for (Point current : live_cells) {
            if ((current.x > board_size.width - 1)
                    || (current.y > board_size.height - 1)) {
                removeList.add(current);
            }
        }
        live_cells.removeAll(removeList);
        repaint();
    }

    /**
     * Remove live cell from the board
     * 
     * @param x
     * @param y
     */
    public void deletePoint(int x, int y) {
        live_cells.remove(new Point(x, y));
        repaint();
    }

    /**
     * Add point to existing live cells
     * 
     * @param event
     */
    public void appendPoint(MouseEvent event) {
        int x = event.getPoint().x / size - 1;
        int y = event.getPoint().y / size - 1;
        if ((x >= 0) && (x < board_size.width) && (y >= 0)
                && (y < board_size.height)) {
            if (!live_cells.contains(new Point(x, y))) {
                live_cells.add(new Point(x, y));
                repaint();
            }
        }
    }

    /**
     * Remove all live cells
     */
    public void resetBoard() {
        live_cells.clear();
        repaint();
    }

    /**
     * Draws grid lines
     * 
     * @param g
     */
    public void drawLines(Graphics g) {
        g.setColor(CommonConstants.LINE_COLOR);
        for (int i = 0; i <= board_size.width; i++) {
            g.drawLine(((i * size) + size), size, (i * size) + size,
                    size + (size * board_size.height));
        }
        for (int i = 0; i <= board_size.height; i++) {
            g.drawLine(size, ((i * size) + size), size * (board_size.width + 1),
                    ((i * size) + size));
        }
    }

    /**
     * Draws live cells on the board
     * 
     * @param g
     */
    public void drawPoint(Graphics g) {
        try {
            for (Point newPoint : live_cells) {
                g.setColor(CommonConstants.LIVE_CELL_COLOR);
                g.fillRect(size + (size * newPoint.x),
                        size + (size * newPoint.y), size, size);
            }
        } catch (ConcurrentModificationException e) {
        }
    }

    /**
     * Checks boundaries of the board and if cell is alive
     * 
     * @param i
     * @param j
     * @return
     */
    private int incrementIfNeighbour(int i, int j) {
        return (i >= 0 && j >= 0 && i < board_size.width
                && j < board_size.height
                && live_cells.contains(new Point(i, j))) ? 1 : 0;
    }

    /**
     * Returns the next set of points in the board that should be alive
     * 
     * @return
     */
    private ArrayList<Point> getNextBoardState() {
        int neighbours_count;
        int[] neighbour_directions = new int[] { -1, 0, 1 };
        ArrayList<Point> nextBoardPoints = new ArrayList<>();
        for (int i = 0; i <= board_size.width; i++) {
            for (int j = 0; j <= board_size.height; j++) {
                neighbours_count = 0;
                for (int d_x = 0; d_x < neighbour_directions.length; d_x++) {
                    for (int d_y = 0; d_y < neighbour_directions.length; d_y++) {
                        if (neighbour_directions[d_x] == 0
                                && neighbour_directions[d_y] == 0)
                            continue;
                        neighbours_count += incrementIfNeighbour(
                                i + neighbour_directions[d_x],
                                j + neighbour_directions[d_y]);
                    }
                }
                if (neighbours_count == 3) {
                    nextBoardPoints.add(new Point(i, j));
                } else if (neighbours_count == 2
                        && live_cells.contains(new Point(i, j))) {
                    nextBoardPoints.add(new Point(i, j));
                }
            }
        }
        return nextBoardPoints;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoint(g);
        drawLines(g);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        board_size = new Dimension(getWidth() / size - 2,
                getHeight() / size - 2);
        updateArraySize();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getPoint().x / size - 1;
        int y = e.getPoint().y / size - 1;
        if (!live_cells.contains(new Point(x, y))) {
            appendPoint(e);
        } else {
            deletePoint(x, y);
        }
    }

    @Override
    public void run() {
        live_cells = getNextBoardState();
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