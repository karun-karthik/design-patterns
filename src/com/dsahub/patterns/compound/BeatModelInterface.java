package com.dsahub.patterns.compound;

public interface BeatModelInterface {
    void initialize();
    void on();
    void off();
    void setBPM(int bpm);
    int getBPM();

    void registerBeatObserver(BeatObserver o);
    void removeBeatObserver(BeatObserver o);

    void registerBPMObserver(BPMObserver o);
    void removeBPMObserver(BPMObserver o);
}
