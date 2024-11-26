package Service;

import java.io.*;
import java.util.regex.*;

public class Service {


    public Service() {

    }

    public static double distance(int cursorX, int cursorY, int targetX, int targetY) {
        return Math.sqrt(Math.pow(cursorX-targetX,2) + Math.pow(cursorY-targetY,2));
    }


    public static boolean isInsideTarget(double r, int cursorX, int cursorY, int targetX, int targetY) {
        return distance(cursorX,cursorY,targetX,targetY)<=r;
    }

    public static double getSpeed(int sequence, int level) {
        String filePath = "src/Data/balanced_ratin_square.txt";
        String ID = "";
        double speed = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 파일에서 한 줄씩 읽기
            while ((line = br.readLine()) != null) {
                if(line.equals("sequence = " + sequence)) {
                    ID = br.readLine().substring(level, level+1);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        filePath = "src/Data/ID.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 파일에서 한 줄씩 읽기
            while ((line = br.readLine()) != null) {
                if(line.equals("ID = " + ID)) {
                    speed = Double.parseDouble(br.readLine().split(" ")[2]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return speed;
    }

    public static int getSize(int sequence, int level) {
        String filePath = "src/Data/balanced_ratin_square.txt";
        String ID = "";
        int size = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 파일에서 한 줄씩 읽기
            while ((line = br.readLine()) != null) {
                if(line.equals("sequence = " + sequence)) {
                    ID = br.readLine().substring(level, level+1);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        filePath = "src/Data/ID.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // 파일에서 한 줄씩 읽기
            while ((line = br.readLine()) != null) {
                if(line.equals("ID = " + ID)) {
                    br.readLine();
                    size = Integer.parseInt(br.readLine().split(" ")[2]);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    public static void createNewData(String name, int sequence) {

        String fileName = "data.txt";
        String filePath = "./src/Data/" + fileName; // 현재 디렉토리에 생성

        File file = new File(filePath);

        try {
            // 파일이 이미 존재하지 않으면 생성
            if (file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write("이름:" + name + "순서:" + sequence + "\n");
                }
                System.out.println(fileName + " 파일이 성공적으로 생성되었습니다.");
            } else {
                System.out.println(fileName + " 파일이 이미 존재합니다. 삭제 후 다시 생성합니다.");
                if (file.delete()) {
                    // 삭제 성공 시 파일을 다시 생성
                    if (file.createNewFile()) {
                        try (FileWriter writer = new FileWriter(file, true)) {
                            writer.write("이름:" + name + "순서:" + sequence + "\n");
                        }
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

    public static void deleteGarbageData(int level) {
        String fileName = "data.txt";
        String filePath = "./src/Data/" + fileName; // 현재 디렉토리
        File inputFile = new File(filePath);

        String regex = "^" + Integer.toString(level) + "\\s+\\S+\\b.*$";
        Pattern pattern = Pattern.compile(regex);

        // 데이터를 임시로 저장할 StringBuilder
        StringBuilder validData = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String currentLine;

            // 조건에 맞는 줄을 제외하고 유효한 데이터만 StringBuilder에 저장
            while ((currentLine = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(currentLine);
                if (!matcher.matches()) {
                    validData.append(currentLine).append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 중 오류 발생: " + e.getMessage());
            return;
        }

        // 유효한 데이터로 원본 파일 덮어쓰기
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
            writer.write(validData.toString());
            System.out.println("조건에 맞는 줄이 삭제되고 파일이 갱신되었습니다.");
        } catch (IOException e) {
            System.out.println("파일 쓰기 중 오류 발생: " + e.getMessage());
        }
    }

    public static void save(int sequence, int level, double time, int targetX, int targetY, int cursorX, int cursorY, double r){

        String fileName = "data.txt";
        String filePath = "./src/Data/" + fileName; // 현재 디렉토리에 생성

        double distance=distance(cursorX,cursorY,targetX,targetY);
        boolean isInside=isInsideTarget(r, cursorX,cursorY,targetX,targetY);

        // 작성할 문자열
        String content =
                level + " "
                + time + " "
                + targetX + " "
                + targetY + " "
                + cursorX + " "
                + cursorY + " "
                + distance + " "
                + isInside + "\n" ;

        try {
            // 파일 객체 생성
            File file = new File(filePath);

            // 파일에 문자열 작성
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(content);
            }
        } catch (IOException e) {
            System.out.println("파일 처리 중 오류가 발생했습니다: " + e.getMessage());
        }


    }


}
