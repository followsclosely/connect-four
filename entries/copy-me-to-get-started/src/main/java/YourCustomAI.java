import net.wilson.games.connect.ArtificialIntelligence;
import net.wilson.games.connect.Board;
import net.wilson.games.connect.impl.MutableBoard;

import java.util.Random;

/**
 * A totally random impl of AI.
 */
public class YourCustomAI extends ArtificialIntelligence {

    private Random random = new Random();

    public YourCustomAI(int color) {
        super(color);
    }

    @Override
    public int yourTurn(Board board) {
        MutableBoard myBoard = new MutableBoard(board);

        //Select a random place on the board
        int x = random.nextInt(board.getWidth());

        //Keep adding one to the random spot while canDropPiece is false or we get to the width of the board
        for (int i = 0, width = board.getWidth(); i < width; i++, x = (x + 1) % board.getWidth()) {
            if (myBoard.canDropPiece(x)) {
                return x;
            }
        }

        return -1;
    }
}