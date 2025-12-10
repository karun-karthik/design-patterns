package com.dsahub.patterns.compound;

public class DJApp {
    public static void main(String[] args) {
        // create strategy, model, controller, and view
        SimpleThreadBeatGenerator generator = new SimpleThreadBeatGenerator();
        BeatModel model = new BeatModel(generator);
        DJController controller = new DJController(model);
        model.initialize();
        // view registers itself as observer
        new BeatView(model, controller);
        // optionally start with default BPM
        controller.setBPM(120);
        controller.start();
    }
}
