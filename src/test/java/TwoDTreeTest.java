import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tn.iit.IMultiPoint;
import tn.iit.IPoint;
import tn.iit.kdtreealgorithm.KDFactory;
import tn.iit.kdtreealgorithm.KDTree;
import tn.iit.twod.TwoDPoint;

class TwoDTreeTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void insert() {
        IPoint[] points = new IPoint[] {
                new TwoDPoint(0,0),
                new TwoDPoint(1,1)
        };

        KDTree tree = KDFactory.generate(points);

        IMultiPoint nearest = tree.nearest(new TwoDPoint(-1, -1));

        System.out.println(nearest);
    }
}
