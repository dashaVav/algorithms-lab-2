package solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task1 {
    List<Rectangle> rectangles;
    public Task1(List<Rectangle> rectangles) {
        this.rectangles = rectangles;
    }

    public int find(Point point) {
        int count = 0;

        for (Rectangle rectangle : rectangles) {
            if (point.x >= rectangle.x1 && point.x <= rectangle.x2
                    && point.y >= rectangle.y1 && point.y <= rectangle.y2) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++){
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        Task1 task = new Task1(rectangles);
        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            System.out.println(task.find(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
    }

}