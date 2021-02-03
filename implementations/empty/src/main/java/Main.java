import net.wilson.games.connect.Simulation;

public class Main {
    public static void main(String[] args){
        new Simulation()
                .number(2000)
                .addArtificialIntelligence(new YourCustomAI(7))
                .run()
                .printSummary();
    }
}