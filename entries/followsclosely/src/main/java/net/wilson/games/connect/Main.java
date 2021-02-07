package net.wilson.games.connect;

import net.wilson.games.connect.ai.ScoreStrategy;
import net.wilson.games.connect.impl.ai.Dummy;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(20000)
                .addArtificialIntelligence(new ScoreStrategy(2, 1))
                .addArtificialIntelligence(new Dummy(1))
                .run()
                .printSummary();
    }
}
