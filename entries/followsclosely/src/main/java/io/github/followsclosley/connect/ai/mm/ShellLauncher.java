package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.Simulation;
import io.github.ryanp102694.connect.MonteCarloAI;

import java.util.concurrent.TimeUnit;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(10)
                .addArtificialIntelligence(new MiniMaxWithAlphaBeta(1, 7)
                        .setTimeout(5, TimeUnit.SECONDS)
                )
                .addArtificialIntelligence(new MonteCarloAI(2))
                .run()
                .printSummary();
        System.exit(0);
    }
}
