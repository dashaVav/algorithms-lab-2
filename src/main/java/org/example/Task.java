package org.example;

import java.util.*;

public class Task {
    public static class Rectangle{
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

    List<Integer> coordinatesX;
    List<Integer> coordinatesY;
    private final Map<Integer, Integer> coordsX = new HashMap<>();
    private final Map<Integer, Integer> coordsY = new HashMap<>();
    List<Node> persistentNode = new ArrayList<>();

    public Task (List<Rectangle> rectangles) {
        if (rectangles.size() == 0) return;
        Set<Integer> coordsX = new HashSet<>();
        Set<Integer> coordsY = new HashSet<>();

        for (Rectangle r : rectangles) {
            coordsX.add(r.x1);
            coordsY.add(r.y1);
            coordsX.add(r.x2 + 1); coordsY.add(r.y2 + 1);
        }
        coordinatesX = new ArrayList<>(coordsX);
        coordinatesY = new ArrayList<>(coordsY);

        Collections.sort(coordinatesX);
        Collections.sort(coordinatesY);

        for (int i = 0; i < coordinatesX.size(); i++) {
            this.coordsX.put(coordinatesX.get(i), i);
        }

        for (int i = 0; i < coordinatesY.size(); i++) {
            this.coordsY.put(coordinatesY.get(i), i);
        }
        build(rectangles);
    }

    public void build(List<Rectangle> rectangles) {
        List<Event> events = new ArrayList<>();
        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x1, coordinatesX, coordsX),
                        binSearch(r.y1, coordinatesY, coordsY),
                        binSearch(r.y2 + 1, coordinatesY, coordsY),
                        1)
        ));

        rectangles.forEach(r -> events.add(
                new Event(binSearch(r.x2 + 1, coordinatesX, coordsX),
                        binSearch(r.y1, coordinatesY, coordsY),
                        binSearch(r.y2 + 1, coordinatesY, coordsY),
                        -1)
        ));

        events.sort(new EventComparator());

        int[] example = new int[coordsY.size()];

        Node empty = this.empty(example, 0, coordsY.size());

        int n = events.get(0).n;
        for(Event event : events) {
            if (n != event.n) {
                persistentNode.add(empty);
                n = event.n;
            }
            empty = addNode(empty, event.left, event.right, event.status);
        }

    }

    public int find(Node root, int num) {
        if (root == null) return 0;

        int middle = (root.leftRange + root.rightRange) / 2;
        if (num < middle) {
            return root.sum + find(root.left, num);
        }
        else return root.sum + find(root.right, num);
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

    public Node empty(int[] example, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex == 1) {
            return new Node(null, null, leftIndex, rightIndex, example[leftIndex]);
        }
        int middle = (leftIndex + rightIndex) / 2;
        Node left = empty(example, leftIndex, middle);
        Node right = empty(example, middle, rightIndex);
        return new Node(left, right, left.leftRange, right.rightRange, left.sum + right.sum);
    }

    public Node addNode(Node root, int leftIndex, int rightIndex, int val) {
        if (leftIndex <= root.leftRange && rightIndex >= root.rightRange) {
            return new Node(root.left, root.right, root.leftRange, root.rightRange, root.sum + val);
        }

        if (root.leftRange >= rightIndex || root.rightRange <= leftIndex) {
            return root;
        }

        Node newRoot = new Node(root.left, root.right, root.leftRange, root.rightRange, root.sum);

        newRoot.left = addNode(newRoot.left, leftIndex, rightIndex, val);
        newRoot.right = addNode(newRoot.right, leftIndex, rightIndex, val);
        return newRoot;
    }

    public int binSearch(Integer num, List<Integer> coordinates, Map<Integer, Integer> coords){

        //todo вернуть -1
        int left = 0;
        int right = coordinates.size() ;
        int middle;
        if (coordinates.get(0) > num || coordinates.get(coordinates.size() - 1) < num) return -1;
        while (left < right) {
            middle = left + (right - left) / 2;
            if (coordinates.get(middle) <= num) {
                left = middle + 1;
            }
            else if (coordinates.get(middle) > num) {
                right = middle;
            }
        }
        return left - 1;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++){
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        Task task = new Task(rectangles);

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            if (rectangles.size() == 0) {
                System.out.println(0);
                continue;
            }
            int x = task.binSearch(scanner.nextInt(), task.coordinatesX,  task.coordsX);
            int y = task.binSearch(scanner.nextInt(), task.coordinatesY,  task.coordsY);
//            System.out.println(x + " " + y);
            if (x == -1 || y == -1 || task.persistentNode.size() <= x ||  task.persistentNode.size() <= y) {
                System.out.println(0);
                continue;
            }
            System.out.println(task.find(task.persistentNode.get(x), y));
        }
    }
}
