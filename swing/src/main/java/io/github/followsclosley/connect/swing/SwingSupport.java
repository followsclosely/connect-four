package io.github.followsclosley.connect.swing;

import io.github.followsclosley.connect.ArtificialIntelligence;
import io.github.followsclosley.connect.impl.MutableBoard;
import io.github.followsclosley.connect.impl.ai.Dummy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class uses a builder patter to launch a swing UI to
 * test your AI.
 */
public class SwingSupport {

    public static final int PLAYER_COLOR = 1;
    public static final int COMPUTER_COLOR = 2;

    private ArtificialIntelligence bot;
    private MutableBoard board;

    public static void main(String[] args) {
        new SwingSupport()
                .setArtificialIntelligence(new Dummy(COMPUTER_COLOR))
                .run();
    }

    /**
     * You can pass in your own board. This allows you to:
     * <ol>
     *     <li>Set up the state of the board</li>
     *     <li>To configure the size of the board</li>
     *     <li>To configure other settings suck as the goal</li>
     * </ol>
     *
     * @param board The board to hold the state of the game
     * @return this to keep the builder going
     */
    public SwingSupport setBoard(MutableBoard board) {
        this.board = board;
        return this;
    }

    public SwingSupport setArtificialIntelligence(ArtificialIntelligence bot) {
        this.bot = bot;
        return this;
    }

    /**
     * Launches the JFrame that contains the BoardPanel to display the game.
     */
    public void run() {

        if (board == null) {
            board = new MutableBoard();
        }

        bot.initialize(PLAYER_COLOR);

        BoardPanel boardPanel = new BoardPanel(board);
        //boardPanel.loadPrettyImages();

        //Register a listener to capture when a piece is to be played.
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / BoardPanel.PIECE_SIZE;
                if (board.canDropPiece(x)) {
                    board.dropPiece(x, PLAYER_COLOR);

                    new Thread(() -> {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException ignore) {
                        }
                        board.dropPiece(bot.yourTurn(board), COMPUTER_COLOR);
                    }).start();

                }
            }
        });

        board.addBoardChangedListener(coordinate -> SwingUtilities.invokeLater(boardPanel::repaint));

        JPanel statusPanel = new JPanel(new BorderLayout());
        JTextField status = new JTextField("");
        status.setEditable(false);
        statusPanel.add(status, BorderLayout.CENTER);
        JButton undo = new JButton("<");
        undo.addActionListener((ActionEvent e) -> SwingUtilities.invokeLater(() -> board.undo()));
        statusPanel.add(undo, BorderLayout.EAST);

        JFrame frame = new JFrame("Connect");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        GridBagConstraints c = new GridBagConstraints();
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}