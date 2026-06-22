package ModelViewControllerProject;

import javax.swing.*;
import java.io.*;

public class AnimationGUI extends JFrame implements Serializable{
    private static final int WIDTH = 512;
    private static final int HEIGHT = 600;

    private final Timer animationTimer;
    private final GraphicPanel graphicsPanel;
    private final ControlPanelInnerWEST controlPanelWEST;
    private final ControlPanelInnerSOUTH controlPanelSOUTH;

    private int[][] gridState;
    private int[][] nextGridState;
    private boolean drawCellEnabled = false;
    private boolean wrapEnabled = false;

    public AnimationGUI() {
        super("John Conway's Game of Life (Animation)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout(5, 5));

        // Initialize panels
        graphicsPanel = new GraphicPanel(this);
        controlPanelWEST = new ControlPanelInnerWEST(this, graphicsPanel);
        controlPanelSOUTH = new ControlPanelInnerSOUTH(this);

        // Add panels to the frame
        add(graphicsPanel, java.awt.BorderLayout.CENTER);
        add(controlPanelWEST, java.awt.BorderLayout.WEST);
        add(controlPanelSOUTH, java.awt.BorderLayout.SOUTH);

        // Initialize the animation timer
        animationTimer = new Timer(500, e -> {
            updateGridState();
            graphicsPanel.repaint();
        });

        // Initialize the grid state
        gridState = new int[10][10];
        nextGridState = new int[10][10];

        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new AnimationGUI();
        System.out.println("Main Thread Terminating");
    }

    // Logic for Conway's Game of life
    // Updates the grid state for the next generation
    public void updateGridState() {
        for (int row = 0; row < gridState.length; row++) {
            for (int col = 0; col < gridState[0].length; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                nextGridState[row][col] = (gridState[row][col] == 1) ?
                        (liveNeighbors == 2 || liveNeighbors == 3 ? 1 : 0) :
                        (liveNeighbors == 3 ? 1 : 0);
            }
        }

        // Swap the grid states
        int[][] temp = gridState;
        gridState = nextGridState;
        nextGridState = temp;
    }


    //Counts the number of live neighbors for a cell.
    private int countLiveNeighbors(int row, int col) {
        int liveCount = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (wrapEnabled) {
                // Wrap around the edges
                newRow = (newRow + gridState.length) % gridState.length;
                newCol = (newCol + gridState[0].length) % gridState[0].length;
            } else {
                // If out of bounds, skip
                if (newRow < 0 || newRow >= gridState.length || newCol < 0 || newCol >= gridState[0].length) {
                    continue;
                }
            }

            liveCount += gridState[newRow][newCol];
        }
        return liveCount;
    }


    // Returns the current grid state
    public int[][] getGridState() {
        return gridState;
    }

    // Sets the grid state
    public void setGridState(int[][] gridState) {
        this.gridState = gridState;
    }

    // Checks if wrapping is enabled
    public boolean isWrapEnabled() {
        return wrapEnabled;
    }

    // Sets the wrapping state
    public void setWrapEnabled(boolean wrapEnabled) {
        this.wrapEnabled = wrapEnabled;
    }

    // Returns the animation timer
    public Timer getAnimationTimer() {
        return animationTimer;
    }

    // Checks if drawing cells is enabled
    public boolean isDrawCellEnabled() {
        return drawCellEnabled;
    }

    // Sets the drawing cells state
    public void setDrawCellEnabled(boolean drawCellEnabled) {
        this.drawCellEnabled = drawCellEnabled;
    }

    // Saves the current grid state to a file
    public void saveGridState(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            // Serialize the grid state
            oos.writeObject(gridState);
            System.out.println("Grid state saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving grid state: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Could not save file.");
        }
    }

    // Loads a grid state from a file
    public void loadGridState(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Deserialize the grid state
            gridState = (int[][]) ois.readObject();
            nextGridState = new int[gridState.length][gridState[0].length];
            // Refresh the panel with the loaded state
            graphicsPanel.repaint();
            System.out.println("Grid state loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading grid state: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Could not load file.");
        }
    }
}