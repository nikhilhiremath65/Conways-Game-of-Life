import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;


public class Board extends JPanel implements  ComponentListener,MouseListener,
	MouseMotionListener, Runnable{


	private static int size  = 15;
    private Dimension boardSize = null;
	private static final long serialVersionUID = 1L;
    private ArrayList<Point> point = new ArrayList<Point>(0);
    
    
    public Board() {
    	setBackground(Color.DARK_GRAY);
        addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    	
    private void updateArraySize() {
        ArrayList<Point> removeList = new ArrayList<Point>(0);
        for (Point current : point) {
            if ((current.x > boardSize.width-1) || 
            	(current.y > boardSize.height-1)) {
                removeList.add(current);
            }
        }
        point.removeAll(removeList);
        repaint();
    }

    
    public void deletePoint(int x, int y) {
        point.remove(new Point(x,y));
        repaint();
    }
    
    
    public void appendPoint(MouseEvent me) {
        int x = me.getPoint().x/size-1;
        int y = me.getPoint().y/size-1;
        if ((x >= 0) && (x < boardSize.width) && (y >= 0) 
        		&& (y < boardSize.height)) {
            if (!point.contains(new Point(x,y))) {
                point.add(new Point(x,y));
                repaint();
            }
        }
    }
    
    
    public void resetBoard() {
        point.clear();
        repaint();
    }

    
    public void drawLines(Graphics g){
    	g.setColor(Color.LIGHT_GRAY);  
        for (int i=0; i<=boardSize.width; i++) {
            g.drawLine(((i*size)+size), size, (i*size)+size, size +
            			(size*boardSize.height));
        }
        for (int i=0; i<=boardSize.height; i++) {
            g.drawLine(size, ((i*size)+size), size * (boardSize.width+1),
            		((i*size)+size));
        }
    }
    
    
    public void drawPoint(Graphics g){
        try {
            for (Point newPoint : point) {
            	g.setColor(Color.YELLOW);
                g.fillRect(size + (size*newPoint.x), size 
                		+ (size*newPoint.y), size, size);
            }
        } catch (ConcurrentModificationException e) {}
    }

    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoint(g);
        drawLines(g);
    }
    

    @Override
    public void componentResized(ComponentEvent e) {
        boardSize = new Dimension(getWidth()/size-2, getHeight()/size-2);
        updateArraySize();
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getPoint().x/size-1;
        int y = e.getPoint().y/size-1;
        if (!point.contains(new Point( x,y))){
            appendPoint(e);
        }
        else{
            deletePoint(x,y);
        }
    }
    
    
 
	@Override
	public void run() {
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