package ModelViewControllerProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GraphicPanel extends JPanel implements MouseMotionListener, MouseListener {
    private final AnimationGUI animationGUI;
    private int rows = 10;
    private int columns = 10;
    private boolean gridEnabled = true;


    public GraphicPanel(AnimationGUI animationGUI) {
        this.animationGUI = animationGUI;
        setBackground(Color.WHITE);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setGridSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        animationGUI.setGridState(new int[rows][columns]);
        repaint();
    }

    public void setGridEnabled(boolean enabled) {
        gridEnabled = enabled;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphicsContext = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        double cellWidth = width / (double) columns;
        double cellHeight = height / (double) rows;

        // Draw grid
        if (gridEnabled) {
            graphicsContext.setColor(Color.BLACK);
            for (int i = 0; i <= columns; i++) {
                graphicsContext.drawLine((int) (i * cellWidth), 0, (int) (i * cellWidth), height);
            }
            for (int i = 0; i <= rows; i++) {
                graphicsContext.drawLine(0, (int) (i * cellHeight), width, (int) (i * cellHeight));
            }
        }

        // Draw cells
        int[][] gridState = animationGUI.getGridState();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (gridState[i][j] == 1) {
                    int x = (int) (j * cellWidth);
                    int y = (int) (i * cellHeight);
                    graphicsContext.setColor(Color.BLACK);
                    graphicsContext.fillOval(x, y, (int) cellWidth, (int) cellHeight);
                }
            }
        }
    }

    // Draws a circle based on the users clicks/mouse movements
    private void handleMouseEvent(MouseEvent event) {
        int cellWidth = getWidth() / columns;
        int cellHeight = getHeight() / rows;
        int col = event.getX() / cellWidth;
        int row = event.getY() / cellHeight;

        if (col >= 0 && col < columns && row >= 0 && row < rows) {
            int[][] gridState = animationGUI.getGridState();
            gridState[row][col] = animationGUI.isDrawCellEnabled() ? 1 : 0;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        handleMouseEvent(event);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        handleMouseEvent(event);
    }

    @Override
    public void mouseMoved(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }
}