import io.github.followsclosley.connect.Simulation;

public class Main {
    public static void main(String[] args) {
        new Simulation()
                .number(1000)
                .addArtificialIntelligence(new JaronBot(2))
                .addArtificialIntelligence(new LaneAI(1, 2, 10))
                .run()
                .printSummary();
    }
}