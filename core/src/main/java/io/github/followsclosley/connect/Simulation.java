package io.github.followsclosley.connect;

import io.github.followsclosley.connect.impl.ai.Dummy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {

    private final Map<Integer, AtomicInteger> counts = new HashMap<>() {
        @Override
        public AtomicInteger get(Object key) {
            AtomicInteger value = super.get(key);
            if (value == null) {
                super.put((Integer) key, value = new AtomicInteger(0));
            }
            return value;
        }
    };
    ArtificialIntelligence ai1, ai2;
    private int numberOfSimulations = 20000;

    public Simulation addArtificialIntelligence(ArtificialIntelligence ai) {
        if (this.ai1 == null) {
            this.ai1 = ai;
        } else {
            this.ai2 = ai;
        }
        return this;
    }

    public Simulation number(int simulations) {
        this.numberOfSimulations = simulations;
        return this;
    }

    public Simulation run() {

        if (ai1 == null) {
            System.out.println("ERROR: ai not provided, call addArtificialIntelligence()");
            return this;
        }
        if (ai2 == null) {
            ai2 = new Dummy(ai1.getColor() + 1);
        }

        for (int i = 1; i <= numberOfSimulations; i++) {
            Engine engine = (i % 2 == 0) ? new Engine(ai1, ai2) : new Engine(ai2, ai1);
            int winner = engine.startGame();
            counts.get(winner).getAndIncrement();
            System.out.print("\r" + i + "/" + numberOfSimulations);

//            if( winner == 1) {
//                for (Coordinate c : engine.getBoard().getTurns()){
//                    System.out.println(String.format("board.dropPiece(%d,%d);", c.getX(), engine.getBoard().getPiece(c.getX(), c.getY())));
//                }
//                System.exit(0);
//            }
        }
        System.out.println();

        return this;
    }

    public void printSummary() {
        for (Map.Entry<Integer, AtomicInteger> entry : counts.entrySet()) {
            String b = "Player/Color\t" + entry.getKey() + ": " +
                    ((float) (Math.round(entry.getValue().floatValue() / numberOfSimulations * 10000)) / 100) + "%\t" +
                    entry.getValue();
            System.out.println(b);
        }
    }

    public Map<Integer, AtomicInteger> getCounts() {
        return counts;
    }

    public Simulation reset() {
        counts.clear();
        return this;
    }
}
