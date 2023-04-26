package org.example;

import java.util.*;
import java.util.List;

public class Task_3 {
    List<Integer> coordinatesX;
    List<Integer> coordinatesY;
    private final Map<Integer, Integer> coordsX = new HashMap<>();
    private final Map<Integer, Integer> coordsY = new HashMap<>();
    List<Node> persistentNode = new ArrayList<>();

    private static class Rectangle{
        int x1, y1, x2, y2;

        public Rectangle(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }

    public static class Event {
        int n;
        int left, right;
        int status;

        Event(int n, int left, int right, int status) {
            this.n = n;
            this.left = left;
            this.right = right;
            this.status = status;
        }
    }

    public static class EventComparator implements Comparator<Event> {
        public int compare(Event e1, Event e2) {
            return e1.n - e2.n;
        }
    }


    public int binSearch(Integer num, List<Integer> coordinates, Map<Integer, Integer> coords){
        int index = Integer.MAX_VALUE;
        int left = 0;
        int right = coordinates.size() ;
        int middle;
        if (coordinates.get(0) > num || coordinates.get(coordinates.size() - 1) < num) return 0;
        while (left <= right) {
            middle = left + (right - left) / 2;
            if (middle == coordinates.size()) return 0;
            if (coordinates.get(middle) < num) {
                left = middle + 1;
            }
            else if (coordinates.get(middle) > num) {
                right = middle - 1;
            }
            else if (Objects.equals(coordinates.get(middle), num)) {
                index = coords.get(coordinates.get(middle));
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {

            index = coords.get(coordinates.get(left));
            index -= index > 1 ? 1 : 0;

        }
        return index;
    }

    public Task_3 (List<Rectangle> rectangles) {
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
        build(rectangles);
    }

    private static class Node {
        Node left, right;
        int leftRange, rightRange, sum;
        Node(Node left, Node right, int leftRange, int rightRange, int sum) {
            this.left = left;
            this.right = right;
            this.rightRange = rightRange;
            this.leftRange = leftRange;
            this.sum = sum;
        }
    }


    public void build(List<Rectangle> rectangles) {
        List<Event> events = new ArrayList<>();
        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x1, coordinatesX, coordsX),
                        binSearch(r.y1, coordinatesY, coordsY),
                        binSearch(r.y2 + 1, coordinatesY, coordsY),
                        -1)
        ));

        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x2 + 1, coordinatesX, coordsX),
                        binSearch(r.y1, coordinatesY, coordsY),
                        binSearch(r.y2 + 1, coordinatesY, coordsY),
                        1)
        ));
        events.sort(new EventComparator());

        Node empty = this.insert(0, coordsY.size(), 1);
        int n = events.get(0).n;
        for(Event event : events) {
            if (n != event.n) {
                persistentNode.add(empty);
            }
            empty = this.insert(empty, event.left, event.right, event.status);
        }

    }

    public Node insert(Node node, int leftRange, int rightRange, int value) {
        if (node == null) {
            return new Node(null, null, leftRange, rightRange, value);
        }

        if (rightRange <= node.leftRange || leftRange >= node.rightRange) {
            return node;
        }

        if (leftRange <= node.leftRange && rightRange >= node.rightRange) {
            return new Node(null, null, node.leftRange, node.rightRange, node.sum + value);
        }

        int mid = (node.leftRange + node.rightRange) / 2;

        Node left = insert(node.left, leftRange, rightRange, value);
        Node right = insert(node.right, leftRange, rightRange, value);

        return new Node(left, right, node.leftRange, node.rightRange, left.sum + right.sum);
    }

    public Node insert(int leftRange, int rightRange, int value) {
        return insert(null, leftRange, rightRange, value);
    }

    public int query(Node node, int leftRange, int rightRange) {
        if (node == null || rightRange <= node.leftRange || leftRange >= node.rightRange) {
            return 0;
        }

        if (leftRange <= node.leftRange && rightRange >= node.rightRange) {
            return node.sum;
        }

        int mid = (node.leftRange + node.rightRange) / 2;

        int leftSum = query(node.left, leftRange, rightRange);
        int rightSum = query(node.right, leftRange, rightRange);

        return leftSum + rightSum;
    }




    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++){
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        Task_3 task = new Task_3(rectangles);

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            int x = task.binSearch(scanner.nextInt(), task.coordinatesX,  task.coordsX);
            int y = task.binSearch(scanner.nextInt(), task.coordinatesY,  task.coordsY);
            System.out.print(task.query(task.persistentNode.get(y), x, x) + " ");
        }
    }
}
