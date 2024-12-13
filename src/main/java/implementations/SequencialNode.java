package implementations;

public class SequencialNode<T> {
    private T element;
    private SequencialNode<T> next;
    private SequencialNode<T> previous;

    /**
     * Creates an empty node with no element and no links.
     */
    public SequencialNode() {
        this.element = null;
        this.next = null;
        this.previous = null;
    }

    /**
     * Creates a node with the specified element and no links.
     *
     * @param element the element to store in the node
     */
    public SequencialNode(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in the node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param element the element to store in the node
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Returns the next node in the sequence.
     *
     * @return the next node, or null if there is no next node
     */
    public SequencialNode<T> getNext() {
        return next;
    }

    /**
     * Sets the reference to the next node in the sequence.
     *
     * @param next the next node to link to
     */
    public void setNext(SequencialNode<T> next) {
        this.next = next;
    }

    /**
     * Returns the previous node in the sequence.
     *
     * @return the previous node, or null if there is no previous node
     */
    public SequencialNode<T> getPrevious() {
        return previous;
    }

    /**
     * Sets the reference to the previous node in the sequence.
     *
     * @param previous the previous node to link to
     */
    public void setPrevious(SequencialNode<T> previous) {
        this.previous = previous;
    }
}
