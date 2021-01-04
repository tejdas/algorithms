package algo.array;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {

    private final Map<Integer, Integer> output = new HashMap<>();

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        final Fibonacci f = new Fibonacci();
        f.initialize();

        final int val = f.fib(10);
        System.out.println("output: " + val);

    }

    public void initialize() {
        output.put(1, 1);
        output.put(2, 1);
    }
    public int fib(int n) {
        System.out.println("fib for: " + n);
        if (output.containsKey(n)) {
            return output.get(n);
        } else {
            final int val = fib(n - 1) + fib(n - 2);
            output.put(n, val);
            return val;
        }
    }
}
