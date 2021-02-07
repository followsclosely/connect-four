import net.wilson.games.connect.Simulation;
import net.wilson.games.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(200000)
                .addArtificialIntelligence(new Dummy(1))
                .addArtificialIntelligence(new YourCustomAI(2))
                .run()
                .printSummary();
    }
}