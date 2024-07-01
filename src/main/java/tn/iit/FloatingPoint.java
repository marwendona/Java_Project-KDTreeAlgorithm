package tn.iit;

public class FloatingPoint {
	public static final double epsilon = 1E-9;

	public static int compare (double d1, double d2) {
		if (FloatingPoint.lesser(d1, d2)) { return -1; }
		if (FloatingPoint.same(d1, d2)) { return 0; }

		return +1;
	}

	public static boolean same (double d1, double d2) {
		if (Double.isNaN(d1)) {
			return Double.isNaN(d2);
		}

		if (d1 == d2) return true;

		if (Double.isInfinite(d1)) {
			return false;
		}

		return value (d1-d2) == 0;
	}

	public static double value(double x) {
		if ((x >= 0) && (x <= epsilon)) {
			return 0.0;
		}

		if ((x < 0) && (-x <= epsilon)) {
			return 0.0;
		}

		return x;
	}

	public static boolean lesser(double x, double y) {
		return value(x-y) < 0;
	}
}
