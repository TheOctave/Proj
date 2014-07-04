package Data;

import java.util.Random;

public class Synthetic {

	private final int seed = 1000;
	
	Random rand = new Random(seed);
	
	double[][] points;
	int[] labels;
	private int n;

	public Synthetic(int _n) {

		n = _n;
		points = new double[n][2];
		labels = new int[n];

		for (int i = 0; i < n; i++) {

			points[i][0] = rand.nextDouble() * 2.0 - 1;
			points[i][1] = rand.nextDouble() * 2.0 - 1;
			
			if (points[i][0] >= 0 && points[i][1] >= 0)
				labels[i] = 1;
			else if (points[i][0] >= 0)
				labels[i] = 2;
			else if (points[i][1] >= 0)
				labels[i] = 3;
			else
				labels[i] = 4;
		}
	}
	
	public int[] getLabels() {
		return labels;
	}

	public double[][] getPoints() {
		return points;
	}
}
