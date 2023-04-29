package solution;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ThirdAlgorithm {
    private static class EventComparator implements Comparator<Event> {
        public int compare(Event e1, Event e2) {
            return e1.n - e2.n;
        }
    }

    PersistentSegmentTree persistentSegmentTree;
    List<Integer> coordinatesX;
    List<Integer> coordinatesY;

    public ThirdAlgorithm(List<Rectangle> rectangles) {
        if (rectangles.size() == 0) return;
        Set<Integer> coordsX = new HashSet<>();
        Set<Integer> coordsY = new HashSet<>();

        for (Rectangle r : rectangles) {
            coordsX.add(r.x1);
            coordsY.add(r.y1);
            coordsX.add(r.x2 + 1);
            coordsY.add(r.y2 + 1);
        }
        coordinatesX = new ArrayList<>(coordsX);
        coordinatesY = new ArrayList<>(coordsY);

        Collections.sort(coordinatesX);
        Collections.sort(coordinatesY);

        List<Event> events = new ArrayList<>();

        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x1, coordinatesX),
                        binSearch(r.y1, coordinatesY),
                        binSearch(r.y2 + 1, coordinatesY),
                        1)
        ));

        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x2 + 1, coordinatesX),
                        binSearch(r.y1, coordinatesY),
                        binSearch(r.y2 + 1, coordinatesY),
                        -1)
        ));

        events.sort(new EventComparator());

        persistentSegmentTree = new PersistentSegmentTree(events, coordinatesY.size());
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

    public int search(Point point){
        if (coordinatesX.size() == 0) {
            return 0;
        }

        int xZip = binSearch(point.x, coordinatesX);
        int yZip = binSearch(point.y, coordinatesY);

        if (xZip == -1 || yZip == -1 || persistentSegmentTree.PersistentSegmentTreeNodes.size() <= xZip) {
            return 0;
        }

        return persistentSegmentTree.search(xZip, yZip);
    }
}
