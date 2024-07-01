package tn.iit;

public interface IMultiPoint {
	int dimensionality ();

	double getCoordinate (int dx);

	double distance (IMultiPoint imp);

	double[] raw();
}
