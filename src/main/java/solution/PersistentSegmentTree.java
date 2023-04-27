package solution;

import java.util.ArrayList;
import java.util.List;

public class PersistentSegmentTree {
    List<Node> PersistentSegmentTreeNodes = new ArrayList<>();

    PersistentSegmentTree(List<Event> events, int size) {
        int[] example = new int[size];
        Node root = builtEmptyPersistentTree(example, 0, size);

        int n = events.get(0).n;
        for(Event event : events) {
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
    
    public int find(int x, int y){
        return searchInPersistentSegmentTree(PersistentSegmentTreeNodes.get(x), y);
    }

    public int searchInPersistentSegmentTree(Node root, int num) {
        if (root == null) return 0;

        int middle = (root.leftRange + root.rightRange) / 2;
        if (num < middle) {
            return root.sum + searchInPersistentSegmentTree(root.left, num);
        }
        else return root.sum + searchInPersistentSegmentTree(root.right, num);
    }

}
