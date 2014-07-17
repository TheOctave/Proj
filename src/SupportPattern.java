
public class SupportPattern {

	double[] x;
	double[] gradient;
	double[] beta;
	int y;
	
	public SupportPattern (double[] _x, double[] _gradient, double[] _beta, double _y) {
		
		x = _x;
		gradient = _gradient;
		beta = _beta;
		y = _y;
	}
}
