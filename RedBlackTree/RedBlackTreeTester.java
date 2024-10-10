package RedBlackTree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RedBlackTreeTester {

    @Test
    void testInsert() {
        // this is the test case silver went over on the board
        RedBlackTree rbtree1 = new RedBlackTree(30);
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
        RedBlackTree rbtree1 = new RedBlackTree(30);
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
}

