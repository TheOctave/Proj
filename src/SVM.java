import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import Data.Synthetic;

public class SVM {

	public ArrayList<SupportPattern> s = new ArrayList<SupportPattern>();

	public double gamma;
	public static boolean debug = false;
	public double[][] x;
	
	public static final int budget = 100;
	public static final double C = 100;

	// public int[][][] y;

	public SVM() {
		
	}

	public int summation(int start, int finish) {

	}

	public int costFunction() {

	}

	public int gradientDescent() {

	}

	public double gradient(int index, int y) {

		int i, j, yBar; // counter variables
		double numA, sum;
		double[] tempResultA;
		double[] tempResultB;
		double[] tempX;
		double x1, x2, y1, y2;
		SupportPattern obj = s.get(index);

		numA = -1 * lossFunction(y, obj.y);

		sum = 0;
		for (j = 0; j < s.size(); j++) {

			for (yBar = 0; yBar < 4; yBar++) {
				sum += s.get(j).beta[yBar]
						* kernel(obj.x, y, s.get(j).x, yBar);
			}
		}

		return numA - sum;
	}

	public double innerProduct(double[] vecA, double[] vecB) {

		double result = 0;

		// assumes arrA and arrB are the same size
		for (int i = 0; i < vecA.length; i++) {

			result += vecA[i] * vecB[i];
		}

		return result;
	}

	public double dist(double[] vecA, double[] vecB) {

		int result = 0;
		double square;

		for (int i = 0; i < vecA.length; i++) {

			result += (square = (vecA[i] - vecB[i])) * square;
		}

		return result;
	}

	public int lossFunction(int y1, int y2) {

		// use epsilon comparison later (if labels are double)
		return (y1 == y2) ? 0 : 1;
	}

	public static int delta(int y1, int y2) {

		return (y1 == y2) ? 1 : 0;
	}

	public double[] featureTransform(double[] x, int label) {

		return x;
	}

	public double kernel(double[] x, int y, double[] xP, int yP) {

		double[] a = featureTransform(x, y);
		double[] b = featureTransform(xP, yP);

		double product = innerProduct(a, b);

		return (y == yP) ? product : -1 * product;
	}
	
	public void checkConstraints() {
		
		int i, y;
		double sum;
		int constraintA = 0;
		int constraintB = 0;
		boolean valid;
		
		for (SupportPattern pres : s) {
			
			sum = 0;
			valid = true;
			for (y = 0; y < 4; y++) {
				
				if (pres.beta[y] > delta(y, pres.y) * C)
					valid = false;
				
				sum += pres.beta[y];
			}
			
			if (valid)
				constraintA++;
			
			if (sum == 0)
				constraintB++;
		}
		
		System.out.println("constraintA success rate: " + (1.0 * constraintA / s.size() * 100));
		System.out.println("constraintB success rate: " + (1.0 * constraintB / s.size() * 100));
	}

	public void SMOStep(int index, int yPos, int yNeg) {

		if (debug)
			System.out.println("SMO STEP: " + index + " " + yPos + " " + yNeg);
		SupportPattern obj = s.get(index);

		double kernelZeroZero;
		double kernelOneOne;
		double kernelZeroOne;

		double lambdaNumerator;
		double lambdaDenominator;

		double lambdaU;
		double lambda;

		double[] tempResult;

		kernelZeroZero = kernel(obj.x, yPos, obj.x, yPos);
		kernelOneOne = kernel(obj.x, yNeg, obj.x, yNeg);
		kernelZeroOne = kernel(obj.x, yPos, obj.x, yNeg);

		// System.out.println(kernelZeroZero + " " + kernelOneOne + " " +
		// kernelZeroOne);
		lambdaNumerator = obj.gradient[yPos] - obj.gradient[yNeg];
		lambdaDenominator = kernelZeroZero + kernelOneOne - 2 * kernelZeroOne;
		if (lambdaDenominator == 0){
			return;
		}

		lambdaU = lambdaNumerator / lambdaDenominator;
		// System.out.println(lambdaU);
		lambda = Math.max(0,
				Math.min(lambdaU, C * delta(yPos, obj.y) - obj.beta[yPos]));

		if (debug)
			System.out.println("lambda: " + lambda);
		// update coefficients
		obj.beta[yPos] += lambda;
		obj.beta[yNeg] -= lambda;

		// update gradients
		for (SupportPattern present : s) {

			double kernelZero = kernel(present.x, present.y, obj.x, yPos);
			double kernelOne = kernel(present.x, present.y, obj.x, yNeg);
			present.gradient[present.y] -= lambda * (kernelZero - kernelOne); // gradient
																				// update
																				// happens
																				// here
		}
	}

