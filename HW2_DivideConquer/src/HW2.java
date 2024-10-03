/**
 * CS361 -- Homework 2
 * Divide and Conquer
 * 
 * @author maddyS
 * @version 2024.10.3
 */
public class HW2 {

    protected static int dopamine(int[] array){
        // calls recursive version of dopamine
    	return dopamine(array, 0, array.length-1);
    }

    protected static boolean detectFraud(int[] array){
        int mostCommon = findMostCommon(array, 0, array.length-1);
        int numMostCommon = countInstancesOf(mostCommon, array, 0, array.length);
    	return (numMostCommon > array.length/2);
    }
    

   
    /*--------------------------------------------------------
     *  	PRIVATE METHODS - FEEL FREE TO ADD YOUR OWN
     *--------------------------------------------------------*/
    
     /**
      * This method recursively checks half of the given array
      * depending on if the last elt of the left is bigger
      * than the first elt on the right.
      * 
      * @param array - array to be checked
      * @param start - start of range to be checked
      * @param end - end of range to be checked
      * @return - index of the peak 
      */
    private static int dopamine(int[] array, int start, int end) {
        // base case of length 1
        if (start == end) {
            return start;
        }

        int mid = (start+end)/2;
        int max;

        // the peak will always be on the side of the larger number
        if (array[mid] > array[mid+1]) {
            max = dopamine(array, start, mid); // recurse on the left
        } else {
            max = dopamine(array, mid+1, end); // recurse on the right
        }
        
        return max;
    }

    /*
     * contrary to its name, it does not always find most common in
     * the whole array, rather, it will find the most common in each
     * subarray. This is okay since, if there is fraud in the overall
     * array, the fraudulent card must be the most common in at least
     * one of the two subarrays.
     */
    private static int findMostCommon(int[] array, int start, int end) {
        if (start == end) {
            return array[start];
        }

        int mid = (start+end)/2;

        int leftMostCommon = findMostCommon(array, start, mid);
        int rightMostCommon = findMostCommon(array, mid+1, end);

        int numTotalLeftMostCommon = countInstancesOf(leftMostCommon, array, start, end);
        int numTotalRightMostCommon = countInstancesOf(rightMostCommon, array, start, end);

        return numTotalLeftMostCommon > numTotalRightMostCommon ? leftMostCommon : rightMostCommon;
    }

    private static int countInstancesOf(int elt, int[] array, int start, int end) {
        int count = 0;
        for (int i = start; i < end; i++) {
            if (equivalent(elt, array[i])) {
                count++;
            }
        }
        return count;
    }
    
    // Tests if two bank accounts are equivalent. Do not modify
    private static boolean equivalent(int a, int b) {
    	return a == b;
    }
    
    
    /*--------------------------------------------------------
     *  				THE MAIN METHOD
     *--------------------------------------------------------*/
    public static void main(String[] args){
        int[] array = {0, 10, 25, 27, 25, 22, 20, 15, 8, 5};
        int ans = dopamine(array);
        System.out.println(ans);

        int[] array2 = {1, 2, 1, 3, 1, 6, 1, 7, 1};

        boolean isFraud = detectFraud(array2);
        System.out.println(isFraud);
    }
}
