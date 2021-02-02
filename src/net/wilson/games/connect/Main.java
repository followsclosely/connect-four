package net.wilson.games.connect;


import net.wilson.games.connect.impl.ai.Dummy;

import net.wilson.games.connect.impl.ai.followsclosely.StinkAI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {

        Map<Integer, AtomicInteger> counts = new HashMap<>() {
            @Override
            public AtomicInteger get(Object key) {
                AtomicInteger value = super.get(key);
                if (value == null) {
                    super.put((Integer) key, value = new AtomicInteger(0));
                }
                return value;
            }
        };

        int numberOfSimulations = 20000;
        for (int i = 0; i < numberOfSimulations; i++) {
            Engine engine = new Engine(new Dummy(1), new StinkAI(2));
            int winner = engine.startGame();
            counts.get(winner).getAndIncrement();
        }

        for (Map.Entry<Integer, AtomicInteger> entry : counts.entrySet()) {
            StringBuffer b = new StringBuffer();
            b.append("Player/Color\t").append(entry.getKey()).append(": ");
            b.append((float)(Math.round(entry.getValue().floatValue() / numberOfSimulations * 10000)) / 100).append("%\t");
            b.append(entry.getValue());
            System.out.println(b);
        }
    }
}