package solution;

import java.awt.*;
import java.util.List;

public class FirstAlgorithm {
    List<Rectangle> rectangles;
    public FirstAlgorithm(List<Rectangle> rectangles) {
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

}