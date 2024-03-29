package io.github.followsclosley.connect.ai.score;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(1000)
                .addArtificialIntelligence(new ScoreStrategy(1))
                .addArtificialIntelligence(new Dummy(2))
                .run()
                .printSummary();
    }
}
