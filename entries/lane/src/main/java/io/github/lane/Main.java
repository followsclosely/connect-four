package io.github.lane;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(1000)
                .addArtificialIntelligence(new Dummy(2))
                .addArtificialIntelligence(new LaneAI(1, 2, 10))
                .run()
                .printSummary();
    }
}