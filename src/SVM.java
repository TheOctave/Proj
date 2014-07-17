import java.util.ArrayList;

public class SVM {

	public ArrayList<SupportPattern> s = new ArrayList<SupportPattern>();
	
	public double gamma;
	public double[][] x;
	
	public static final double C = 100;
//	public int[][][] y;
	
	public int summation (int start, int finish) {
		
		
	}
	
	public int costFunction () {
		
		
	}
	
	public int gradientDescent () {
		
		
	}
	
	public double gradient (int index, int y) {
		
		int i, j; //counter variables
		double numA, sum;
		double[] tempResultA;
		double[] tempResultB;
		double[] tempX;
		double x1, x2, y1, y2;
		SupportPattern obj = s.get(index);
		
			
		numA = -1 * lossFunction(y, obj.y);
		
		sum = 0;
		for (j = 0; j < s.size(); j++) {
			
			tempResultA = featureTransform(obj.x, y);
			tempResultB = featureTransform(s.get(j).x, s.get(j).y); //recheck this
			sum += obj.gradient[j] * dist(tempResultA, tempResultB);
		}
			
		return numA - sum;
	}
	
	public double innerProduct (double[] vecA, double[] vecB) {
		
		double result = 0;
		
		//assumes arrA and arrB are the same size
		for (int i = 0; i < vecA.length; i++) {
				
				result += vecA[i] * vecB[i];
		}
		
		return result;
	}
	
	public int dist (double[] vecA, double[] vecB) {
		
		int result = 0;
		double square;
		
		for (int i = 0; i < vecA.length; i++) {
			
			result += (square = (vecA[i] - vecB[i])) * square;
		}
		
		return result;
	}
	
	public int lossFunction (int y1, int y2) {
		
		//use epsilon comparison later (if labels are double)
		return (y1 == y2) ? 0 : 1;
	}
	
	public static int delta (int y1, int y2) {
		
		return (y1 == y2) ? 0 : 1;
	}
	
	public double[] featureTransform (double[] x, int label) {
		
		return x;
	}
	
	public void SMOStep (int index, int yPos, int yNeg) {
		
		SupportPattern obj = s.get(index);
		
		double kernelZeroZero;
		double kernelOneOne;
		double kernelZeroOne;
		
		double lambdaNumerator;
		double lambdaDenominator;
		
		double lambdaU;
		double lambda;
		
		double[] tempResult;
		double[] tempResultA = featureTransform(obj.x, yPos);
		double[] tempResultB = featureTransform(obj.x, yNeg);
		
		kernelZeroZero = Math.pow(Math.E, -1 * (dist(tempResultA, tempResultA)));
		kernelOneOne = Math.pow(Math.E, -1 * (dist(tempResultB, tempResultB)));
		kernelZeroOne = Math.pow(Math.E, -1 * (dist(tempResultA, tempResultB)));
		
		lambdaNumerator = gradient(index, yPos) - gradient(index, yNeg);
		lambdaDenominator = kernelZeroZero + kernelOneOne - 2 * kernelZeroOne;
		
		lambdaU = lambdaNumerator / lambdaDenominator;
		lambda = Math.max(0, Math.min(lambdaU, C * delta(yPos, obj.y)));
		
		//update coefficients
		obj.beta[yPos] += lambda;
		obj.beta[yNeg] += lambda;
		
		//update gradients
		for (SupportPattern present : s) {
			
			tempResult = featureTransform(present.x, present.y);
			double kernelZero = dist(tempResult, tempResultA);
			double kernelOne = dist(tempResult, tempResultB);
			present.gradient[present.y] -= lambda * (kernelZero - kernelOne);
		}
	}
	
	public double[][] Struct (double[][] frame, double[][] prevBox) {
		
		int i, j; //counter variables
		double yCurrent = 0;
		
		//works if the function never returns negative
		
	}
}
