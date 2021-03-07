import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.ai.ScoreStrategy;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(200000)
                .addArtificialIntelligence(new ScoreStrategy(1))
                .addArtificialIntelligence(new JaronBot(2))
                .run()
                .printSummary();
    }
}