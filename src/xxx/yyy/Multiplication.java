package xxx.yyy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Multiplication {
	private static List<Integer> reverseAndConvertToList(final String input) {
	    final char[] array = input.toCharArray();
		final List<Integer> list = new ArrayList<>();
		for (int index = array.length-1; index >= 0; index--) {
		    list.add(Character.digit(array[index], 10));
		}
		return list;
	}

    private static String reverseAndConvertToString(final List<Integer> result) {
        final StringBuilder sb = new StringBuilder();
        for (int index = result.size()-1; index >= 0; index--) {
            sb.append(Character.forDigit(result.get(index), 10));
        }
        return sb.toString();
    }

	public String multiply(final String input1, final String input2) {
	    if (input1.equalsIgnoreCase("0") || input2.equalsIgnoreCase("0"))
	        return "0";
		final List<Integer> listA = reverseAndConvertToList(input1);
		final List<Integer> listB = reverseAndConvertToList(input2);

		final List<Integer> result = multiply(listA, listB);
		return reverseAndConvertToString(result);
	}

	private static List<Integer> multiply(final List<Integer> listA, final List<Integer> listB) {
		List<Integer> result = null;

		for (int index = 0; index < listB.size(); index++) {
		    final int val = listB.get(index);
		    if (val == 0) continue;

			final List<Integer> product = new ArrayList<>();
			for (int k = 0; k < index; k++) {
				product.add(0);
			}

			int carryOver = 0;
			for (final int j : listA) {
				final int res = (j * val) + carryOver;
				product.add(res % 10);
				carryOver = res / 10;
			}
			if (carryOver > 0)
				product.add(carryOver);

			if (result == null)
			    result = product;
			else
			    result = sum(result, product);
		}
		return result;
	}

	private static List<Integer> sum(final List<Integer> listA, final List<Integer> listB) {
		final List<Integer> result = new ArrayList<>();

		final Iterator<Integer> iterA = listA.iterator();
		final Iterator<Integer> iterB = listB.iterator();

		int carryOver = 0;
		while (iterA.hasNext() || iterB.hasNext()) {
			final int valA = iterA.hasNext()? iterA.next() : 0;
			final int valB = iterB.hasNext()? iterB.next() : 0;

			final int res = valA + valB + carryOver;
			result.add(res % 10);
			carryOver = res / 10;
		}
		if (carryOver > 0)
			result.add(carryOver);

		return result;
	}

	public static void main(final String[] args) {
		final String s1 = "10004796832668910533699906532";
		final String s2 = "7800000009800125778";

	    //String s1 = "0";
	    //String s2 = "1111223"; //"300567";
		final String res = new Multiplication().multiply(s1, s2);
		System.out.println(res);

		final BigInteger b1 = new BigInteger(s1);
		final BigInteger b2 = new BigInteger(s2);

		final BigInteger b3 = b1.multiply(b2);
		System.out.println(b3.toString(10));
	}
}
