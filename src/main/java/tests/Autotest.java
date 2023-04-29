package tests;

import solution.Rectangle;
import solution.FirstAlgorithm;
import solution.SecondAlgorithm;
import solution.ThirdAlgorithm;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Autotest {
    private static List<Rectangle> generateTestRectangles(int n){
        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rectangles.add(new Rectangle(10 * i, 10 * i, 10 * (2 * n - 1), 10 * (2 * n - 1)));
        }
        return rectangles;
    }

    private static List<Point> generateTestPoints(int n){
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = (int) Math.pow(1009 * i, 31) % (20 * n);
            int y = (int) Math.pow(1013 * i, 31) % (20 * n);
            points.add(new Point(x, y));
        }
        return points;
    }

    public static void main(String[] args) throws IOException {
        long start, built, search;
        List<Point> points = generateTestPoints(100000);

        PrintWriter writerFirstAlgorithm = new PrintWriter("artefacts/BruteForceAlgorithmResults.csv");
        writerFirstAlgorithm.write("Rectangles count; Build time; Request time; Total time\n");
        PrintWriter writerSecondAlgorithm = new PrintWriter("artefacts/MapAlgorithmResults.csv");
        writerSecondAlgorithm.write("Rectangles count; Build time; Request time; Total time\n");
        PrintWriter writerThirdAlgorithm = new PrintWriter("artefacts/TreeAlgorithmResults.csv");
        writerThirdAlgorithm.write("Rectangles count; Build time; Request time; Total time\n");

        writerFirstAlgorithm.write("");
        for (int i = 0; i < 15; i++){
            List<Rectangle> rectangles = generateTestRectangles((int) Math.pow(2, i));
            int I = (int) Math.pow(2, i);
            System.out.println(I);
            start = System.nanoTime();
            FirstAlgorithm firstAlgorithm = new FirstAlgorithm(rectangles);
            built = System.nanoTime() - start;
            start = System.nanoTime();
            points.forEach(firstAlgorithm::search);
            search = System.nanoTime() - start;
            writerFirstAlgorithm.write(I + "; " + built + "; " + search + "; " + (built + search) + "\n");

            if (i < 13) {
                start = System.nanoTime();
                SecondAlgorithm secondAlgorithm = new SecondAlgorithm(rectangles);
                built = System.nanoTime() - start;
                start = System.nanoTime();
                points.forEach(secondAlgorithm::search);
                search = System.nanoTime() - start;
                writerSecondAlgorithm.write(I + "; " + built + "; " + search + "; " + (built + search) + "\n");
            }

            start = System.nanoTime();
            ThirdAlgorithm thirdAlgorithm = new ThirdAlgorithm(rectangles);
            built = System.nanoTime() - start;
            start = System.nanoTime();
            points.forEach(thirdAlgorithm::search);
            search = System.nanoTime() - start;
            writerThirdAlgorithm.write(I + "; " + built + "; " + search + "; " + (built + search) + "\n");
        }

        writerFirstAlgorithm.close();
        writerSecondAlgorithm.close();
        writerThirdAlgorithm.close();
    }

}
