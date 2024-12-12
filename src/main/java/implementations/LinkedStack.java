package implementations;


import ADT.StackADT;
import Exceptions.*;

public class LinkedStack<T> implements StackADT<T> {
    private LinearNode<T> head;
    private int top;

    /**
     * Creates an empty stack using the default capacity.
     */
    public LinkedStack() {
        top = 0;
        head = null;
    }

    /**
     * Adds the specified element to the top of this stack,
     * expanding the capacity of the stack array if necessary.
     * @param element generic element to be pushed onto stack
     */
    @Override
    public void push(T element) {
        LinearNode<T> newNode = new LinearNode<T>(element);

        newNode.setNext(head);
        head = newNode;

        top++;
    }

    /**
     * Removes the element at the top of this stack and
     * returns a reference to it.
     * Throws an EmptyCollectionException if the stack is empty.
     *
     * @return T element removed from top of stack
     * @throws EmptyCollectionException if a pop
     *                                  is attempted on empty stack
     */
    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Stack is empty");

        T result = head.getElement();
        head = head.getNext();
        top--;

        return result;
    }

    /**
     * Returns a reference to the element at the top of this stack.
     * The element is not removed from the stack.
     * Throws an EmptyCollectionException if the stack is empty.
     *
     * @return T element on top of stack
     * @throws EmptyCollectionException if a
     *                                  peek is attempted on empty stack
     */
    @Override
    public T peek() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }
        return head.getElement();
    }

    /**
     * Verifies if the stack is empty or not
     *
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return (this.size() == 0);
    }

    /**
     * Returns the number of elements in the stack
     *
     * @return the number of elements in the stack the stack
     */
    @Override
    public int size() {
        return this.top;
    }


    /**
     * Returns a string representation of the elements in the sequence starting from this node.
     * The elements are separated by newlines.
     *
     * @return a string representation of the sequence
     */
    @Override
    public String toString() {
        String txt = "";
        LinearNode<T> current = head;
        
        while (current != null) {
            txt += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        
        return txt;
    }

}
