package io.github.followsclosley.connect.ai.mm;

import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.ai.score.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.ryanp102694.connect.MonteCarloAI;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(100)
                .addArtificialIntelligence(new MiniMaxWithAlphaBeta(1, 3))
                .addArtificialIntelligence(new MonteCarloAI(2))
                .run()
                .printSummary();
    }
}
