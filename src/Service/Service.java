package Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Service {


    public Service() {

    }

    public static double distance(int cursorX, int cursorY, int targetX, int targetY) {
        return Math.sqrt(Math.pow(cursorX-targetX,2) + Math.pow(cursorY-targetY,2));
    }


    public static boolean isInsideTarget(double r, int cursorX, int cursorY, int targetX, int targetY) {
        return distance(cursorX,cursorY,targetX,targetY)<=r;
    }

    public static void createNewData() {

        String fileName = "data.txt";
        String filePath = "./" + fileName; // 현재 디렉토리에 생성

        File file = new File(filePath);

        try {
            // 파일이 이미 존재하지 않으면 생성
            if (file.createNewFile()) {
                System.out.println(fileName + " 파일이 성공적으로 생성되었습니다.");
            } else {
                System.out.println(fileName + " 파일이 이미 존재합니다. 삭제 후 다시 생성합니다.");
                if (file.delete()) {
                    // 삭제 성공 시 파일을 다시 생성
                    if (file.createNewFile()) {
                        System.out.println(fileName + " 파일이 다시 생성되었습니다.");
                    } else {
                        System.out.println(fileName + " 파일 생성에 실패했습니다.");
                        System.exit(1);
                    }
                } else {
                    System.out.println(fileName + " 파일 삭제에 실패했습니다.");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void save(int level, double time, int targetX, int targetY, int cursorX, int cursorY, double r){

        String fileName = "data.txt";
        String filePath = "./" + fileName; // 현재 디렉토리에 생성

        double distance=distance(cursorX,cursorY,targetX,targetY);
        boolean isInside=isInsideTarget(r, cursorX,cursorY,targetX,targetY);

        // 작성할 문자열
        String content = Integer.toString(level) + " "
                + time+ " "
                + targetX+ " "
                + targetY+ " "
                + cursorX+ " "
                + cursorY+ " "
                + distance+ " "
                + isInside+ "\n" ;

        try {
            // 파일 객체 생성
            File file = new File(filePath);

            // 파일에 문자열 작성
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(content);
                System.out.println("파일에 문자열을 성공적으로 작성했습니다.");
            }
        } catch (IOException e) {
            System.out.println("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
        }


    }


}
