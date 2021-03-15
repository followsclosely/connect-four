package io.github.jaron.connect;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(10000)
                .addArtificialIntelligence(new ScoreStrategy(7, 1,2,3))
                .addArtificialIntelligence(new JaronBot(3))
                .run()
                .printSummary();
    }
}