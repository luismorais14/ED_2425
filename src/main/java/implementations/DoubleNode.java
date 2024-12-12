package implementations;

public class DoubleNode<T> {
    private T element;
    private DoubleNode<T> next;
    private DoubleNode<T> previous;

    public DoubleNode() {
        next = null;
        element = null;
        previous = null;
    }

    public DoubleNode(T element) {
        this.element = element;
        this.next = null;
        this.previous = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public DoubleNode<T> getNext() {
        return next;
    }

    public void setNext(DoubleNode<T> next) {
        this.next = next;
    }

    public DoubleNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(DoubleNode<T> previous) {
        this.previous = previous;
    }
}
