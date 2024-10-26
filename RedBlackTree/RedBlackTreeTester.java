package RedBlackTree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RedBlackTreeTester {

    @Test
    void testInsert() {
        // this is the test case silver went over on the board
        RedBlackTree<Integer> rbtree1 = new RedBlackTree<Integer>(30);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
    
        rbtree1.insert(18);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());

        rbtree1.insert(46);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getRightChild().getData());
        
        rbtree1.insert(72);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(72, rbtree1.getRoot().getRightChild().getData());
        assertEquals(46, rbtree1.getRoot().getRightChild().getLeftChild().getData());

        rbtree1.insert(100);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        // value
        assertEquals(72, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getLeftChild().getRightChild().getData());

        rbtree1.insert(91);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        // value
        assertEquals(72, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getLeftChild().getRightChild().getData());
        assertEquals(91, rbtree1.getRoot().getRightChild().getLeftChild().getData());

        rbtree1.insert(5);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getColour());
        // value
        assertEquals(72, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getLeftChild().getRightChild().getData());
        assertEquals(91, rbtree1.getRoot().getRightChild().getLeftChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getData());

        rbtree1.insert(95);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getColour());
        // value
        assertEquals(72, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(95, rbtree1.getRoot().getRightChild().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getLeftChild().getRightChild().getData());
        assertEquals(91, rbtree1.getRoot().getRightChild().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getRightChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getData());
    }

    @Test
    void testContains() {
        RedBlackTree<Integer> rbtree1 = new RedBlackTree<Integer>(30);
        rbtree1.insert(18);
        rbtree1.insert(46);
        rbtree1.insert(72);
        rbtree1.insert(100);
        assertTrue(rbtree1.contains(30));
        assertTrue(rbtree1.contains(18));
        assertTrue(rbtree1.contains(46));
        assertTrue(rbtree1.contains(72));
        assertTrue(rbtree1.contains(100));
        assertFalse(rbtree1.contains(-1));
        assertFalse(rbtree1.contains(101));
        assertFalse(rbtree1.contains(0));
    }

    @Test
    void testRemove() throws Exception {
        RedBlackTree<Integer> rbtree1 = new RedBlackTree<Integer>();
        rbtree1.insert(1);
        assertEquals(1, rbtree1.getRoot().getData());
        rbtree1.remove(1);
        assertEquals(null, rbtree1.getRoot());
        rbtree1.remove(0);
        assertEquals(null, rbtree1.getRoot());
        // we know this set of inserts works if testInsert() works :)
        rbtree1.insert(30);
        rbtree1.insert(18);
        rbtree1.insert(46);
        rbtree1.insert(72);
        rbtree1.insert(100);
        rbtree1.insert(91);
        rbtree1.insert(5);
        rbtree1.insert(95);

        rbtree1.remove(95);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getColour());
        // value
        assertEquals(72, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(46, rbtree1.getRoot().getLeftChild().getRightChild().getData());
        assertEquals(91, rbtree1.getRoot().getRightChild().getLeftChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getLeftChild().getData());

        rbtree1.remove(72);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getRightChild().getLeftChild().getColour());
        // value
        assertEquals(46, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getRightChild().getData());
        assertEquals(91, rbtree1.getRoot().getRightChild().getLeftChild().getData());

        // this test also tests the weird edge case
        rbtree1.remove(91);
        System.out.println(rbtree1);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getRightChild().getColour());
        // value
        assertEquals(46, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getRightChild().getData());

        rbtree1.remove(46);
        System.out.println(rbtree1);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getLeftChild().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
        assertEquals(18, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getLeftChild().getData());

        rbtree1.remove(18);
        System.out.println(rbtree1);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertFalse(rbtree1.getRoot().getLeftChild().getColour());
        assertFalse(rbtree1.getRoot().getRightChild().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());
        assertEquals(5, rbtree1.getRoot().getLeftChild().getData());
        assertEquals(100, rbtree1.getRoot().getRightChild().getData());

        rbtree1.remove(5);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        assertTrue(rbtree1.getRoot().getLeftChild().getColour());
        // value
        assertEquals(100, rbtree1.getRoot().getData());
        assertEquals(30, rbtree1.getRoot().getLeftChild().getData());

        rbtree1.remove(100);
        // colour
        assertFalse(rbtree1.getRoot().getColour());
        // value
        assertEquals(30, rbtree1.getRoot().getData());

        rbtree1.remove(30);
        assertNull(rbtree1.getRoot());
    }
}

