package Game;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private int gameSequence;

    public GameFrame(){
        setTitle("TARGETING GAME");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
//        setLayout(new BorderLayout());
        setLayout(null);



        GamePanel gamePanel=new GamePanel();

        add(gamePanel);

        setVisible(true);

        Thread thread=new Thread(gamePanel);
        thread.run();

        gameSequence = 0;

    }
}
