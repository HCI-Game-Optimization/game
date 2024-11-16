package Game;

import javax.swing.*;
import java.awt.*;

import static Service.Service.isInsideTarget;
import static Service.Service.save;


public class GamePanel extends JPanel implements Runnable{
    private JLabel timeLabel = new JLabel();
    private double time;
    private double readyTime;
    private int targetSize;
    private double targetSpeed;
    private double totalAngle;
    private boolean ready;
    private int cursor_X;
    private int cursor_Y;
    private int target_X;
    private int target_Y;
    private int leftCircle_X=300;


    private int level;
    //private double




    public GamePanel(){


        initGame(100, Math.toRadians(3.6));
        setBounds(0,0,1000,800);
        setBackground(Color.white);
        setVisible(true);


        add(timeLabel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setLayout(null);
        g.drawOval(target_X-targetSize/2,target_Y-targetSize/2,targetSize,targetSize); //draw target
        g.drawOval(100,200,400,400); //left circle
        g.drawOval(500,200,400,400); //right circle
    }

    //speed : 4 6 8 10 per 10 seconds
    // 10 : 7.2도/s
    @Override
    public void run() {
        while(true) {

            setCursorCoordinate();
            timeLabel.setText("시작까지: " + String.format("%.2f", readyTime));
            repaint();
            if(isInsideTarget((int)(targetSize/2), cursor_X, cursor_Y, target_X, target_Y)) {
                readyTime -= 0.016;
                if(readyTime <= 0) {
                    break;
                }
            }
            else {
                readyTime = 3;
            }

            try {
                // 16ms 대기 (약 60fps)
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            calcTargetCenter();
            save(level, time, target_X, target_Y,  cursor_X,  cursor_Y, targetSize/2);

            // 패널 다시 그리기
            repaint();

            try {
                // 16ms 대기 (약 60fps)
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void initGame(int size, double speed) {
        time = 10.0;
        target_X = 500;
        target_Y = 400;
        this.targetSize = size;
        this.targetSpeed = speed;
        totalAngle=0;
        readyTime = 3.0;

        timeLabel.setText("3");
        timeLabel.setBounds(500, 400, 800, 200);
        timeLabel.setSize(new Dimension(800,200));
    }

    public void calcTargetCenter() {
        // 원의 위치 업데이트

        if(totalAngle > 4* Math.PI-targetSpeed) totalAngle=0;
        totalAngle+= targetSpeed;
        //if(totalAngle >= 4* Math.PI) totalAngle=0;

        if(totalAngle <= 2*Math.PI ) {
            target_X = 300 + (int) (200 * Math.cos(totalAngle));
            target_Y = 400 + (int) (200 * Math.sin(totalAngle));
        }
        else if(totalAngle > 2 * Math.PI) {
            target_X = 700 + (int) (200 * Math.cos(totalAngle- Math.PI));
            target_Y = 400 - (int) (200 * Math.sin(totalAngle-Math.PI));
        }


    }

    public void setCursorCoordinate() {
        try{
            cursor_X = (int) getMousePosition().getX();
            cursor_Y = (int) getMousePosition().getY();
        }catch (NullPointerException e){
            cursor_X=cursor_X;
            cursor_Y=cursor_Y;
        }
    }

    public int[] getCursorCoordinate() {
        return new int[]{1, 3};
    }

    public void drawTimeLabel() {

    }

    public void setTargetSize(int size) {
        this.targetSize = size;
    }

    public void setTargetSpeed(int speed) {
        this.targetSpeed = speed;
    }

}
