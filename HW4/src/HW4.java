import java.util.Arrays;

/**
 * CS361 -- Homework 4
 * Dynamic Programming
 * 
 * Feel free to add your own private methods and to reorganize the methods as you'd like.
 *  
 * The only thing I ask is that you keep the "protected" methods protected so that they can
 * be accessed by the unit tests.
 * 
 * @author maddyS
 * @version 2024.10.30
 */
public class HW4 {

	// Returns an integer array containing the indices of the chosen items
	// These indices should be returned using base-0 indexing
    protected static int[] peddler(int[] values, int[] weights, int maxWeight){
        // initialize maxMoneyArr 2D array
        int[][] maxMoneyArr = new int[values.length+1][maxWeight+1];

        // base cases
        // note that neither of these base cases are required since it's automatically populated with 0s
        for (int i = 0; i < maxWeight+1; i++) {
            maxMoneyArr[0][i] = 0;
        }

        for (int i = 0; i < values.length+1; i++) {
            maxMoneyArr[i][0] = 0;
        }

        // populate 2D array
        for (int i = 1; i < maxMoneyArr.length; i++) {
            for (int j = 1; j < maxMoneyArr[0].length; j++) {
                if (j - weights[i-1] < 0) {
                    // item is too heavy
                    maxMoneyArr[i][j] = maxMoneyArr[i-1][j];
                } else {
                    // check both taking and leaving item and keep max value
                    maxMoneyArr[i][j] = Math.max(maxMoneyArr[i-1][j], values[i-1]+maxMoneyArr[i-1][j-weights[i-1]]);
                }
            }
        }

        // find solution from populated 2D array
        return peddlerSolution(maxMoneyArr, weights, values, maxWeight, values.length-1);
    }

    // Returns the longest palindrome that is a substring of the input string
    protected static String longestPalindromeSubstring(String inputString) {
        int[][] lengthOfPalindrome = new int[inputString.length()+2][inputString.length()+1];

        // base cases
        // note that neither of thes base cases are required since it's automatically populated with 0s
        for (int i = 0; i < lengthOfPalindrome.length; i++) {
            lengthOfPalindrome[i][0] = 0;
        }

        for (int i = 0; i< lengthOfPalindrome[0].length; i++) {
            lengthOfPalindrome[inputString.length()+1][i] = 0;
        }

        // populate 2D array
        for (int startPos = inputString.length(); startPos > 0; startPos--) {
            for (int endPos = 1; endPos < inputString.length()+1; endPos++) {
                if (startPos > endPos) {
                    // starts after it ends; this is effectively an empty string
                    lengthOfPalindrome[startPos][endPos] = 0;
                } else if (startPos == endPos) {
                    // start and end are the same, so add one to the palindrome length
                    lengthOfPalindrome[startPos][endPos] = 1+lengthOfPalindrome[startPos+1][endPos-1];
                } else if (inputString.charAt(startPos-1) == inputString.charAt(endPos-1)) {
                    // start and end have the same character but aren't the equal, so add two to the palindrome length
                    lengthOfPalindrome[startPos][endPos] = 2+lengthOfPalindrome[startPos+1][endPos-1];
                } else {
                    // the characters do not match. throw either the first or last character out
                    lengthOfPalindrome[startPos][endPos] = Math.max(lengthOfPalindrome[startPos+1][endPos], lengthOfPalindrome[startPos][endPos-1]);
                }
            }
        }

        // find solution from populated 2D array
    	return longestPalindromeSubstringSolution(lengthOfPalindrome, inputString, 0, inputString.length()-1);
    }
    
    // Returns an integer array whose length is equal to the number of people (i.e. skiers)
    // The ith entry in the array is the index of the assigned ski for the ith person
    protected static int[] skiAssignments(int[] skierHeights, int[] skiLengths) {
    	int[][] differences = new int[skierHeights.length+1][skiLengths.length+1];
        // base cases
        for (int i = 0; i < differences.length; i++) {
            differences[i][0] = Integer.MAX_VALUE; // if there are not enough skis, effectly say not possible
        }

        // note this base case isn't required since it's automatically populated with 0s
        for (int i = 0; i < differences[0].length; i++) {
            differences[0][i] = 0;
        }

        // populate 2D array
        for (int i = 1; i < differences.length; i++) {
            for (int j = 1; j < differences[0].length; j++) {
                if (differences[i-1][j-1] == Integer.MAX_VALUE){
                    // there are not enough skis for everyone; Integer.MAX_VALUE will effectively never be chosen
                    differences[i][j] = Integer.MAX_VALUE;
                } else {
                    // take the minimum of taking these skis and trying another set of skis
                    differences[i][j] = Math.min(
                        Math.abs(skierHeights[i-1]-skiLengths[j-1]) + differences[i-1][j-1],
                        differences[i][j-1]
                    );
                }
            }
        }

        // find solution from populated 2D array
        return skiAssignmentsSolution(differences, skierHeights, skiLengths, skierHeights.length-1, skiLengths.length-1);
    }

