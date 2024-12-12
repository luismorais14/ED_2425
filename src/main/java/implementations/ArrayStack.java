package implementations;

import ADT.SmackStackADT;
import Exceptions.EmptyCollectionException;

public class ArrayStack<T> implements SmackStackADT<T> {
    private static final int DEFAULT_CAPACITY = 100;
    private static final int CAPACITY_INCREMENT = 2;

    protected int top;
    protected T[] stack;

    /**
     * Creates an empty stack using the default capacity.
     */
    public ArrayStack() {
        top = 0;
        stack = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    /**
     * Creates an empty stack using the specified capacity.
     *
     * @param initialCapacity represents the specified capacity
     */
    public ArrayStack(int initialCapacity) {
        top = 0;
        stack = (T[]) (new Object[initialCapacity]);
    }

    private void expandCapacity() {
        T[] newArray = (T[]) (new Object[this.stack.length * CAPACITY_INCREMENT]);
        for (int i = 0; i < this.stack.length; i++) {
            newArray[i] = this.stack[i];
        }

        this.stack = newArray;
    }

    /**
     * Adds the specified element to the top of this stack,
     * expanding the capacity of the stack array if necessary.
     *
     * @param element generic element to be pushed onto stack
     */
    @Override
    public void push(T element) {
        if (size() == stack.length) {
            expandCapacity();
        }

        stack[top++] = element;
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
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack");
        }

        top--;
        T result = stack[top];
        stack[top] = null;

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

        return stack[top - 1];
    }

    /**
     * Verifies if the stack is empty or not
     *
     * @return true if the stack is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.top == 0;
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

    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < top; i++) {
            if (stack[i] != null) {
                txt += stack[i].toString() + "\n";
            }
        }

        return txt;
    }
}
