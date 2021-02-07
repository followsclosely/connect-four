import net.wilson.games.connect.SwingSupport;

public class SwingLauncher {
    public static void main(String[] args) {
        SwingSupport launcher = new SwingSupport();
        launcher.run(new YourCustomAI(SwingSupport.COMPUTER_COLOR));
    }
}