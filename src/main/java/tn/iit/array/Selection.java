package tn.iit.array;

import java.util.Comparator;

public class Selection {
	public static void swap (Object[]ar, int pos1, int pos2) {
		if (pos1 == pos2) { return; }

		Object tmp = ar[pos1];
		ar[pos1] = ar[pos2];
		ar[pos2] = tmp;
	}

	public static int partition (Object[] ar, int left, int right, int pivotIndex, Comparator comparator) {
		Object pivot = ar[pivotIndex];
		swap (ar, right, pivotIndex);

		int store = left;
		for (int idx = left; idx < right; idx++) {
			if (comparator.compare(ar[idx], pivot) <= 0) {
				swap (ar, idx, store);
				store++;
			}
		}

		swap (ar, right, store);
		return store;
	}

	public static int selectPivotIndex (Object[] ar, int left, int right, Comparator comparator) {
		int midIndex = (left+right)/2;

		int lowIndex = left;

		if (comparator.compare(ar[lowIndex], ar[midIndex]) >= 0) {
			lowIndex = midIndex;
			midIndex = left;
		}

		if (comparator.compare(ar[right], ar[lowIndex]) <= 0) {
			return lowIndex;
		} else if (comparator.compare(ar[right], ar[midIndex]) <= 0) {
			return right;
		}

		return midIndex;
	}

	public static void select (Object[]ar, int k, int left, int right, Comparator comparator) {
		do {
			int idx = selectPivotIndex (ar, left, right, comparator);
			int pivotIndex = partition (ar, left, right, idx, comparator);

			if (left+k-1 == pivotIndex) {
				return;
			}

			if (left+k-1 < pivotIndex) {
				right = pivotIndex - 1;
			} else {
				k -= (pivotIndex-left+1);
				left = pivotIndex + 1;
			}
		} while (true);
	}
}
