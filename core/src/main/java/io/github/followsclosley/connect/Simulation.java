package io.github.followsclosley.connect;

import io.github.followsclosley.connect.impl.ai.Dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {

    private int numberOfSimulations = 20000;
    private Map<Integer, AtomicInteger> counts = new HashMap<Integer, AtomicInteger>() {
        @Override
        public AtomicInteger get(Object key) {
            AtomicInteger value = super.get(key);
            if (value == null) {
                super.put((Integer) key, value = new AtomicInteger(0));
            }
            return value;
        }
    };
    private List<ArtificialIntelligence> ais = new ArrayList<>();

    public Simulation addArtificialIntelligence(ArtificialIntelligence ai) {
        ais.add(ai);
        return this;
    }

    public Simulation number(int simulations) {
        this.numberOfSimulations = simulations;
        return this;
    }

    public Simulation run() {

        if (ais.size() == 0) {
            System.out.println("ERROR: ai not provided, call addArtificialIntelligence()");
            return this;
        } else if (ais.size() == 1) {
            ais.add(0, new Dummy(1));
        }

        for (int i = 0; i < numberOfSimulations; i++) {

            Engine engine = new Engine(ais.toArray(new ArtificialIntelligence[ais.size()]));
            int winner = engine.startGame(i%ais.size());
            counts.get(winner).getAndIncrement();
        }

        return this;
    }

    public Simulation printSummary() {

        for (Map.Entry<Integer, AtomicInteger> entry : counts.entrySet()) {
            StringBuffer b = new StringBuffer();
            b.append("Player/Color\t").append(entry.getKey()).append(": ");
            b.append((float) (Math.round(entry.getValue().floatValue() / numberOfSimulations * 10000)) / 100).append("%\t");
            b.append(entry.getValue());
            System.out.println(b);
        }

        return this;
    }

    public Simulation reset() {
        counts.clear();
        return this;
    }
}
