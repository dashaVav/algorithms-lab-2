package solution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SecondAlgorithm {
    private final int[][] map;
    List<Integer> zipX;
    List<Integer> zipY;

    public SecondAlgorithm(List<Rectangle> rectangles) {
        Set<Integer> setX = new HashSet<>();
        Set<Integer> setY = new HashSet<>();

        for (Rectangle r : rectangles) {
            setX.add(r.x1);
            setX.add(r.x2);
            setY.add(r.y1);
            setY.add(r.y2);
            setX.add(r.x2 + 1);
            setY.add(r.y2 + 1);
        }

        zipX = new ArrayList<>(setX);
        zipY = new ArrayList<>(setY);

        Collections.sort(zipX);
        Collections.sort(zipY);

        Map<Integer, Integer> coordsX = new HashMap<>();
        Map<Integer, Integer> coordsY = new HashMap<>();

        for (int i = 0; i < zipX.size(); i++) {
            coordsX.put(zipX.get(i), i);
        }

        for (int i = 0; i < zipY.size(); i++) {
            coordsY.put(zipY.get(i), i);
        }

        map = new int[zipX.size()][zipY.size()];

        for (Rectangle r: rectangles) {
            for (int i = coordsX.get(r.x1); i < coordsX.get(r.x2) + 1; i++) {
                for (int j = coordsY.get(r.y1); j < coordsY.get(r.y2) + 1; j++) {
                    map[i][j] ++;
                }
            }
        }
    }

    private int binSearch(Integer num, List<Integer> coordinates){
        int left = 0;
        int right = coordinates.size();

        while (left < right) {
            int middle = left + (right - left) / 2;
            if (coordinates.get(middle) <= num) {
                left = middle + 1;
            }
            else{
                right = middle;
            }
        }
        return left - 1;
    }

    public int search(Point point) {
        int i = binSearch(point.x, zipX);
        int j = binSearch(point.y, zipY);
        if (i == -1 || j == -1) return 0;
        return map[i][j];
    }

}
