package ModelViewControllerProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;

// Constructor to initialize the control panel
public class ControlPanelInnerWEST extends JPanel {
    private final JLabel GridText = new JLabel("  Grid   ");
    private final JButton SetSize;
    private final JTextField RowsTextField;
    private final JTextField ColumnsTextField;
    private final JCheckBox gridCheckbox;
    private final JCheckBox drawCellCheckbox;
    private final JCheckBox wrapCheckbox;

    private final AnimationGUI animationGUI;
    private final GraphicPanel graphicsPanel;

    public ControlPanelInnerWEST(AnimationGUI animationGUI, GraphicPanel graphicsPanel) {
        this.animationGUI = animationGUI;
        this.graphicsPanel = graphicsPanel;

        setLayout(new FlowLayout());

        RowsTextField = new JTextField("10", 5);
        ColumnsTextField = new JTextField("10", 5);

        // Button to set the grid size and checks if the values given are valid
        SetSize = new JButton("Set size");
        SetSize.addActionListener(arg0 -> {
            try {
                int rows = Integer.parseInt(RowsTextField.getText());
                int columns = Integer.parseInt(ColumnsTextField.getText());
                graphicsPanel.setGridSize(rows, columns);
            } catch (NumberFormatException e) {
                System.out.println("Value entered is not valid");
                JOptionPane.showMessageDialog(null, "A value either in the row or column is not valid.");
            }
            graphicsPanel.requestFocus();
        });

        // Button to save a animation
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                animationGUI.saveGridState(file);
            }
        });

        // Button to load a animation
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                animationGUI.loadGridState(file);
            }
        });

        // Checkbox to toggle the grid
        gridCheckbox = new JCheckBox("Grid on");
        gridCheckbox.setSelected(true);
        gridCheckbox.addItemListener(e -> {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            graphicsPanel.setGridEnabled(selected);
            System.out.println("Grid " + (selected ? "enabled" : "disabled"));
        });

        // Checkbox for cell drawing toggle
        drawCellCheckbox = new JCheckBox("Draw cell");
        drawCellCheckbox.addItemListener(e -> animationGUI.setDrawCellEnabled(
                e.getStateChange() == ItemEvent.SELECTED
        ));

        // Checkbox for wrap toggle
        wrapCheckbox = new JCheckBox("Wrap");
        wrapCheckbox.addItemListener(e -> {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            animationGUI.setWrapEnabled(selected); // Update the wrap flag in AnimationGUI
            System.out.println("Wrap " + (selected ? "enabled" : "disabled"));
        });

        // Add all controls to the panel (in order)
        this.add(GridText);
        this.add(SetSize);
        this.add(new JLabel("  Rows   "));
        this.add(RowsTextField);
        this.add(new JLabel("  Columns   "));
        this.add(ColumnsTextField);
        this.add(gridCheckbox);
        this.add(drawCellCheckbox);
        this.add(wrapCheckbox);
        this.add(saveButton);
        this.add(loadButton);
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 500);
    }
}