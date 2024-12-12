package implementations;

public class DoubleNode<T> {
    private T element;
    private DoubleNode<T> next;
    private DoubleNode<T> previous;

    /**
     * Default constructor that creates an empty node.
     * Initializes the element as null and the next and previous references as null.
     */
    public DoubleNode() {
        next = null;
        element = null;
        previous = null;
    }

    /**
     * Constructor that creates a node with a specific element.
     * Initializes the next and previous references as null.
     *
     * @param element The element to be stored in the node.
     */
    public DoubleNode(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return The element stored in the node.
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param element The element to be stored in the node.
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the next node in the list.
     *
     * @return The next node in the list.
     */
    public DoubleNode<T> getNext() {
        return next;
    }

    /**
     * Sets the next node in the list.
     *
     * @param next The next node to be referenced.
     */
    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the previous node in the list.
     *
     * @return The previous node in the list.
     */
    public DoubleNode<T> getPrevious() {
        return previous;
    }

    /**
     * Sets the previous node in the list.
     *
     * @param previous The previous node to be referenced.
     */
    public void setPrevious(DoubleNode<T> previous) {
        this.previous = previous;
    }
}
