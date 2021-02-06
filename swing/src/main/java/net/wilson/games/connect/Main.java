package net.wilson.games.connect;

import net.wilson.games.connect.ai.ScoreStrategy;
import net.wilson.games.connect.impl.MutableBoard;
import net.wilson.games.connect.impl.ai.Dummy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {

        MutableBoard board = new MutableBoard();
        ArtificialIntelligence bot = new ScoreStrategy(2);

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

        JPanel statusPanel = new JPanel(new BorderLayout());
        JTextField status = new JTextField("Your turn player 1...");
        status.setEditable(false);
        statusPanel.add(status, BorderLayout.CENTER);
        JButton undo = new JButton("<");
        undo.addActionListener((ActionEvent e) -> {
            SwingUtilities.invokeLater(() -> {
                board.undo();
                board.undo();
                boardPanel.repaint();
            });
        });
        statusPanel.add(undo, BorderLayout.EAST);


        // Run GUI in the Event Dispatcher Thread (EDT) instead of main thread.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Set up main window (using Swing's Jframe)
                JFrame frame = new JFrame("Connect");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setContentPane(boardPanel);
                frame.setLayout(new BorderLayout());
                GridBagConstraints c = new GridBagConstraints();
                frame.add(boardPanel, BorderLayout.CENTER);
                frame.add(statusPanel, BorderLayout.SOUTH);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}