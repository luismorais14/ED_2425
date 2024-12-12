package implementations;

import ADT.SmackADT;
import Exceptions.EmptyCollectionException;

public class SmackStack<T> extends ArrayStack<T> implements SmackADT<T> {

    /**
     * Constructor calling the superclass constructor
     */
    public SmackStack() {
        super();
    }

    /**
     * Eliminates and returns the last element of the stack
     *
     * @return The last element of the stack
     */
    @Override
    public T smack() throws EmptyCollectionException {
        if (super.isEmpty()) {
            throw new EmptyCollectionException("Empty stack");
        }

        T element = stack[0];

        for (int i = 0; i < this.size() - 1; i++) {
            stack [i] = stack [i + 1];
        }

        stack[top - 1] = null;

        return element;
    }
}
