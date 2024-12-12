package implementations;

public class SequencialNode<T> {
    private T element;
    private SequencialNode<T> next;
    private SequencialNode<T> previous;

    public SequencialNode() {
        this.element = null;
        this.next = null;
        this.previous = null;
    }

    public SequencialNode(T element) {
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

    public SequencialNode<T> getNext() {
        return next;
    }

    public void setNext(SequencialNode<T> next) {
        this.next = next;
    }

    public SequencialNode<T> getPrevious() {
        return previous;
    }

    public void setPrevious(SequencialNode<T> previous) {
        this.previous = previous;
    }
}
