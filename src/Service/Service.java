package Service;

public class Service {


    public Service() {

    }

    public static double distance(int cursorX, int cursorY, int targetX, int targetY) {
        return Math.sqrt(Math.pow(cursorX-targetX,2) + Math.pow(cursorY-targetY,2));
    }


    public static boolean isInsideTarget(double r, int cursorX, int cursorY, int targetX, int targetY) {
        return distance(cursorX,cursorY,targetX,targetY)<=r;
    }

    //public static void save

}
