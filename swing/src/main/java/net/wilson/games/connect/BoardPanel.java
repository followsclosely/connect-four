package net.wilson.games.connect;


import javax.swing.*;
import java.awt.*;

/**
 * This panel draws the connect four board given a Board
 *
 * @see net.wilson.games.connect.Board
 */
public class BoardPanel extends JPanel {

    protected Dimension defaultDimension;

    private Color[] COLORS = {Color.GRAY, Color.GRAY, Color.RED, Color.BLACK};

    private Board board;

    public BoardPanel(Board board) {
        this.board = board;
        this.defaultDimension = new Dimension(board.getWidth() * 50 - 5, board.getHeight() * 50 - 5);
    }

    @Override
    public Dimension getPreferredSize() {
        return defaultDimension;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int x = 0, width = board.getWidth(); x < width; x++) {
            for (int y = 0, height = board.getHeight(); y < height; y++) {
                g.setColor(COLORS[board.getPiece(x, y) + 1]);
                g.fillRoundRect(x * 50, y * 50, 45, 45, 45, 45);
            }
        }
    }
}