public class Harr {

	public int[] function (int[][] img) {
		
		int divHorizontal = img.length / 4;
		int divVertical = img[0].length / 4;
		
		int[] harrValues = new int[5];
		
//		 store grid as an array
	    int[] horizontal = new int[5];
	    int[] vertical = new int[5];
	    
	    //create the cells to return
	    int[] cell = new int[80];
	    
	    //create counter variables
	    int i, j, count;
	    
	    for (i = 1; i < 4; i++) {
	    	horizontal[i] = horizontal[i - 1] + divHorizontal + 1;
	    	vertical[i] = vertical[i - 1] + divVertical + 1;
	    }
	    
	    horizontal[4] = img.length - 1;
	    vertical[4] = img[0].length - 1;
	    
	    count = 0;
	    
	    for (i = 1; i < 5; i++) {
	    	
	    	for (j = 1; j < 5; j++) {
	    		
	    		harrValues = calculateHarr(img, horizontal[i - 1], horizontal[i], vertical[j - 1], vertical[j]);
	    	            cell[count] = harrValues[0];
	    	            count = count + 1;
	    	            cell[count] = harrValues[1];
	    	            count = count + 1;
	    	            cell[count] = harrValues[2];
	    	            count = count + 1;
	    	            cell[count] = harrValues[3];
	    	            count = count + 1;
	    	            cell[count] = harrValues[4];
	    	            count = count + 1;	    	}
	    }
	    
//	    arrayMin = min(cell)
//	    cell = cell - arrayMin
//	    arrayMax = max(cell)
//	    cell = cell / arrayMax
	    
	    return cell;
	}

	public static void calculateHarr(int[][] img, int x1, int y1, int x2, int y2) {

		int[][] sum = new int[x2 - x1  + 1][y2 - y1 + 1];
		int[] sumA = new int[5];
		int[] sumB = new int[5];
					    
			    int halfA = (x2 - x1 + 1) / 2;
			    int halfB = (y2 - y1 + 1) / 2;
			    int thirdA = (x2 - x1 + 1) / 3;
			    int twoThirdA = thirdA * 2;
			    int thirdB = (y2 - y1 + 1) / 3;
			    int twoThirdB = thirdB * 2;
			    
			    int i, j;
			    
			    for (i = x1; i <= x2; i++)
			    	sum[i][i - x1] = img[i][y1];
			    for (i = x1 + 1; i <= x2; i++) {
			    	
			    	for (j = y1 + 1; j <= y2; j++) {
			    		
			    		
			    	}
			    }
			    for i in range(x1, x2 + 1):
			        for j in range(y1, y2 + 1):
			            #deal with harr1
			            if (j < y1 + halfB):
			                sumA[0] = sumA[0] + img[i][j]
			            else:
			                sumB[0] = sumB[0] + img[i][j]
			                
			            #deal with harr2
			            if (i < x1 + halfA):
			                sumA[1] = sumA[1] + img[i][j]
			            else:
			                sumB[1] = sumB[1] + img[i][j]
			                
			            #deal with harr3
			            if (i < x1 + halfA and j < y1 + halfB):
			                sumA[2] = sumA[2] + img[i][j]
			            elif (i < x1 + halfA and j >= y1 + halfB):
			                sumB[2] = sumB[2] + img[i][j]
			            elif (i >= x1 + halfA and j < y1 + halfB):
			                sumB[2] = sumB[2] + img[i][j]
			            else:
			                sumA[2] = sumA[2] + img[i][j]
			                
			            #deal with harr4
			            if (j < y1 + thirdB):
			                sumA[3] = sumA[3] + img[i][j]
			            elif (j < y1 + twoThirdB):
			                sumB[3] = sumB[3] + img[i][j]
			            else:
			                sumA[3] = sumA[3] + img[i][j]
			                
			            #deal with harr4
			            if (i < x1 + thirdA):
			                sumA[4] = sumA[4] + img[i][j]
			            elif (i < x1 + twoThirdA):
			                sumB[4] = sumB[4] + img[i][j]
			            else:
			                sumA[4] = sumA[4] + img[i][j]
			    
			    return sumA - sumB
	}
}
