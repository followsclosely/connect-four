import net.wilson.games.connect.SwingSupport;
import net.wilson.games.connect.impl.MutableBoard;

/**
 * Class to launch a Swing interface to test your AI.
 */
public class SwingLauncher {
    public static void main(String[] args) {
        new SwingSupport()
                .setBoard(new MutableBoard())
                .setArtificialIntelligence(new YourCustomAI(SwingSupport.COMPUTER_COLOR))
                .run();
    }
}