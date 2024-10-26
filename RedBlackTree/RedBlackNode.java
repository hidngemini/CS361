package RedBlackTree;

public class RedBlackNode<T extends Comparable<T>> {
    
    private T value;    
    private boolean colour; // true = red
    private RedBlackNode<T> leftChild;
    private RedBlackNode<T> rightChild;
    private transient int depth = -1;

    public RedBlackNode(T data, boolean colour, RedBlackNode<T> leftChild, RedBlackNode<T> rightChild) {
        this.value = data;
        this.colour = colour;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public RedBlackNode(T data, boolean colour) {
        this.value = data;
        this.colour = colour;
        this.leftChild = null;
        this.rightChild = null;
    }

    public String toString() {
        return this.value.toString();
    }

    protected void colourFlip() {
        changeColour();
        if (this.leftChild != null) {
            this.leftChild.changeColour();
        }
        if (this.rightChild != null) {
            this.rightChild.changeColour();
        }
    }

    protected void rotateLeft() {
        
        // guaranteed to work since this node must have either a child or null
        RedBlackNode<T> tempLeft = this.leftChild;

        // rotateLeft only happens when there's a right child, so all these will work
        RedBlackNode<T> tempRightLeft = this.rightChild.getLeftChild();
        RedBlackNode<T> tempRightRight = this.rightChild.getRightChild();

        // swap parent and right child's values
        T tempRightValue = (T) this.rightChild.getData();
        this.rightChild.setData(this.getData());
        this.setData(tempRightValue);

        this.leftChild = this.rightChild; // make parent the new left child 
        this.rightChild = tempRightRight; // give right its old right child

        this.leftChild.leftChild = tempLeft; // old parent gets its old left child
        this.leftChild.rightChild = tempRightLeft; // old parents gets new parent's old left
    }

    protected void rotateRight() {
        
        // guaranteed to work since this node must have either a child or null
        RedBlackNode<T> tempRight = this.rightChild;

        // rotateLeft only happens when there's a right child, so all these will work
        RedBlackNode<T> tempLeftLeft = this.leftChild.getLeftChild();
        RedBlackNode<T> tempLeftRight = this.leftChild.getRightChild();

        // swap parent and left child's values
        T tempLeftValue = (T) this.leftChild.getData();
        this.leftChild.setData(this.getData());
        this.setData(tempLeftValue);

        this.rightChild = this.leftChild; // make parent the new right child
        this.leftChild = tempLeftLeft; // give left its old left child

        this.rightChild.rightChild = tempRight; // old parent gets its old right child
        this.rightChild.leftChild = tempLeftRight; // old parent gets new parent's old right

    }

    protected void changeColour() {
        this.colour = !this.colour;
    }

    public void selfCorrect() {
        boolean[] corrections = this.getCorrectionData();
        boolean toRotateLeft = corrections[0];
        boolean toRotateRight = corrections[1];
        boolean toColourFlip = corrections[2];
        while(toRotateLeft || toRotateRight || toColourFlip) {
            if (toRotateLeft) {
                this.rotateLeft();
            }
            if (toRotateRight) {
                this.rotateRight();
            }
            if (toColourFlip) {
                this.colourFlip();
            }
            corrections = this.getCorrectionData();
            toRotateLeft = corrections[0];
            toRotateRight = corrections[1];
            toColourFlip = corrections[2];
        }
    }

    private boolean[] getCorrectionData() {
        boolean hasRedRight = false;
        if (this.rightChild != null) {
            hasRedRight = this.rightChild.getColour();
        }
        boolean hasRedLeft = false;
        if (this.leftChild != null) {
            hasRedLeft = this.leftChild.getColour();
        }
        boolean leftHasRedLeft = false;
        if (this.leftChild != null) {
            leftHasRedLeft = this.leftChild.leftChild != null && this.leftChild.getLeftChild().getColour();
        }
        boolean[] toReturn = {hasRedRight && !hasRedLeft, hasRedLeft && leftHasRedLeft, hasRedLeft && hasRedRight};
        return toReturn;
    }

    protected void insert(T value) {
        this.depth = -1; // reset depth when inserting
        if (value.compareTo(this.value) < 0) {
            if (this.leftChild == null) {
                this.leftChild = new RedBlackNode(value, true);
            } else {
                this.leftChild.insert(value);
            }
        } else {
            if (this.rightChild == null) {
                this.rightChild = new RedBlackNode(value, true);
            } else {
                this.rightChild.insert(value);
            }
        }
        this.selfCorrect();
    }

    protected boolean contains(T value) {
        if (this.value.equals(value)) {
            return true;
        }
        if (value.compareTo(this.value) > 0 && this.rightChild != null) {
            return this.rightChild.contains(value);
        } else if (value.compareTo(this.value) < 0 && this.leftChild != null) {
            return this.leftChild.contains(value);
        } else {
            return false;
        }
    }

    protected RedBlackNode<T> remove(T value) throws Exception {
        RedBlackNode toReturn;

        // weird edge case
        if (this != null && this.rightChild != null && this.rightChild.getColour() 
        && this.rightChild.leftChild != null && this.rightChild.getColour()) {
            this.rightChild.rotateLeft();
            this.rotateLeft();
        }

        if (value.compareTo(this.value) == 0) {
            // goal
            if (this.leftChild == null && this.rightChild == null) {
                toReturn = null;
            } else if (this.rightChild == null) {
                toReturn = this.leftChild;
            } else if (this.leftChild == null) {
                throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
            } else {
                if (this.leftChild.getColour()) {
                    // red left child
                    RedBlackNode<T> pred = this.getPredecessor();
                    T tempPredValue = (T) pred.getData();
                    pred.setData(this.getData());
                    this.setData(tempPredValue);
                    if (!this.leftChild.getColour()) {
                        this.colourFlip();
                    } 
                    this.leftChild = this.leftChild.remove(pred.getData());
                    toReturn = this;
                } else if (this.rightChild.getColour()){
                    throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
                } else {
                    // both black
                    RedBlackNode<T> pred = this.getPredecessor();
                    T tempPredValue = (T) pred.getData();
                    pred.setData(this.getData());
                    this.setData(tempPredValue);
                    if (!this.leftChild.getColour()) {
                        this.colourFlip();
                    } 
                    this.leftChild = this.leftChild.remove(pred.getData());
                    toReturn = this;
                }
            }
        } else if (value.compareTo(this.value) > 0) {
            // right
            if (this.leftChild == null && this.rightChild == null) {
                toReturn = this;
            } else if (this.rightChild == null) {
                toReturn = this;
            } else if (this.leftChild == null) {
                throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
            } else {
                if (this.leftChild.getColour()) {
                    // red left child
                    this.rotateRight();
                } else if (this.rightChild.getColour()){
                    throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
                } else {
                    // both black
                    this.colourFlip();
                }
            }
            this.rightChild = this.rightChild.remove(value);
            toReturn = this;
        } else {
            // left
            if (this.leftChild == null && this.rightChild == null) {
                toReturn = this;
            } else if (this.rightChild == null) {
                toReturn = this.leftChild.remove(value);
            } else if (this.leftChild == null) {
                throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
            } else {
                if (this.leftChild.getColour()) {
                    // red left child
                    // all good; keep going
                } else if (this.rightChild.getColour()){
                    throw new Exception("your red black tree sucks idiot this isn't supposed to happen");
                } else {
                    // both black
                    this.colourFlip();
                }
            }
            this.leftChild = this.leftChild.remove(value);
            toReturn = this;
        }
        if (toReturn != null) {
            toReturn.selfCorrect();
        }
        return toReturn;
    }

    protected RedBlackNode<T> getPredecessor() {
        if (this.leftChild != null) {
            return this.leftChild.getMax();
        } else {
            return null;
        }
    }

    protected RedBlackNode<T> getMax() {
        if (this.rightChild != null) {
            return this.rightChild.getMax();
        } else {
            return this;
        }
    }

    /*
     * |-----------------------|
     * |                       |
     * |  Getters and Setters  |
     * |                       |
     * |-----------------------|
     */

    public T getData() {
        return this.value;
    }

    public void setData(T data) {
        this.value = data;
    }

    public RedBlackNode getLeftChild() {
        return this.leftChild;
    }

    protected void setLeftChild(RedBlackNode<T> leftChild) {
        this.leftChild = leftChild;
    }

    public RedBlackNode getRightChild() {
        return this.rightChild;
    }

    protected void setRightChild(RedBlackNode<T> rightChild) {
        this.rightChild = rightChild;
    }

    public boolean getColour() {
        return this.colour;
    }

    protected int getDepth() {
        if (this.depth == -1) { // pseudo dynamic programming kinda sorta
            if (this.leftChild == null && this.rightChild == null) {
                this.depth = 1;
            } else if (this.leftChild == null) {
                this.depth = this.rightChild.getDepth() + 1;
            } else if (this.rightChild == null) {
                this.depth = this.leftChild.getDepth() + 1;
            } else {
                this.depth = Math.max(this.leftChild.getDepth(), this.rightChild.getDepth()) + 1;
            }
        } 
        return this.depth;
    }

    /*
     * |--------------------|
     * |                    |
     * |  Printing Utilies  |
     * |                    |
     * |--------------------|
     */

    protected String[] getHeapRepresentation(int depth, int maxLength) {
        String[] leftHeap;
        String[] rightHeap; 
        String[] heapRepresentation = new String[(int) (Math.pow(2,depth))-1];
        String toAppend;
        if (this.colour) {
            toAppend = "r" + this.value.toString();
        } else {
            toAppend = "f" + this.value.toString();
        }
        if (this.leftChild == null && this.rightChild == null) {
            heapRepresentation[0] = toAppend;
            leftHeap = getEmpty(depth-1, maxLength);
            rightHeap = getEmpty(depth-1, maxLength);
        } else if (this.leftChild == null) {
            heapRepresentation[0] = toAppend;
            leftHeap = getEmpty(depth-1, maxLength);
            rightHeap = this.rightChild.getHeapRepresentation(depth-1, maxLength);
        } else if (this.rightChild == null) {
            heapRepresentation[0] = toAppend;
            leftHeap = this.leftChild.getHeapRepresentation(depth-1, maxLength);
            rightHeap = getEmpty(depth-1, maxLength);
        } else {
            heapRepresentation[0] = toAppend;
            leftHeap = this.leftChild.getHeapRepresentation(depth-1, maxLength);
            rightHeap = this.rightChild.getHeapRepresentation(depth-1, maxLength);
        }
        for (int i = 0; i < depth; i++) {
            int pow = (int) Math.pow(2, i-1);
            for (int j = 0; j < pow; j++) {
                int subHeapStartingPoint = (int) Math.pow(2,i-1)-1;
                int pow2 = (int) Math.pow(2,i);
                heapRepresentation[pow2+j-1] = leftHeap[subHeapStartingPoint+j];
                heapRepresentation[pow2+j+pow-1] = rightHeap[subHeapStartingPoint+j];
            }
        }
        return heapRepresentation;
    }

    private static String[] getEmpty(int depth, int maxLength) {
        int size = (int) (Math.pow(2,depth))-1;
        String[] heapRepresentation = new String[size];
        String toAdd = "f";
        for (int i = 0; i < maxLength; i++) {
            toAdd += "-";
        }
        for (int i = 0; i < heapRepresentation.length; i++) {
            heapRepresentation[i] = toAdd;
        }
        return heapRepresentation;
    }

    protected int getMaxLengthOfEntry() {
        if (this.leftChild == null && this.rightChild == null) {
            return this.value.toString().length();
        } else if (this.leftChild == null) {
            return Math.max(this.value.toString().length(),this.rightChild.getMaxLengthOfEntry());
        } else if (this.rightChild == null) {
            return Math.max(this.value.toString().length(),this.leftChild.getMaxLengthOfEntry());
        } else {
            return Math.max(this.leftChild.getMaxLengthOfEntry(),this.rightChild.getMaxLengthOfEntry());
        }
    }
}


