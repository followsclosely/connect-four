package io.github.followsclosley.connect;

import io.github.followsclosley.connect.ai.MinMax;
import io.github.followsclosley.connect.ai.MiniMaxAlgorithm;
import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;
import io.github.ryanp102694.connect.MonteCarloAI;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(1000)
                .addArtificialIntelligence(new MinMax(1, 9))
                .addArtificialIntelligence(new Dummy(2))
                .run()
                .printSummary();
    }
}
