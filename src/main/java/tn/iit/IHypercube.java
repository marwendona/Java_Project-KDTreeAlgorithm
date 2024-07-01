package tn.iit;

public interface IHypercube {
	int dimensionality();

	double getLeft(int d);

	double getRight(int d);
}
