package net.wilson.games.connect;

import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.Dummy;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {

        MutableBoard board = new MutableBoard();
        ArtificialIntelligence bot = new Dummy(2);

        BoardPanel boardPanel = new BoardPanel(board);
        //Register a listener to capture when a piece is to be played.
        boardPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 50;
                if( board.canDropPiece(x)){
                    board.dropPiece(x, 1);
                    SwingUtilities.invokeLater(() -> {boardPanel.repaint();});

                    SwingUtilities.invokeLater(() -> {
                        int x2 = bot.yourTurn(board);
                        board.dropPiece(x2, 2);
                        boardPanel.repaint();
                    });
                }
            }
        });

        // Run GUI in the Event Dispatcher Thread (EDT) instead of main thread.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Set up main window (using Swing's Jframe)
                JFrame frame = new JFrame("Connect");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(boardPanel);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}