    private static int[] peddlerSolution(int[][] maxMoneyArr, int[] weights, int[] values, int maxWeight, int consideredItem) {
        // base cases
        // considered item -1 since otherwise we don't check 0th item (indexed)
        if (maxWeight == 0 || consideredItem == -1) {
            return new int[0];
        } 
        
        // this item is too heavy to take
        if (maxWeight-weights[consideredItem] < 0) {
            return peddlerSolution(maxMoneyArr, weights, values, maxWeight, consideredItem-1);
        } 
        
        // is it better to take this item or not?
        if (maxMoneyArr[consideredItem][maxWeight] > values[consideredItem] + maxMoneyArr[consideredItem][maxWeight-weights[consideredItem]]) {
            // don't take the item
            return peddlerSolution(maxMoneyArr, weights, values, maxWeight, consideredItem-1);
        } else {
            // take the item and add it to return array
            int[] withoutCurrentItem = peddlerSolution(maxMoneyArr, weights, values, maxWeight-weights[consideredItem], consideredItem-1);
            int[] withCurrentItem = Arrays.copyOf(withoutCurrentItem, withoutCurrentItem.length+1);
            withCurrentItem[withCurrentItem.length-1] = consideredItem;
            return withCurrentItem;
        }
    }

    private static String longestPalindromeSubstringSolution(int[][] lengths, String inputString, int startPos, int endPos) {
        // if we start after we finish, effectively an empty string (or if we ARE an empty string)
        if (startPos > endPos || inputString.equals("")) {
            return "";
        }

        // if we start and end on the same position, return the character at that position
        if (startPos == endPos ) {
            return "" + inputString.charAt(startPos);
        }

        // if characters are the same at start and end (but start != end), subproblem starting and ending with that character
        if (inputString.charAt(startPos) == inputString.charAt(endPos)) {
            return inputString.charAt(startPos) + longestPalindromeSubstringSolution(lengths, inputString, startPos+1, endPos-1) + inputString.charAt(endPos);
        }

        if (lengths[startPos+1][endPos] >= lengths[startPos+2][endPos+1]) {
            // it's better to keep the first character
            return longestPalindromeSubstringSolution(lengths, inputString, startPos, endPos-1);
        } else {
            // it's better to keep the last character
            return longestPalindromeSubstringSolution(lengths, inputString, startPos+1, endPos);
        }
    }
    
    private static int[] skiAssignmentsSolution(int[][] differences, int[] heights, int[] lengths, int consideredIndividual, int consideredSkis) {
        // base case
        if (consideredIndividual == -1) {
            return new int[heights.length];
        }

        // calculating these here since they're too long for the if statement below
        int takeSkis = differences[consideredIndividual][consideredSkis] + Math.abs(heights[consideredIndividual] - lengths[consideredSkis]);
        int leaveSkis = differences[consideredIndividual+1][consideredSkis];

        if (takeSkis < leaveSkis) {
            // it's better to take assign these skis
            int[] solution = skiAssignmentsSolution(differences, heights, lengths, consideredIndividual-1, consideredSkis-1);
            solution[consideredIndividual] = consideredSkis;
            return solution;
        } else {
            // it's better to try the next skis
            return skiAssignmentsSolution(differences, heights, lengths, consideredIndividual, consideredSkis-1);
        }
    }

    /*--------------------------------------------------------
     *  				THE MAIN METHOD
     *--------------------------------------------------------*/
    public static void main(String[] args){
    	
    	// Peddler
        int[] weights = {1, 2, 3};
    	int[] values = {6, 10, 12};
        int maxWeight = 5;
        
        int[] chosenItems = peddler(values, weights, maxWeight);
        System.out.println("Weights:" + Arrays.toString(weights));
        System.out.println("Values:" + Arrays.toString(values));
        System.out.println("Max weight:" + maxWeight);
        System.out.println("Chosen Items:" + Arrays.toString(chosenItems));
        System.out.println();
        
        // Palindrome subsequence
        String s = "character";
        String palindrome = longestPalindromeSubstring(s);
        System.out.println("The longest palindrome substring of \"" + s + "\" is " + palindrome);
        System.out.println();
        
        // Ski assignments
        int[] skierHeights = {66, 67, 68};
        int[] skiLengths = {61, 62, 63, 64, 65};
		//int[] soltn3 = {1, 2, 3};
        int[] assignments = skiAssignments(skierHeights, skiLengths);
        System.out.println("Skier Heights: " + Arrays.toString(skierHeights));
        System.out.println("Ski Lengths: " + Arrays.toString(skiLengths));
        System.out.println("Assignments: " + Arrays.toString(assignments));
    }
}
