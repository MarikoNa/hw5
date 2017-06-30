/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw;


/**
 *
 * @author mariko
 */

import java.io.*;
import java.util.*;

public class Tsp {
    static ArrayList<Double> xPos = new ArrayList<Double>();
    static ArrayList<Double> yPos = new ArrayList<Double>();
    static double totalDistance = 0;
    static double xStartPoint = 0; 
    static double yStartPoint = 0;
    static double xFirstPoint = 0;
    static double yFirstPoint = 0;
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
        for(int i=0;i<xPos.size();i++) {
            double dx = xPos.get(i)-xStartPoint;
            double dy = yPos.get(i)-yStartPoint;
            double distance = Math.sqrt( dx*dx + dy*dy );
            if(minDistance > distance) {
                minDistance = distance;
                xMin = i; yMin = i;
            }
        }
            System.out.println("x: "+xPos.get(xMin)+" y: "+yPos.get(yMin));
            xStartPoint = xPos.remove(xMin);
            yStartPoint = yPos.remove(yMin);
            totalDistance += minDistance;
    }
    
    public static void findFirstPoint() {
        double minSum = Double.MAX_VALUE;
        int minX = 0;
        int minY = 0;
        for(int i=0;i<xPos.size();i++) {
            double sum = xPos.get(i) + yPos.get(i);
            if(minSum > sum) {
                minSum = sum;
                minX = i; minY = i;               
            }
        }
        xFirstPoint = xPos.remove(minX); yFirstPoint = yPos.remove(minY);
        xStartPoint = xFirstPoint; yStartPoint = yFirstPoint;
        System.out.println("x: "+xFirstPoint+" y: "+yFirstPoint); 
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
        while(xPos.size() != 0) {
            makeRoute();
        }
        connectFirstPointStartPoint();
        System.out.println(totalDistance);
    }
    
}
