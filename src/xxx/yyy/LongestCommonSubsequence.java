package xxx.yyy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LongestCommonSubsequence {

	static class Pair {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + idx1;
			result = prime * result + idx2;
			return result;
		}
		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final Pair other = (Pair) obj;
			if (idx1 != other.idx1)
				return false;
			if (idx2 != other.idx2)
				return false;
			return true;
		}
		public Pair(final int idx1, final int idx2) {
			super();
			this.idx1 = idx1;
			this.idx2 = idx2;
		}
		int idx1;
		int idx2;
	}

	static Map<Pair, List<Character>> memoizedMap = new HashMap<>();

	static void longestSubsequence(final String s1, final String s2) {
		final char[] array1 = s1.toCharArray();
		final char[] array2 = s2.toCharArray();
		final int val = findLongestCommonSubsequence(array1, array1.length-1, array2, array2.length-1);
		System.out.println(val);
		final List<Character> list = memoizedMap.get(new Pair(array1.length-1, array2.length-1));
		for (final char c : list) System.out.print(c + " ");
		System.out.println();
	}

	static int findLongestCommonSubsequence(final char[] array1, final int idx1, final char[] array2, final int idx2) {
		if (idx1 < 0 || idx2 < 0)
			return 0;

		final Pair p = new Pair(idx1, idx2);
		if (memoizedMap.containsKey(p)) {
			return memoizedMap.get(p).size();
		} else {
			if (array1[idx1] != array2[idx2]) {
				final int size1 = findLongestCommonSubsequence(array1, idx1-1, array2, idx2);
				final int size2 = findLongestCommonSubsequence(array1, idx1, array2, idx2-1);
				List<Character> list = null;
				if (size1 > size2) {
					list = memoizedMap.get(new Pair(idx1-1, idx2));
				} else {
					list = memoizedMap.get(new Pair(idx1, idx2-1));
				}

				final List<Character> curList = new ArrayList<>();
				if (list != null)
					curList.addAll(list);
				memoizedMap.put(p,  curList);
				return Math.max(size1, size2);
			} else {
				final int size = findLongestCommonSubsequence(array1, idx1-1, array2, idx2-1);
				final List<Character> list = memoizedMap.get(new Pair(idx1-1, idx2-1));

				final List<Character> curList = new ArrayList<>();
				if (list != null)
					curList.addAll(list);
				curList.add(array1[idx1]);
				memoizedMap.put(p,  curList);
				return size+1;
			}
		}
	}

    public static void main(final String[] args) {
		longestSubsequence("abcdefghnkcd", "bamenqrgsthlmc");
		System.out.println(memoizedMap.size());
	}
}
