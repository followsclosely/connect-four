package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.ai.score.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.ryanp102694.connect.MonteCarloAI;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(10)
                .addArtificialIntelligence(new MiniMaxWithAlphaBeta(1, 9))
                .addArtificialIntelligence(new MonteCarloAI(2))
                .run()
                .printSummary();
        System.exit(0);
    }
}
