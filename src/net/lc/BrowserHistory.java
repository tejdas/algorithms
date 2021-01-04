package net.lc;

/**
 * 1472
 * Design
 * Stack
 */
public class BrowserHistory {
    static class Node {
        String val;
        Node up;
        Node down;

        public Node(String val) {
            this.val = val;
        }
    }

    private Node top;
    private Node bottom;
    private Node cur;
    public BrowserHistory(String homepage) {
        Node n = new Node(homepage);
        top = n;
        bottom = n;
        cur = n;
    }

    public void visit(String url) {
        Node n = new Node(url);
        n.down = cur;
        cur.up = n;
        cur = n;
        top = n;
    }

    public String back(int steps) {
        if (cur.down == null) {
            return cur.val;
        }
        Node newcur = cur;
        while (steps > 0 && newcur.down != null) {
            newcur = newcur.down;
            steps--;
        }
        cur = newcur;
        return cur.val;
    }

    public String forward(int steps) {
        if (cur.up == null) {
            return cur.val;
        }
        Node newcur = cur;
        while (steps > 0 && newcur.up != null) {
            newcur = newcur.up;
            steps--;
        }
        cur = newcur;
        return cur.val;
    }

    public static void main(String[] args) {
        BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
        browserHistory.visit("google.com");       // You are in "leetcode.com". Visit "google.com"
        browserHistory.visit("facebook.com");     // You are in "google.com". Visit "facebook.com"
        browserHistory.visit("youtube.com");      // You are in "facebook.com". Visit "youtube.com"
        System.out.println(browserHistory.back(1));                   // You are in "youtube.com", move back to "facebook.com" return "facebook.com"
        System.out.println(browserHistory.back(1));                   // You are in "facebook.com", move back to "google.com" return "google.com"
        System.out.println(browserHistory.forward(1));                // You are in "google.com", move forward to "facebook.com" return "facebook.com"
        browserHistory.visit("linkedin.com");     // You are in "facebook.com". Visit "linkedin.com"
        System.out.println(browserHistory.forward(2));              // You are in "linkedin.com", you cannot move forward any steps.
        System.out.println(browserHistory.back(2));                   // You are in "linkedin.com", move back two steps to "facebook.com" then to "google.com". return "google.com"
        System.out.println(browserHistory.back(7));                   // You are in "google.com", you can move back only one step to "leetcode.com". return "leetcode.com"
    }
}
