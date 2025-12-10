package com.dsahub.patterns.compound;

public class DJController {
    private final BeatModelInterface model;

    public DJController(BeatModelInterface model) {
        this.model = model;
    }

    public void start() {
        model.on();
    }

    public void stop() {
        model.off();
    }

    public void setBPM(int bpm) {
        model.setBPM(bpm);
    }

    public int getBPM() {
        return model.getBPM();
    }
}
