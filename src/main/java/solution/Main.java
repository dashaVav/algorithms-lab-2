package solution;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<Rectangle> rectangles = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }
        //нужно выбрать алгоритм, который запустится
//        FirstAlgorithm task = new FirstAlgorithm(rectangles);
//        SecondAlgorithm task = new SecondAlgorithm(rectangles);
        ThirdAlgorithm task = new ThirdAlgorithm(rectangles);

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            System.out.print(task.find(new Point(scanner.nextInt(), scanner.nextInt())) + " ");
        }
    }
}
