package net.wilson.games.connect;

import net.wilson.games.connect.ai.Dummy;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine(new Dummy(1), new Dummy(2));
        engine.startGame();
    }
}