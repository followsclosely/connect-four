package io.github.jaron.connect;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.ai.score.ScoreStrategy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(10000)
                .addArtificialIntelligence(new ScoreStrategy(7))
                .addArtificialIntelligence(new JaronBot(3))
                .run()
                .printSummary();
    }
}