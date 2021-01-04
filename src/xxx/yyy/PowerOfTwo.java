package xxx.yyy;

public class PowerOfTwo {
	private static boolean isPowerOfTwo(int val) {
		int x = val;
		while (x > 1) {
			int rem = x % 2;
			if (rem == 1) {
				return false;
			}
			x = x/2;
		}
		return true;
	}

	private static boolean isPowerOfTwoo(int val) {
		int x = val;
		while (x > 1) {
			if ((x & 0x01) == 0x01) {
				return false;
			}
			x = x >> 1;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(isPowerOfTwoo(256));
	}
}