	public int predict(double[] x) {

		int i, j, yBar; // counter variables

		SupportPattern obj;

		double sum = 0.0;
		double max = Integer.MIN_VALUE;
		int yVal = 0;

		for (i = 0; i < 4; i++) {

			sum = 0;
			for (j = 0; j < s.size(); j++) {

				obj = s.get(j);

				for (yBar = 0; yBar < 4; yBar++) {
					sum += obj.beta[yBar] * kernel(x, i, obj.x, yBar);
				}
				
			}
			
			if(debug)
				System.out.println("prediction sum; " + sum);

			if (sum > max) {
				max = sum;
				yVal = i;
			}
		}

		return yVal;
	}

	public double calculateObjective() {

		int i, j, y, yBar;// counter variables;
		double sumA = 0;
		double sumB = 0;
		double temp = 0;
		SupportPattern obj;
		SupportPattern obj2;

		for (i = 0; i < s.size(); i++) {
			obj = s.get(i);
			for (y = 0; y < 4; y++) {
				
				sumA += lossFunction(obj.y, y) * obj.beta[obj.y];
				
				for (j = 0; j < s.size(); j++) {
					
					obj2 = s.get(j);
					for (yBar = 0; yBar < 4; yBar++) {
						
						sumB += obj.beta[y] * obj2.beta[yBar]
						* kernel(obj.x, y, obj2.x, yBar);
					}
				}
			}

		}
		
		return -sumA - 0.5 * sumB;
	}

	public void processNew(int index, int[] SMOStepVar, int y) {

		if (debug)
			System.out.println("ProcessNew: " + index + " "
					+ Arrays.toString(SMOStepVar) + " " + y);
		int i; // counter variable
		SMOStepVar[0] = y;

		double min = Double.MAX_VALUE;
		double tempSolution;

		for (i = 0; i < 4; i++)
			if ((tempSolution = gradient(index, i)) < min) {

				min = tempSolution;
				SMOStepVar[1] = i;
			}
	}

	public void processOld(int index, int[] SMOStepVar, int y) {

		if (debug)
			System.out.println("process old " + index + " "
					+ Arrays.toString(SMOStepVar) + "  " + y);
		int i; // counter variable

		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		double tempSolution;
		int comp = 0;

		for (i = 0; i < 4; i++) {
			if (s.get(index).y == i)
				comp = 1;
			else
				comp = 0;

			if (s.get(index).beta[i] < comp * C
					&& (tempSolution = gradient(index, i)) > max) {

				max = tempSolution;
				SMOStepVar[0] = i;
			}

			if (s.get(index).beta[i] < comp * C
					&& (tempSolution = gradient(index, i)) < min) {

				min = tempSolution;
				SMOStepVar[1] = i;
			}
		}
	}

	public void optimize(int index, int[] SMOStepVar, int y) {

		if (debug)
			System.out.println("optimize: " + index + " "
					+ Arrays.toString(SMOStepVar) + " " + y);
		int i; // counter variable

		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		double tempSolution;
		int comp;

		for (i = 0; i < 4; i++) {
			if (s.get(index).y == i)
				comp = 1;
			else
				comp = 0;

			if (s.get(index).beta[i] < comp * C
					&& (tempSolution = gradient(index, i)) > max) {

				max = tempSolution;
				SMOStepVar[0] = i;
			}

			if (s.get(index).beta[i] != 0
					&& s.get(index).beta[i] < comp * C && (tempSolution = gradient(index, i)) < min) {

				min = tempSolution;
				SMOStepVar[1] = i;
			}
		}
	}
	
