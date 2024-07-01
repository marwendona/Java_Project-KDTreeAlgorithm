package tn.iit.kdtreealgorithm;

import tn.iit.IMultiPoint;
import tn.iit.IPoint;
import tn.iit.array.Selection;
import tn.iit.twod.TwoDPoint;

import java.util.Comparator;

public class KDFactory {
	private static Comparator[] comparators = null;

	public static synchronized KDTree generate (IMultiPoint[]points) {
		if (points.length == 0) { return null; }

		int maxD = points[0].dimensionality();
		KDTree tree = new KDTree(maxD);

		comparators = new Comparator[maxD+1];
		for (int i = 1; i <= maxD; i++) {
			comparators[i] = new DimensionalComparator(i);
		}

		tree.setRoot(generate (1, maxD, points, 0, points.length-1));
		return tree;
	}

	public static synchronized KDTree generate (IPoint[]points) {
		if (points.length == 0) { return null; }

		IMultiPoint[] others = new IMultiPoint[points.length];
		for (int i = 0; i < points.length; i++) {
			if (points[i] instanceof IMultiPoint) {
				others[i] = (IMultiPoint) points[i];
			} else {
				others[i] = new TwoDPoint(points[i].x(), points[i].y());
			}
		}

		return generate (others);
	}


	private static DimensionalNode generate (int d, int maxD, IMultiPoint[] points, int left, int right) {

		if (right < left) { return null; }
		if (right == left) { return new DimensionalNode (d, points[left]); }

		int m = 1+(right-left)/2;
		Selection.select(points, m, left, right, comparators[d]);

		DimensionalNode dm = new DimensionalNode (d, points[left+m-1]);

		if (++d > maxD) { d = 1; }

		dm.setBelow(generate (d, maxD, points, left, left+m-2));
		dm.setAbove(generate (d, maxD, points, left+m, right));
		return dm;
	}
}
