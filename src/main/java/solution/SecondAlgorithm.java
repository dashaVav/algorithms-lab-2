package solution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SecondAlgorithm {
    private final int[][] map;
    List<Integer> coordinatesX;
    List<Integer> coordinatesY;

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

        coordinatesX = new ArrayList<>(setX);
        coordinatesY = new ArrayList<>(setY);

        Collections.sort(coordinatesX);
        Collections.sort(coordinatesY);

        Map<Integer, Integer> coordsX = new HashMap<>();
        Map<Integer, Integer> coordsY = new HashMap<>();

        for (int i = 0; i < coordinatesX.size(); i++) {
            coordsX.put(coordinatesX.get(i), i);
        }

        for (int i = 0; i < coordinatesY.size(); i++) {
            coordsY.put(coordinatesY.get(i), i);
        }

        map = new int[coordinatesX.size()][coordinatesY.size()];

        for (Rectangle r: rectangles) {
            for (int i = coordsX.get(r.x1); i < coordsX.get(r.x2) + 1; i++) {
                for (int j = coordsY.get(r.y1); j < coordsY.get(r.y2) + 1; j++) {
                    map[i][j] ++;
                }
            }
        }
    }

    public int binSearch(Integer num, List<Integer> coordinates){
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

    public int find(Point point) {
        int i = binSearch(point.x, coordinatesX);
        int j = binSearch(point.y, coordinatesY);
        if (i == -1 || j == -1) return 0;
        return map[i][j];
    }

}
