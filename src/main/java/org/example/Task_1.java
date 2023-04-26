package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task_1 {
    private static class Rectangle{
        int x1, y1, x2, y2;

        public Rectangle(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    private final List<Rectangle> rectangles= new ArrayList<>();

    public void addRectangle(Rectangle rectangle) {
        rectangles.add(rectangle);
    }

    public int find(Point point) {
        int count = 0;

        for (Rectangle rectangle : rectangles) {
            if (point.x >= rectangle.x1 && point.x <= rectangle.x2 && point.y >= rectangle.y1 && point.y <= rectangle.y2) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Task_1 task = new Task_1();

        for (int i = 0; i < n; i++){
            task.addRectangle(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            System.out.println(task.find(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
    }

}