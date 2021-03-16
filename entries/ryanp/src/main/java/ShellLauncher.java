import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(200000)
                .addArtificialIntelligence(new Dummy(1))
                .addArtificialIntelligence(new MonteCarloAI(2))
                .run()
                .printSummary();
    }
}