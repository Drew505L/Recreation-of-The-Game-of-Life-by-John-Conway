package ModelViewControllerProject;

import javax.swing.*;
import java.awt.*;

public class ControlPanelInnerSOUTH extends JPanel {
    private final JLabel animationLabel;
    private final JButton AnimationTimerButton;
    private final JSlider animationSpeedSlider;

    private final AnimationGUI animationGUI;

    public ControlPanelInnerSOUTH(AnimationGUI animationGUI) {
        this.animationGUI = animationGUI;

        setLayout(new FlowLayout());

        // Label for the animation section
        animationLabel = new JLabel("Animation");
        animationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Button to start/stop the animation timer
        AnimationTimerButton = new JButton("Start");
        AnimationTimerButton.addActionListener(arg0 -> {
            if (animationGUI.getAnimationTimer().isRunning()) {
                animationGUI.getAnimationTimer().stop();
                System.out.println("Animation stopped");
                AnimationTimerButton.setText("Start");
            }
            else {
                animationGUI.getAnimationTimer().start();
                System.out.println("Animation started");
                AnimationTimerButton.setText("Stop");
            }
        });

        // Creates the slider that changes the speed of the timer
        animationSpeedSlider = new JSlider(0, 100, 50);
        animationSpeedSlider.setMajorTickSpacing(20);
        animationSpeedSlider.setPaintTicks(true);
        animationSpeedSlider.setPaintLabels(true);

        // Changes the speed of the timer
        animationSpeedSlider.addChangeListener(e -> {
            int sliderValue = animationSpeedSlider.getValue();
            // Map slider value (0-100) to delay (50 ms to 2000 ms)
            int delay = mapSliderToDelay(sliderValue);
            animationGUI.getAnimationTimer().setDelay(delay);
            System.out.println("Slider value: " + sliderValue + ", Timer delay: " + delay + " ms");
        });

        // Add all controls to the panel (in order)
        this.add(animationLabel);
        this.add(AnimationTimerButton);
        this.add(animationSpeedSlider);
    }

    // 50 = minimum delay, and 2000 is the max delay
    private int mapSliderToDelay(int sliderValue) {
        int minDelay = 50;
        int maxDelay = 2000;
        return minDelay + (maxDelay - minDelay) * (100 - sliderValue) / 100;
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }
}