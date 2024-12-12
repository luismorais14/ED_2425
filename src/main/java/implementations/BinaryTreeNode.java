package implementations;

/**
 * BinaryTreeNode represents a node in a binary tree with a left and
 * right child.
 */
public class BinaryTreeNode<T> {
    protected T element;
    protected BinaryTreeNode<T> left, right;

    /**
     * Creates a new tree node with the specified data.
     *
     * @param obj the element that will become a part of
     *            the new tree node
     */
    BinaryTreeNode(T obj) {
        element = obj;
        left = null;
        right = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return The element of type T in the current node.
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element in this node.
     *
     * @param element The new element to store in the node.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the left child node of this node.
     *
     * @return The left child node of type BinaryTreeNode<T>.
     */
    public BinaryTreeNode<T> getLeft() {
        return left;
    }

    /**
     * Sets the left child node of this node.
     *
     * @param left The new left child node to set.
     */
    public void setLeft(BinaryTreeNode<T> left) {
        this.left = left;
    }

    /**
     * Returns the right child node of this node.
     *
     * @return The right child node of type BinaryTreeNode<T>.
     */
    public BinaryTreeNode<T> getRight() {
        return right;
    }

    /**
     * Sets the right child node of this node.
     *
     * @param right The new right child node to set.
     */
    public void setRight(BinaryTreeNode<T> right) {
        this.right = right;
    }

    /**
     * Returns the number of non-null children of this node.
     * This method may be able to be written more efficiently.
     *
     * @return the integer number of non-null children of this node
     */
    public int numChildren() {
        int children = 0;
        if (left != null)
            children = 1 + left.numChildren();
        if (right != null)
            children = children + 1 + right.numChildren();
        return children;
    }
}
