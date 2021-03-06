import java.io.*;
import java.util.*;

public class Tsp3 {
    static ArrayList<Double> xPos = new ArrayList<Double>();
    static ArrayList<Double> yPos = new ArrayList<Double>();
    static ArrayList<Double> xList = new ArrayList<Double>();
    static ArrayList<Double> yList = new ArrayList<Double>();
    static double totalDistance = 0;
    static double xStartPoint = 0; 
    static double yStartPoint = 0;
    static double xFirstPoint = 0;
    static double yFirstPoint = 0;
    static double xMax = 0;
    static double xMin = Double.MAX_VALUE;
    static double yMax = 0;
    static double yMin = Double.MAX_VALUE;
    static double xRange = 0;
    static double yRange = 0;
    /**
     * @param args the command line arguments
     */
    public static void readCSVFile(String fileName){
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine(); 
            StringTokenizer token;
            while((line = br.readLine()) != null){
                token = new StringTokenizer(line,",");
                double x = Double.parseDouble(token.nextToken());
                double y = Double.parseDouble(token.nextToken());
                xPos.add(x);
                yPos.add(y);
            }
            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
 
    public static void makeRoute() { 
        double minDistance = Double.MAX_VALUE;
        int xMin = 0;
        int yMin = 0;
        for(int i=0;i<xList.size();i++) {
            double dx = xList.get(i)-xStartPoint;
            double dy = yList.get(i)-yStartPoint;
            double distance = Math.sqrt( dx*dx + dy*dy );
            if(minDistance > distance) {
                minDistance = distance;
                xMin = i; yMin = i;
            }
        }
            System.out.println("x: "+xList.get(xMin)+" y: "+yList.get(yMin));
            xStartPoint = xList.remove(xMin);
            yStartPoint = yList.remove(yMin);
            totalDistance += minDistance;
    }
    
    public static void makeXYList(double startX, double endX, double startY, double endY) {
        xList.clear();
        yList.clear();
        for (int i = 0; i < xPos.size(); i++) {
            double x = xPos.get(i);
            double y = yPos.get(i);
            if (x >= startX && x < endX) {
		if(y >= startY && y < endY) {
		    x = xPos.remove(i);
		    y = yPos.remove(i);
		    xList.add(x);
		    yList.add(y);
		    i--;
		}
	    }
        }
        while (xList.size() != 0) {
            makeRoute();
        }
    }
    
    public static void findFirstPoint() {
        double minX = Double.MAX_VALUE;
        int minXNumber = 0;
        for (int i = 0; i < xPos.size(); i++) {
            double y = yPos.get(i);
            if (y < yMin+yRange/2) { //y<yMin + range/2以下のところで最もxの値が小さいもの。
                double x = xPos.get(i);
                if (minX > x) {
                    minX = x;
                    minXNumber = i;
                }
            }
        }
        xFirstPoint = xPos.remove(minXNumber);
        yFirstPoint = yPos.remove(minXNumber);
        xStartPoint = xFirstPoint;
        yStartPoint = yFirstPoint;
        System.out.println("x: " + xFirstPoint + " y: " + yFirstPoint);
    }
    
    public static void findRange() {
        xMax = 0;
        xMin = Double.MAX_VALUE;
        yMax = 0;
        yMin = Double.MAX_VALUE;
        for (int i = 0; i < xPos.size(); i++) {
            double x = xPos.get(i);
            double y = yPos.get(i);
            if (x > xMax) {
                xMax = x;
            }
            if (x < xMin) {
                xMin = x;
            }
            if (y > yMax) {
                yMax = y;
            }
            if (y < yMin) {
                yMin = y;
            }
        }
        xRange = xMax - xMin;
        yRange = yMax - yMin;
    }
    
    public static void connectFirstPointStartPoint() {
        double dx = xFirstPoint - xStartPoint;
        double dy = yFirstPoint - yStartPoint;
        double distance = Math.sqrt( dx*dx + dy*dy );
        totalDistance += distance;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        String fileName = sc.next();
        readCSVFile(fileName);
        findFirstPoint();
        findRange();
        makeXYList(0,xMin+xRange/2, 0, yMin+yRange/2);
        makeXYList(xMin+xRange/2,Double.MAX_VALUE, 0, yMin+yRange/2);
        makeXYList(xMin+xRange/2,Double.MAX_VALUE,yMin+yRange/2,Double.MAX_VALUE);
        makeXYList(0,xMin+xRange/2, yMin+yRange/2,Double.MAX_VALUE);
        connectFirstPointStartPoint();
        System.out.println(totalDistance);
    }
    
}
