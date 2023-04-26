package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Task_2 {

    private static class Rectangle{
        int x1, y1, x2, y2;

        public Rectangle(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private final int[][] map;
    private final Map<Integer, Integer> coordsX = new HashMap<>();
    private final Map<Integer, Integer> coordsY = new HashMap<>();

    List<Integer> coordinatesX;
    List<Integer> coordinatesY;
    Task_2(List<Rectangle> rectangles) {
        Set<Integer> coordsX = new HashSet<>();
        Set<Integer> coordsY = new HashSet<>();

        for (Rectangle r : rectangles) {
            coordsX.add(r.x1); coordsX.add(r.x2);
            coordsY.add(r.y1); coordsY.add(r.y2);
            coordsX.add(r.x2 + 1); coordsY.add(r.y2 + 1);
        }
        coordinatesX = new ArrayList<>(coordsX);
        coordinatesY = new ArrayList<>(coordsY);

        Collections.sort(coordinatesX);
        Collections.sort(coordinatesY);

        for (int i = 0; i < coordinatesX.size(); i++) {
            this.coordsX.put(coordinatesX.get(i), i + 1);
        }

        for (int i = 0; i < coordinatesY.size(); i++) {
            this.coordsY.put(coordinatesY.get(i), i + 1);
        }

        map = new int[coordinatesX.size() + 1][coordinatesY.size() + 1];

        for (Rectangle r: rectangles) {
            for (int i = this.coordsX.get(r.x1); i < this.coordsX.get(r.x2) + 1; i++) {
                for (int j = this.coordsY.get(r.y1); j < this.coordsY.get(r.y2) + 1; j++) {
                    map[i][j] ++;
                }
            }
        }
    }

    private int binSearchX(Point point){
        int index = Integer.MAX_VALUE;
        int left = 0;
        int right = coordinatesX.size();
        int middle;
        if (coordinatesX.get(0) > point.x || coordinatesY.get(coordinatesY.size() - 1) < point.y) return 0;
        while (left <= right) {
            middle = left + (right - left) / 2;
            if (middle == coordinatesX.size()) return 0;
            if (coordinatesX.get(middle) < point.x) {
                left = middle + 1;
            }
            else if (coordinatesX.get(middle) > point.x) {
                right = middle - 1;
            }
            else if (coordinatesX.get(middle) == point.x) {
                index = coordsX.get(coordinatesX.get(middle));
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {
            index =  coordsX.get(coordinatesX.get(left));
            index -=  index > 1 ? 1 : 0;
        }
        return index;
    }

    private int binSearchY(Point point){
        int index = Integer.MAX_VALUE;
        int left = 0;
        int right = coordinatesY.size() ;
        int middle;
        if (coordinatesY.get(0) > point.y || coordinatesY.get(coordinatesY.size() - 1) < point.y) return 0;
        while (left <= right) {
            middle = left + (right - left) / 2;
            if (middle == coordinatesY.size()) return 0;
            if (coordinatesY.get(middle) < point.y) {
                left = middle + 1;
            }
            else if (coordinatesY.get(middle) > point.y) {
                right = middle - 1;
            }
            else if (coordinatesY.get(middle) == point.y) {
                index = coordsY.get(coordinatesY.get(middle));
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {

            index = coordsY.get(coordinatesY.get(left));
            index -= index > 1 ? 1 : 0;

        }
        return index;
    }

    public int find(Point point) {
        return map[binSearchX(point)][binSearchY(point)];
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++){
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        Task_2 task = new Task_2(rectangles);

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            System.out.print(task.find(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
    }
}
