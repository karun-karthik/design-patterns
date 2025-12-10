package com.dsahub.patterns.compound;

public interface BeatGenerator {
    void initialize();
    void start();
    void stop();
    void setBPM(int bpm);
    void setBeatListener(Runnable onBeat);
}
