import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HW4Tester {

	@Test
	void testPeddler(){
		int[] weights1 = {1, 2, 3};
    	int[] values1 = {6, 10, 12};
        int maxWeight1 = 5;
        int[] soltn1 = {1, 2};  // base-0 indices of the chosen items
		assertArrayEquals(soltn1, HW4.peddler(values1, weights1, maxWeight1));
        
		int[] weights2 = {3, 4, 5, 6};
    	int[] values2 = {2, 3, 1, 4};
        int maxWeight2 = 8;
        int[] soltn2 = {0, 1}; // base-0 indices of the chosen items
        assertArrayEquals(soltn2, HW4.peddler(values2, weights2, maxWeight2));
        
		int[] weights3 = {1, 3, 5, 7};
    	int[] values3 = {2, 4, 7, 10};
        int maxWeight3 = 8;
        int[] soltn3 = {0, 3}; // base-0 indices of the chosen items
        assertArrayEquals(soltn3, HW4.peddler(values3, weights3, maxWeight3));        
	}

	@Test
	void testPalindrome() {
		assertEquals("", HW4.longestPalindromeSubstring(""));
		assertEquals("a", HW4.longestPalindromeSubstring("a"));
		assertEquals("carac", HW4.longestPalindromeSubstring("character"));		
		assertEquals("civic", HW4.longestPalindromeSubstring("civic"));
		assertEquals("civic", HW4.longestPalindromeSubstring("xxxciviczzz"));		
	}

	@Test
	void testSkis() {
		int[] skiers1 = {66, 67, 68};
		int[] skis1 = {61, 62, 63, 64, 65};
		int[] soltn1 = {2, 3, 4}; // base-0 index of ski assigned to each person

		int[] skiers2 = {72, 73, 75};
		int[] skis2 = {63, 72, 73, 77};
		int[] soltn2 = {1, 2, 3}; // base-0 index of ski assigned to each person

		int[] skiers3 = {66, 67, 68};
		int[] skis3 = {65, 66, 67, 68, 69};
		int[] soltn3 = {1, 2, 3}; // base-0 index of ski assigned to each person

		int[] answer1 = HW4.skiAssignments(skiers1, skis1);
		int[] answer2 = HW4.skiAssignments(skiers2, skis2);
		int[] answer3 = HW4.skiAssignments(skiers3, skis3);

		assertArrayEquals(soltn1, answer1);
		assertArrayEquals(soltn2, answer2);
		assertArrayEquals(soltn3, answer3);

	}
}

