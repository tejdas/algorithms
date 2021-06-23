package net.lc.design;

/**
 * 1352
 */
public class ProductOfNumbers {
    private int lastZeroIndex = -1;

    private int[] input = new int[40001];
    private int[] mult = new int[40001];
    private int lastIndex = -1;

    public ProductOfNumbers() {

    }

    public void add(int num) {
        lastIndex++;
        input[lastIndex] = num;
        if (num == 0) {
            lastZeroIndex = lastIndex;
            mult[lastIndex] = 0;
        } else if (lastZeroIndex == lastIndex-1) {
            mult[lastIndex] = num;
        } else {
            mult[lastIndex] = mult[lastIndex-1] * num;
        }
    }

    public int getProduct(int k) {

        if (lastIndex-k < lastZeroIndex) return 0;
        else if (lastIndex-k == lastZeroIndex) {
            int cur = mult[lastIndex];
            int prev = mult[lastIndex-k+1];
            return (cur/prev) * input[lastIndex-k+1];
        }

        int cur = mult[lastIndex];
        int prev = mult[lastIndex-k];
        return cur/prev;
    }

    public static void main(String[] args) {
        ProductOfNumbers productOfNumbers = new ProductOfNumbers();
        productOfNumbers.add(3);        // [3]
        productOfNumbers.add(0);        // [3,0]
        productOfNumbers.add(2);        // [3,0,2]
        productOfNumbers.add(5);        // [3,0,2,5]
        productOfNumbers.add(4);        // [3,0,2,5,4]
        System.out.println(productOfNumbers.getProduct(2)); // return 20. The product of the last 2 numbers is 5 * 4 = 20
        System.out.println(productOfNumbers.getProduct(3)); // return 40. The product of the last 3 numbers is 2 * 5 * 4 = 40
        System.out.println(productOfNumbers.getProduct(4)); // return 0. The product of the last 4 numbers is 0 * 2 * 5 * 4 = 0
        productOfNumbers.add(8);        // [3,0,2,5,4,8]
        System.out.println(productOfNumbers.getProduct(2)); // return 32. The product of the last 2 numbers is 4 * 8 = 32
    }
}
