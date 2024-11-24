package Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private int gameSequence;
    private GamePanel gamePanel;

    public GameFrame(int sequence){
        setTitle("TARGETING GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);



        gamePanel=new GamePanel();

        add(gamePanel);

        setUndecorated(true);

        setVisible(true);

        gameSequence = sequence;

    }

    public void threadRun() {
        Thread thread = new Thread(gamePanel);
        thread.start();
    }
}
