import io.github.followsclosley.connect.Simulation;
import io.github.followsclosley.connect.impl.ai.Dummy;

public class ShellLauncher {
    public static void main(String[] args) {
        new Simulation()
                .number(100)
                .addArtificialIntelligence(new Dummy(1))
                .addArtificialIntelligence(new LaneAI(2, 1, 5))
                .run()
                .printSummary();
    }
}