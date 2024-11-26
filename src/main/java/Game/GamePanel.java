package main.java.Game;

import main.java.Service.Service;

import javax.swing.*;
import java.awt.*;

import static main.java.Service.Service.isInsideTarget;
import static main.java.Service.Service.save;


public class GamePanel extends JPanel implements Runnable{
    private JLabel timeLabel;
    private JLabel levelLabel;
    private double readyTime;
    private double time;
    private int targetSize;
    private double targetSpeed;
    private double initialSpeed;
    private double totalAngle;
    private boolean ready;
    private int cursor_X;
    private int cursor_Y;
    private int target_X;
    private int target_Y;
    private int sequence;


    private int level;
    //private double




    public GamePanel(int sequence){
        this.sequence = sequence;

        timeLabel = new JLabel();
        timeLabel.setLayout(null);
        timeLabel.setBounds(300, 0, 400, 200);
        timeLabel.setSize(new Dimension(400,200));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setText("3");
        add(timeLabel);

        level = -1;

        levelLabel = new JLabel();
        levelLabel.setLayout(null);
        levelLabel.setBounds(0, 0, 400, 200);
        levelLabel.setSize(new Dimension(400,200));
        levelLabel.setFont(new Font("Arial", Font.BOLD, 20));
        levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        levelLabel.setText("level: " + (level+1) + "/16");
        add(levelLabel);

        initGame();
        setBounds(0,0,1000,800);
        setLayout(null);
        setBackground(Color.white);
        setVisible(true);


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
            boolean isBreak;
            while(true) {

                setCursorCoordinate();
                timeLabel.setText("countdown: " + String.format("%.0f", readyTime));
                repaint();
                if(isInsideTarget((int)(targetSize/2), cursor_X, cursor_Y, target_X, target_Y)) {
                    readyTime -= 0.016;
                    if(readyTime <= 0) {
                        readyTime = 0;
                        ready = true;
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

            while(true) {
                // 1초간 점진적으로 속도 올라감
                calcInitialSpeed();

                timeLabel.setText("countdown: " + String.format("%.0f", time));

                time -= 0.016;
                if(time <= 10) {
                    time = 10.0;
                    isBreak = false;
                    break;
                }

                setCursorCoordinate();
                if(cursor_X == -1) {
                    // 다시 이번 단계 처음으로 이동
                    level -= 1;
                    isBreak = true;
                    break;
                }

                repaint();

                try {
                    // 16ms 대기 (약 60fps)
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(isBreak) {
                initGame();
                continue;
            }

            while (true) {
                calcTargetCenter();

                timeLabel.setText("countdown: " + String.format("%.0f", time));
                time -= 0.016;
                if(time <= 0) {
                    time = 0;
                    ready = false;
                    break;
                }
                setCursorCoordinate();
                if(cursor_X == -1) {
                    // 이번 단계 데이터 버리기
                    System.out.println(level);
                    Service.deleteGarbageData(level);
                    // 다시 이번 단계 처음으로 이동
                    level -= 1;
                    break;
                }
                save(sequence, level, time, target_X, target_Y,  cursor_X,  cursor_Y, targetSize/2);

                // 패널 다시 그리기
                repaint();

                try {
                    // 16ms 대기 (약 60fps)
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            initGame();
            if(level > 15) {
                break;
            }
        }
        System.exit(0);
    }

    public void initGame() {
        time = 12;
        target_X = 500;
        target_Y = 400;
        level += 1;
        targetSize = Service.getSize(sequence, level);
        targetSpeed = Math.toRadians(Service.getSpeed(sequence, level));
        initialSpeed = 0.0;
        totalAngle=0;
        readyTime = 3.0;
        ready = false;

        timeLabel.setText(Double.toString(readyTime));
        levelLabel.setText("level: " + (level+1) + "/16");
    }

    public void calcInitialSpeed() {
        // 점진적으로 올라가는 속도 업데이트
        initialSpeed += (targetSpeed*16)/2000;
        totalAngle += initialSpeed;

        if(totalAngle <= 2*Math.PI ) {
            target_X = 300 + (int) (200 * Math.cos(totalAngle));
            target_Y = 400 + (int) (200 * Math.sin(totalAngle));
        }
        else if(totalAngle > 2 * Math.PI) {
            target_X = 700 + (int) (200 * Math.cos(totalAngle- Math.PI));
            target_Y = 400 - (int) (200 * Math.sin(totalAngle-Math.PI));
        }
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
            cursor_X = -1;
            cursor_Y = -1;
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
