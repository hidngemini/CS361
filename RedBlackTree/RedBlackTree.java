package RedBlackTree;

public class RedBlackTree<T extends Comparable<T>> {
    
    private RedBlackNode<T> root;

    public RedBlackTree(T rootValue) {
        this.root = new RedBlackNode<T>(rootValue, false);
    }

    public void insert(T value) {
        this.root.insert(value);
        if (this.root.getColour()) {
            this.root.changeColour();
        }
    }

    public boolean contains(T value) {
        return this.root.contains(value);
    }

    public int getDepth() {
        return this.root.getDepth();
    }

    public RedBlackNode getRoot() {
        return this.root;
    }

    /*
     * |--------------------|
     * |                    |
     * |  Printing Utilies  |
     * |                    |
     * |--------------------|
     */

    public String[] toStringArr(int maxLength) {
        int depth = this.root.getDepth();
        String[] heapRep = this.root.getHeapRepresentation(depth, maxLength);
        return heapRep;
    }

    public String toString() {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        int maxLength = this.root.getMaxLengthOfEntry();
        String[] heapRep = this.toStringArr(maxLength);
        int depth = (int) (Math.log(heapRep.length+1) / Math.log(2));
        StringBuilder stringRep = new StringBuilder();
        int depthOffset = 0;

        String baseOffset = "";
        for (int i = 0; i<maxLength; i++) {
            baseOffset += " ";
        }

        for (int i = 0; i<depth; i++) {
            String offset = "";
            for (int j = 0; j<Math.pow(2,depth-i)-1; j++) {
                offset += baseOffset;
            }
            for (int j = 0; j<Math.pow(2,depth-i-1)-1; j++) {
                stringRep.append(baseOffset);
            }
            for (int j = 0; j<Math.pow(2,i); j++) {
                if (heapRep[j+depthOffset].charAt(0) == 'r') {
                    stringRep.append(ANSI_RED);
                } else {
                    stringRep.append(ANSI_RESET);
                }
                while (heapRep[j+depthOffset].length() != maxLength+1) {
                    heapRep[j+depthOffset] += " ";
                }
                stringRep.append(heapRep[j+depthOffset].substring(1));
                stringRep.append(offset);
            }
            depthOffset += Math.pow(2,i);
            stringRep.append('\n');
        }
        return stringRep.toString();
    }
    

    public static void main(String[] args) {
        RedBlackTree myTree = new RedBlackTree(30);
        myTree.insert(18);
        System.out.println(myTree);
        myTree.insert(46);
        System.out.println(myTree);
        myTree.insert(72);
        System.out.println(myTree);
        myTree.insert(100);
        System.out.println(myTree);
        myTree.insert(91);
        System.out.println(myTree);
        myTree.insert(5);
        System.out.println(myTree);
        myTree.insert(95);
        System.out.println(myTree);
    }
}