	public void budgetMaintainance() {
		
		int i, j, y; //counter varaibles
		int counter = 0;
		
		
		for(SupportPattern pattern : s) {
			
			for (i = 0; i < 4; i++) {
				
				if (pattern.beta[i] != 0)
					counter++;
			}
		}
		
		if (counter > budget) {
			
			double score;
			int minI = 0, minY = 0;
			
			i = 0;
			for (SupportPattern pattern : s) {
				
				for (y = 0; y < 4; y++) {
					
					score = pattern.beta[y] * pattern.beta[y] * (kernel(pattern.x, y, pattern.x, y)
							+ kernel(pattern.x, pattern.y, pattern.x, pattern.y) - 2 * kernel(pattern.x, y, pattern.x, pattern.y));
					if (score < s.get(minI).beta[minY]) {
						minI = i;
						minY = y;
						score = s.get(minI).beta[minY];
					}
				}
				
				i++;
			}
			
			j = 0; //negative betas
			int posInd = 0;
			for (i = 0; i < 4; i++) {
				if (s.get(minI).beta[i] < 0)
					j++;
				else if (s.get(minI).beta[i] > 0)
					posInd = i;
			}
			
			if (j > 1) {
				s.get(minI).beta[posInd] += s.get(minI).beta[minY];
				
				updateGradients(minI, posInd, -s.get(minI).beta[minY]);
				updateGradients(minI, minY, s.get(minI).beta[minY]);
				
				s.get(minI).beta[minY] = 0;
				
			} else {
				
				for (i = 0; i < 4; i++)
					updateGradients(minI, i, s.get(minI).beta[i]);
				s.remove(s.get(minI));
			}
		}
	}
	
	public void updateGradients (int index, int z, double alpha) {
		
		if (alpha == 0)
			return;
		
		int i;//counter variables
		
		for (SupportPattern pattern : s) {
			
			for (i = 0; i < 4; i++) {
				
				pattern.gradient[i] += alpha * kernel(pattern.x, i, s.get(index).x, z);
			}
		}
	}

	public int process(int y, double[] x) {

		// create a predict function
		int i, j; // counter variables

		// int y = predict(x);
		SupportPattern pattern = new SupportPattern(x, new double[4],
				new double[4], y);

		int index = s.size();
		s.add(pattern);
		for (i = 0; i < 4; i++) {
			
			pattern.gradient[i] = gradient(index, i);
			if(debug)
				System.out.println("gradient; "+ s.get(index).gradient[i]);
		}
		
		int[] SMOStepVar = new int[2];

		processNew(index, SMOStepVar, y);

		SMOStep(index, SMOStepVar[0], SMOStepVar[1]);
		if (debug)
			checkConstraints();

		budgetMaintainance();
		
		Random rand = new Random();
		int randInt;
		for (i = 0; i < 10; i++) {

			randInt = rand.nextInt(s.size());
			processOld(randInt, SMOStepVar, s.get(randInt).y);
			SMOStep(randInt, SMOStepVar[0], SMOStepVar[1]);
			if(debug)
				checkConstraints();
			
			budgetMaintainance();
			for (j = 0; j < 10; j++) {

				randInt = rand.nextInt(s.size());
				optimize(randInt, SMOStepVar, s.get(randInt).y);
				SMOStep(randInt, SMOStepVar[0], SMOStepVar[1]);
				if (debug)
					checkConstraints();
			}
		}

		return y;
	}

	public double[][] Struck(int index, double[][] frame, double[][] prevBox) {

		int i, j; // counter variables
		double yCurrent = 0;

		// works if the function never returns negative

	}

	public static void main(String[] args) {

		System.loadLibrary("opencv_java249");
		Mat m = Highgui.imread("C:/Users/Ikechukwu/Pictures/danbo-hd-wallpaper-desktop-3.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		new ReadImage("C:/Users/Ikechukwu/Pictures/danbo-hd-wallpaper-desktop-3.jpg", m);
		// debug = true;
		Synthetic object = new Synthetic(400);
		double[][] x = object.getPoints();
		int[] y = object.getLabels();

		SVM o = new SVM();
		double func;

		for (int i = 0; i < x.length; i++) {

			if (debug)
				System.out.println("MAIN: " + y[i]);
			o.process(y[i], x[i]);
			func = o.calculateObjective();
			if(debug)
				System.out.println(func);
		}

		Synthetic object2 = new Synthetic(400);
		double[][] x2 = object2.getPoints();
		int[] y2 = object2.getLabels();

		int[] predictions = new int[x.length];
		double correct = 0;

		for (int i = 0; i < x2.length; i++) {

			predictions[i] = o.predict(x2[i]);
			if (debug)
				System.out.println(predictions[i] + " " + y2[i]);
			if (predictions[i] == y2[i])
				correct++;
		}

		System.out.println(correct / x2.length * 100);
	}
}
