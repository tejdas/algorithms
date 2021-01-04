package xxx.yyy;

import java.util.Comparator;

public class FooBar {
    static class Apple {
        int weight;

        public int getWeight() {
            return weight;
        }
    }
    Comparator<Apple> byWeight = (a1, a2) -> Integer.compare(a1.weight, a2.weight);

    Runnable a = () -> {

    };

    public void performSQRT(int number) {
        if (number < 0) {
            throw new CustomException("not a valid number: " + number);
        }


    }

    public void performSQRT(String number) throws CustomException {
        try {
            int val = Integer.valueOf(number);
        } catch (NumberFormatException ex) {
            throw new CustomException(ex, number);
        }

        int val2 = Integer.valueOf(number);
    }
}
