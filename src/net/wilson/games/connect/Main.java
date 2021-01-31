package net.wilson.games.connect;


import net.wilson.games.connect.impl.ai.Dummy;
import net.wilson.games.connect.impl.ai.JaronBot;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine(new JaronBot(1), new Dummy(2));
        engine.startGame();
    }
}