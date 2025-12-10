package com.dsahub.patterns.compound;

import javax.swing.*;
import java.awt.*;

public class BeatView implements BeatObserver, BPMObserver {

    private final BeatModelInterface model;
    private JFrame frame;
    private JLabel beatLabel;
    private JLabel bpmLabel;
    private JButton startButton;
    private JButton stopButton;
    private JSlider bpmSlider;

    private final DJController controller;

    public BeatView(BeatModelInterface model, DJController controller) {
        this.model = model;
        this.controller = controller;
        model.registerBeatObserver(this);
        model.registerBPMObserver(this);
        createUI();
    }

    private void createUI() {
        frame = new JFrame("HFDP Compound - DJ Beat MVC");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        beatLabel = new JLabel(" ", SwingConstants.CENTER);
        beatLabel.setFont(new Font("SansSerif", Font.BOLD, 32));

        bpmLabel = new JLabel("BPM: " + model.getBPM(), SwingConstants.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(beatLabel, BorderLayout.CENTER);
        centerPanel.add(bpmLabel, BorderLayout.SOUTH);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        bpmSlider = new JSlider(40, 200, model.getBPM());
        bpmSlider.setMajorTickSpacing(40);
        bpmSlider.setPaintTicks(true);
        bpmSlider.setPaintLabels(true);

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);
        controlPanel.add(new JLabel("BPM:"));
        controlPanel.add(bpmSlider);

        startButton.addActionListener(e -> controller.start());
        stopButton.addActionListener(e -> controller.stop());
        bpmSlider.addChangeListener(e -> {
            if (!bpmSlider.getValueIsAdjusting()) {
                controller.setBPM(bpmSlider.getValue());
            }
        });

        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void updateBeat() {
        // flash "Beat!" briefly
        SwingUtilities.invokeLater(() -> {
            beatLabel.setText("Beat!");
            Timer t = new Timer(120, e -> beatLabel.setText(" "));
            t.setRepeats(false);
            t.start();
        });
    }

    @Override
    public void updateBPM(int bpm) {
        SwingUtilities.invokeLater(() -> bpmLabel.setText("BPM: " + bpm));
    }
}
