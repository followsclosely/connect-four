package io.github.ryanp102694.connect;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.ryanp102694.connect.MonteCarloAI;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(1000)
                .addArtificialIntelligence(new Dummy(1))
                .addArtificialIntelligence(new MonteCarloAI(2))
                .run()
                .printSummary();
    }
}