package tn.iit.twod;

import tn.iit.FloatingPoint;
import tn.iit.IMultiPoint;
import tn.iit.IPoint;

public record TwoDPoint(double x, double y) implements IPoint, IMultiPoint {


	public boolean equals(Object o) {
		if (o == null) return false;

		if (o instanceof IPoint ip) {

			return ((FloatingPoint.value(x - ip.x()) == 0) &&
					(FloatingPoint.value(y - ip.y()) == 0));
		}

		return false;
	}

	public String toString() {
		return x + "," + y;
	}

	public int dimensionality() {
		return 2;
	}

	public double getCoordinate(int d) {
		if (d == 1) {
			return x;
		}

		return y;
	}

	public double distance(IMultiPoint imp) {
		if (imp.dimensionality() != 2) {
			throw new IllegalArgumentException("distance computation can only be performed between two-dimensional points");
		}

		double ox = imp.getCoordinate(1);
		double oy = imp.getCoordinate(2);

		return Math.sqrt((ox - x) * (ox - x) + (oy - y) * (oy - y));
	}

	@Override
	public int hashCode() {
		long bits = Double.doubleToLongBits(x());
		bits ^= Double.doubleToLongBits(y()) * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	public double[] raw() {
		return new double[]{x, y};
	}

	@Override
	public int compareTo(IPoint p) {
		double fp = FloatingPoint.value(x() - p.x());
		if (fp < 0) {
			return -1;
		}
		if (fp > 0) {
			return +1;
		}

		fp = FloatingPoint.value(y() - p.y());
		if (fp < 0) {
			return -1;
		}
		if (fp > 0) {
			return +1;
		}

		return 0;
	}
}
