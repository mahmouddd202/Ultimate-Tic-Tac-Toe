package SenArchPackage;

import java.awt.Color;
import javax.swing.JFrame;

public class Main1v1 {

    public static int WIDTH = 500;
    public static int HEIGHT = 500;
    
    public static int ROWS = 3;
    public static int MATCH = 3;
    public static int SIZE = ROWS * ROWS;
    
    public static JFrame frame;
    
    public static String playerX;
    public static String playerO;

    public Main1v1(int rows, int match, int size, String playerXName, String playerOName) {
        ROWS = rows;
        MATCH = match;
        SIZE = size;

        playerX = playerXName;
        playerO = playerOName;

        frame = new JFrame("Tic-Tac-Toe: " + playerX + " (X) vs " + playerO + " (O)");
        GamePanel1v1 game = new GamePanel1v1(new Color(0x464646), playerX, playerO);

        frame.add(game);
        frame.addMouseListener(game);
        frame.addMouseMotionListener(game);
        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
