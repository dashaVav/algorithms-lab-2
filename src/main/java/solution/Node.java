package solution;

public class Node {
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


