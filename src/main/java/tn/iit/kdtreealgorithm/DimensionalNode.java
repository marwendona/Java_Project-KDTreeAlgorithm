package tn.iit.kdtreealgorithm;

import tn.iit.IMultiPoint;
import tn.iit.nd.Hypercube;

public class DimensionalNode {
	public static int numDoubleRecursions = 0;
	public static int numRecursions = 0;
	public final IMultiPoint point;
	private final double[] cached;
	public final int dimension;
	public final int max;
	public final double coord;
	protected Hypercube region;
	protected DimensionalNode below;
	protected DimensionalNode above;

	public DimensionalNode(int dimension, IMultiPoint pt) {
		this.dimension = dimension;
		this.point = pt;
		this.max = pt.dimensionality();
		this.region = new Hypercube(max);

		cached = new double[max];
		for (int i = 1; i <= max; i++) {
			cached[i-1] = pt.getCoordinate(i);
			region.setLeft(i, Double.NEGATIVE_INFINITY);
			region.setRight(i, Double.POSITIVE_INFINITY);
		}

		this.coord = cached[dimension-1];
	}

	public DimensionalNode getBelow() {
		return below;
	}

	public DimensionalNode getAbove() {
		return above;
	}

	public void setBelow(DimensionalNode node) {
		if (node == null) {
			this.below = null;
			return;
		}

		if ((this.dimension == max && node.dimension == 1) ||
			(this.dimension +1 == node.dimension)) {
			this.below = node;

			node.region = new Hypercube (region);
			node.region.setRight(dimension, coord);
			return;
		}

		throw new IllegalArgumentException ("Can only set as children nodes whose dimensionality is one greater.");
	}

	public void setAbove(DimensionalNode node) {
		if (node == null) {
			this.above = null;
			return;
		}

		if ((this.dimension == max && node.dimension == 1) ||
			(this.dimension +1 == node.dimension)) {
			this.above = node;

			node.region = new Hypercube (region);
			node.region.setLeft(dimension, coord);
			return;
		}

		throw new IllegalArgumentException ("Can only set as children nodes whose dimensionality is one greater.");
	}

	public boolean isBelow (IMultiPoint pt) {
		return pt.getCoordinate(dimension) < coord;
	}

	protected double shorter (double[] rawTarget, double min) {
		double minsq = min*min;
		double maxV = minsq;

		for (int i = 0; i < this.max; i++) {
			double d = rawTarget[i]-cached[i];
			if ((maxV -= d*d) < 0) {
				return -1;  // leave NOW! can't be shorter.
			}
		}
		return Math.sqrt(minsq-maxV);
	}

	protected IMultiPoint nearest (double[] rawTarget, double[] min) {
		IMultiPoint result = null;

		double d = shorter(rawTarget, min[0]);
		if (d >= 0 && d < min[0]) {
			min[0] = d;
			result = point;
		}

		double dp = Math.abs(coord - rawTarget[dimension-1]);
		IMultiPoint newResult = null;

		int numDblRec = 0;
		if (dp < min[0]) {
			if (above != null) {
				numDblRec++;
				newResult = above.nearest (rawTarget, min);
				if (newResult != null) { result = newResult; }
			}

			if (below != null) {
				numDblRec++;
				newResult = below.nearest(rawTarget, min);
				if (newResult != null) { result = newResult; }
			}
			if (numDblRec == 2) {
				numDoubleRecursions++;
			} else if (numDblRec == 1) {
				numRecursions++;
			}
		} else {
			numRecursions++;
			if (rawTarget[dimension-1] < coord) {
				if (below != null) {
					newResult = below.nearest (rawTarget, min);
				}
			} else {
				if (above != null) {
					newResult = above.nearest (rawTarget, min);
				}
			}

			if (newResult != null) { return newResult; }
		}
		return result;
	}

	public String toString () {
		return dimension + ":<" + point + ">";
	}

	void propagate(Hypercube region) {
		this.region = region;

		if (below != null) {
			Hypercube child = new Hypercube (region);
			child.setRight(dimension, coord);
			below.propagate(child);
		}

		if (above != null) {
			Hypercube child = new Hypercube (region);
			child.setLeft(dimension, coord);
			above.propagate(child);
		}
	}
}
