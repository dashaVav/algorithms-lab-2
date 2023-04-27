package contest;

import java.util.*;

public class Task3 {
    public static class EventComparator implements Comparator<Event> {
        public int compare(Event e1, Event e2) {
            return e1.n - e2.n;
        }
    }

    PersistentSegmentTree persistentSegmentTree;
    List<Integer> coordinatesX;
    List<Integer> coordinatesY;

    public Task3 (List<Rectangle> rectangles) {

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

    public int find(int x, int y){
        if (coordinatesX.size() == 0) {
            return 0;
        }

        int xZip = binSearch(x, coordinatesX);
        int yZip = binSearch(y, coordinatesY);


        if (xZip == -1 || yZip == -1 || persistentSegmentTree.PersistentSegmentTreeNodes.size() <= xZip) {
            return 0;
        }

        return persistentSegmentTree.find(xZip, yZip);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<Rectangle> rectangles= new ArrayList<>();
        for (int i = 0; i < n; i++){
            rectangles.add(new Rectangle(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
        }

        Task3 task = new Task3(rectangles);

        int m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            System.out.println(task.find(scanner.nextInt(), scanner.nextInt()));
        }
    }

}


class Event {
    int n;
    int left, right;
    int isBegOrEnd;

    Event(int n, int left, int right, int isBegOrEnd) {
        this.n = n;
        this.left = left;
        this.right = right;
        this.isBegOrEnd = isBegOrEnd;
    }
}


class Node {
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


class PersistentSegmentTree {
    List<Node> PersistentSegmentTreeNodes = new ArrayList<>();

    PersistentSegmentTree(List<Event> events, int size) {
        int[] example = new int[size];
        Node root = builtEmptyPersistentTree(example, 0, size);

        int n = events.get(0).n;
        for (Event event : events) {
            if (n != event.n) {
                PersistentSegmentTreeNodes.add(root);
                n = event.n;
            }
            root = addNodeToPersistentTree(root, event.left, event.right, event.isBegOrEnd);
        }
    }

    public Node builtEmptyPersistentTree(int[] emptyMas, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex == 1) {
            return new Node(null, null, leftIndex, rightIndex, emptyMas[leftIndex]);
        }

        int middle = (leftIndex + rightIndex) / 2;

        Node left = builtEmptyPersistentTree(emptyMas, leftIndex, middle);
        Node right = builtEmptyPersistentTree(emptyMas, middle, rightIndex);

        return new Node(left, right, left.leftRange, right.rightRange, left.sum + right.sum);
    }

    public Node addNodeToPersistentTree(Node root, int leftIndex, int rightIndex, int val) {
        if (leftIndex <= root.leftRange && rightIndex >= root.rightRange) {
            return new Node(root.left, root.right, root.leftRange, root.rightRange, root.sum + val);
        }

        if (root.leftRange >= rightIndex || root.rightRange <= leftIndex) {
            return root;
        }

        Node newRoot = new Node(root.left, root.right, root.leftRange, root.rightRange, root.sum);

        newRoot.left = addNodeToPersistentTree(newRoot.left, leftIndex, rightIndex, val);
        newRoot.right = addNodeToPersistentTree(newRoot.right, leftIndex, rightIndex, val);

        return newRoot;
    }

    public int find(int x, int y) {
        return searchInPersistentSegmentTree(PersistentSegmentTreeNodes.get(x), y);
    }

    public int searchInPersistentSegmentTree(Node root, int num) {
        if (root == null) return 0;

        int middle = (root.leftRange + root.rightRange) / 2;
        if (num < middle) {
            return root.sum + searchInPersistentSegmentTree(root.left, num);
        } else return root.sum + searchInPersistentSegmentTree(root.right, num);
    }
}

class Rectangle {
    int x1, y1, x2, y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

}



