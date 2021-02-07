package net.wilson.games.connect;

import net.wilson.games.connect.ai.ScoreStrategy;
import net.wilson.games.connect.impl.MutableBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingSupport {

    public static final int PLAYER_COLOR = 1;
    public static final int COMPUTER_COLOR = 2;

    public static void main(String[] args) {
        SwingSupport launcher = new SwingSupport();
        launcher.run(new ScoreStrategy(COMPUTER_COLOR, PLAYER_COLOR));
    }

    public void run(ArtificialIntelligence bot){
        MutableBoard board = new MutableBoard();

        BoardPanel boardPanel = new BoardPanel(board);
        //Register a listener to capture when a piece is to be played.
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 50;
                if (board.canDropPiece(x)) {
                    board.dropPiece(x, PLAYER_COLOR);

                    int x2 = bot.yourTurn(board);
                    board.dropPiece(x2, COMPUTER_COLOR);
                }
            }
        });

        board.addBoardChangedListener(coordinate -> SwingUtilities.invokeLater(() -> boardPanel.repaint()));

        JPanel statusPanel = new JPanel(new BorderLayout());
        JTextField status = new JTextField("Your turn player 1...");
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