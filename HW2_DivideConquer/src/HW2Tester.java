import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HW2Tester {

	private final int[] dopamine1 = {0, 10, 25, 27, 25, 22, 20, 15, 8, 5};
    private final int[] dopamine2 = {75, 74, 73, 72, 71, 70, 69, 68, 67, 66};
    private final int[] dopamine3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private final int[] dopamine4 = {0, 1, 5, 8, 25, 35, 40, 41, 38, 30};

    private final int[] fraud1 = {1, 2, 1, 3, 1, 6, 1, 7, 1};
    private final int[] fraud2 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    private final int[] fraud3 = {1, 1, 1, 1, 1, 1, 1, 1, 1};
    private final int[] fraud4 = {1, 2, 1, 2, 1, 2, 1, 2};
    
    @Test
    void testDopamine(){
        assertEquals(3, HW2.dopamine(dopamine1));
        assertEquals(7, HW2.dopamine(dopamine4));
        assertEquals(0, HW2.dopamine(dopamine2));
        assertEquals(9, HW2.dopamine(dopamine3));      
    }
    
    @Test
    void testFraud() {
    	assertEquals(true, HW2.detectFraud(fraud1));
    	assertEquals(false, HW2.detectFraud(fraud2));
    	assertEquals(true, HW2.detectFraud(fraud3));
    	assertEquals(false, HW2.detectFraud(fraud4));
    }

}

