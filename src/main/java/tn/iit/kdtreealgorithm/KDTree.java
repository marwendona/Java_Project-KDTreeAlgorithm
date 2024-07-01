package tn.iit.kdtreealgorithm;

import tn.iit.IMultiPoint;
import tn.iit.nd.Hypercube;

public class KDTree {
	private DimensionalNode root;
	public final int maxDimension;

	public KDTree(int md) {
		if (md < 1) {
			throw new IllegalArgumentException ("KD tree must have at least one dimension.");
		}

		root = null;
		maxDimension = md;
	}

	public DimensionalNode parent (IMultiPoint value) {
		if (value == null) {
			throw new IllegalArgumentException ("unable to insert null value into KDTree");
		}

		if (root == null) { return null; }

		DimensionalNode node = root;
		DimensionalNode next;
		while (true) {
			if (node.isBelow(value)) {
				next = node.getBelow();
            } else {
				next = node.getAbove();
            }
            if (next == null) {
                break;
            } else {
                node = next;
            }
        }

		return node;
	}

	public void setRoot(DimensionalNode newRoot) {
		root = newRoot;
		if (root == null) {
			return;
		}

		double [] lows = new double[maxDimension];
		double [] highs = new double[maxDimension];
		for (int i = 0; i < maxDimension; i++) {
			lows[i] = Double.NEGATIVE_INFINITY;
			highs[i] = Double.POSITIVE_INFINITY;
		}

		Hypercube region = new Hypercube(lows, highs);
		root.propagate(region);
	}

	public IMultiPoint nearest (IMultiPoint target) {
		if (root == null || target == null) return null;

		DimensionalNode parent = parent(target);
		IMultiPoint result = parent.point;
		double smallest = target.distance(result);

		double[] best = new double[] { smallest };

		double[] raw = target.raw();
		IMultiPoint betterOne = root.nearest (raw, best);
		if (betterOne != null) { return betterOne; }
		return result;
	}

	private void buildString (StringBuilder sb, DimensionalNode node) {
		if (node == null) { return; }

		DimensionalNode left = node.getBelow();
		DimensionalNode right = node.getAbove();

		if (left != null) { buildString(sb, left); }
		sb.append (node);
		if (right != null) { buildString (sb, right); }
	}

	public String toString () {
		if (root == null) { return ""; }

		StringBuilder sb = new StringBuilder();
		buildString (sb, root);
		return sb.toString();
	}
}
