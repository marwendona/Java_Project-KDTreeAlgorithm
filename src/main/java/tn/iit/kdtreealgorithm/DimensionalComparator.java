package tn.iit.kdtreealgorithm;

import tn.iit.FloatingPoint;
import tn.iit.IMultiPoint;

import java.util.Comparator;

public record DimensionalComparator(int d) implements Comparator<IMultiPoint> {
	public int compare(IMultiPoint o1, IMultiPoint o2) {
		return FloatingPoint.compare(o1.getCoordinate(d), o2.getCoordinate(d));
	}
}
