package main.java.Service;
import java.io.*;
import java.util.*;

public class TargetAnalysis {
    public static void main(String[] args) {
        String fileName = "HCIdata.txt"; // 분석할 파일 이름
        Map<Integer, int[]> difficultyStats = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length != 8) continue;

                // 데이터 파싱
                int difficulty = Integer.parseInt(tokens[0]); // 첫 번째 값이 난이도
                boolean isInside = Boolean.parseBoolean(tokens[7]); // 마지막 값이 타겟 안 여부

                // 난이도별 통계 업데이트
                difficultyStats.putIfAbsent(difficulty, new int[2]);
                int[] stats = difficultyStats.get(difficulty);
                stats[1]++; // 전체 카운트 증가
                if (isInside) {
                    stats[0]++; // 안쪽에 존재 카운트 증가
                }
            }

            // 결과 출력
            System.out.println("난이도별 타겟이 안쪽에 존재하는 비율:");
            for (Map.Entry<Integer, int[]> entry : difficultyStats.entrySet()) {
                int difficulty = entry.getKey();
                int[] stats = entry.getValue();
                double percentage = (stats[0] / (double) stats[1]) * 100;
                System.out.printf("난이도 %d: %.2f%% (%d/%d)%n", difficulty, percentage, stats[0], stats[1]);
            }
        } catch (IOException e) {
            System.err.println("파일 읽기 오류: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("데이터 파싱 오류: " + e.getMessage());
        }
    }
}
