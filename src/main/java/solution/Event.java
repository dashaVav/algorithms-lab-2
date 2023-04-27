package solution;

public class Event {
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