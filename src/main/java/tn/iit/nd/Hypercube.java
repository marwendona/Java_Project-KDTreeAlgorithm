package tn.iit.nd;

import tn.iit.FloatingPoint;
import tn.iit.IHypercube;

public class Hypercube implements IHypercube {
	final int dimension;
	double[] lows;
	double[] highs;

	public Hypercube(int dimension) {
		this.dimension = dimension;

		this.lows = new double [dimension];
		this.highs = new double [dimension];
	}

	public Hypercube(IHypercube cube) {
		this.dimension = cube.dimensionality();

		this.lows = new double [dimension];
		this.highs = new double [dimension];

		for (int i = 1; i <= dimension; i++) {
			this.lows[i-1] = cube.getLeft(i);
			this.highs[i-1] = cube.getRight(i);
		}
	}

	public Hypercube(double[] lows, double[] highs) {
		dimension = lows.length;
		if (lows.length != highs.length) {
			throw new IllegalArgumentException ("lows and highs arrays do not contain the same number of dimensions.");
		}

		if (dimension < 2) {
			throw new IllegalArgumentException ("Hypercube can only be created with dimensions of 2 and higher.");
		}

		this.lows = new double[dimension];
		this.highs = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			this.lows[i] = lows[i];
			this.highs[i] = highs[i];
		}
	}

	public int dimensionality() {
		return dimension;
	}

	public double getLeft(int d) {
		return lows[d-1];
	}

	public void setLeft(int d, double value) {
		lows[d-1] = value;
	}

	public double getRight(int d) {
		return highs[d-1];
	}

	public void setRight(int d, double value) {
		highs[d-1] = value;
	}

	public int hashCode() {
		long bits = Double.doubleToLongBits(lows[0]);
		for (int i = 1; i < dimension; i++) {
			bits ^= Double.doubleToLongBits(lows[i-1]) * 31;
		}
		for (int i = 1; i < dimension; i++) {
			bits ^= Double.doubleToLongBits(highs[i-1]) * 31;
		}

		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	public boolean equals (Object o) {
		if (o == null) return false;

		if (o instanceof IHypercube ihc) {

			if (ihc.dimensionality() != dimension) { return false; }

			for (int i = 1; i <= dimension; i++) {

				double x = ihc.getLeft(i);
				if (!FloatingPoint.same(x,lows[i-1])) return false;

				x = ihc.getRight(i);
				if (!FloatingPoint.same(x,highs[i-1])) return false;
			}

			return true;
		}

		return false;
	}

	public String toString () {
		StringBuilder sb = new StringBuilder ("[");
		for (int d = 1; d <= dimension; d++) {
			sb.append(getLeft(d)).append(",").append(getRight(d));
			if (d != dimension) { sb.append (" : "); }
		}

		sb.append("]");
		return sb.toString();
	}
}
