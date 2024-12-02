package implementations;

public class LinearNode<T> {
    private T element;
    private LinearNode<T> next;

    /**
     * Creates an empty node.
     */
    public LinearNode() {
        element = null;
        next = null;
    }

    /**
     * Creates a node storing the specified element.
     * @param element the specified element
     */
    public LinearNode(T element) {
        this.element = element;
        next = null;
    }

    /**
     * Returns the element stored in this node.
     * @return the element stored in this node.
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     * @param element the element stored in this node.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the node that follows this one.
     * @return the node that follows this one.
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Sets the node that follows this one.
     * @param next the node that follows this one.
     */
    public void setNext(LinearNode<T> next) {
        this.next = next;
    }
}
