package implementations;

public class LinearNode<T> {
    private T element;
    private LinearNode<T> next;

    public LinearNode() {
        element = null;
        next = null;
    }

    public LinearNode(T element) {
        this.element = element;
        next = null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public LinearNode<T> getNext() {
        return next;
    }

    public void setNext(LinearNode<T> next) {
        this.next = next;
    }
}
