package main.java.Game;

import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;

    public GameFrame(int sequence){
        setTitle("TARGETING GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(null);



        gamePanel=new GamePanel(sequence);

        add(gamePanel);

        setUndecorated(true);

        setVisible(true);

    }

    public void threadRun() {
        Thread thread = new Thread(gamePanel);
        thread.start();
    }
}
