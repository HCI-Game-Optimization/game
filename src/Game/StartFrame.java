package Game;

import Service.Service;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    public StartFrame() {
        // 프레임 기본 설정
        setTitle("간단한 네모 칸");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500); // 프레임 크기
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setLayout(null); // 레이아웃을 null로 설정 (절대 위치)

        // 밝은 회색 배경색 설정 (RGB 값: 211, 211, 211)
        getContentPane().setBackground(new Color(211, 211, 211));

        // 이름 라벨 생성
        JLabel nameLabel = new JLabel("이름:");
        nameLabel.setBounds(100, 100, 100, 30); // 위치와 크기 설정
        add(nameLabel);

        // 테스트 순서 라벨 생성
        JLabel orderLabel = new JLabel("테스트 순서:");
        orderLabel.setBounds(100, 150, 100, 30); // 위치와 크기 설정
        add(orderLabel);

        // 이름 텍스트 필드 생성
        JTextField nameField = new JTextField(20);
        nameField.setBounds(190, 100, 200, 30); // 위치와 크기 설정
        nameField.setBorder(BorderFactory.createEmptyBorder()); // 테두리 제거
        add(nameField);

        // 테스트 순서 텍스트 필드 생성
        JTextField orderField = new JTextField(20);
        orderField.setBounds(190, 150, 200, 30); // 위치와 크기 설정
        orderField.setBorder(BorderFactory.createEmptyBorder()); // 테두리 제거
        add(orderField);

        // 설명 라벨 생성
        JLabel startLabel = new JLabel("<html>Lorem ipsum dolor sit amet, consectetur adipiscing elit,<br>" +
                "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.<br>" +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip<br>" +
                "ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse<br>" +
                "cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident,<br>" +
                "sunt in culpa qui officia deserunt mollit anim id est laborum.</html>");
        startLabel.setBounds(10, 200, 580, 120); // 위치와 크기 조정
        add(startLabel);

        // "Start" 버튼 생성
        JButton startButton = new JButton("Start");
        startButton.setBounds(220, 350, 140, 40); // 위치와 크기 설정

        // 버튼 배경색, 텍스트 색, 테두리 제거 및 스타일 설정
        startButton.setBackground(new Color(50, 50, 50)); // 기본 배경색 (어두운 회색)
        startButton.setForeground(Color.WHITE); // 텍스트 색 (흰색)
        startButton.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 2)); // 어두운 회색 테두리
        startButton.setFocusPainted(false); // 버튼에 포커스가 있을 때 하이라이트 없애기
        startButton.setFont(new Font("Arial", Font.BOLD, 16)); // 폰트 설정
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 커서 변경

        // 기본 Look and Feel 제거
        startButton.setContentAreaFilled(false); // 버튼의 기본 채우기 스타일 제거
        startButton.setOpaque(true); // 배경색이 적용되도록 설정

        // 버튼에 마우스를 올렸을 때 색상 변화
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(70, 70, 70)); // 마우스를 올렸을 때 배경색 변경 (중간 회색)
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(50, 50, 50)); // 마우스를 떼었을 때 배경색 원래대로 (어두운 회색)
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(30, 30, 30)); // 마우스를 클릭했을 때 배경색 변경
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(70, 70, 70)); // 마우스를 떼었을 때 배경색 변경
                startGame(Integer.parseInt(orderField.getText()));
            }
        });

        add(startButton);
    }

    public void startGame(int sequence) {
        Service.createNewData();
        GameFrame gameFrame = new GameFrame(sequence);
        gameFrame.threadRun();
    }

    public static void main(String[] args) {
        // StartFrame 객체 생성
        new StartFrame().setVisible(true);
    }
}
