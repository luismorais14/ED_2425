package implementations;

import ADT.ListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayList<T> implements ListADT<T> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int INCREMENT_VALUE = 2;

    protected T[] array;
    protected int counter;
    protected int modCount;

    /**
     * Creates an empty list using the default capacity.
     */
    public ArrayList() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        counter = 0;
        modCount = 0;
    }

    /**
     * Creates an empty list using the specified array.
     * @param array the array of the list
     */
    public ArrayList(T[] array) {
        this.array = array;
        counter = array.length;
        modCount = 0;
    }

    /**
     * Removes and returns the first element in the list.
     * @return the removed element
     * @throws EmptyCollectionException exception to thrown if the list is empty
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T element = array[0];

        for (int i = 0; i < counter - 1; i++) {
            array[i] = array[i + 1];
        }

        array[counter - 1] = null;
        counter--;
        modCount++;
        return element;
    }

    /**
     * Removes and returns the last element in the list.
     * @return the removed element.
     * @throws EmptyCollectionException exception to thrown if the list is empty
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        counter--;
        T element = array[counter];
        array[counter] = null;
        modCount++;

        return element;
    }


    /**
     * Finds the element passed as argument.
     * @param element the element to be found
     * @return the index of the element in the list, -1 if the element was not found
     */
    protected int find(T element) {
        for (int i = 0; i < counter; i++) {
            if (array[i] != null && array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes and returns the specified element.
     * @param element the element to be removed from the list
     * @return the removed element
     * @throws EmptyCollectionException exception to thrown if the list is empty
     * @throws ElementNotFoundException exception to be thrown if the element was not found
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T removedElement = null;

        int index = find(element);

        if (index == -1) {
            throw new ElementNotFoundException("Element not found!");
        }
        
        for (int i = index; i < counter - 1; i++) {
            array[i] = array[i + 1];
        }

        array[--counter] = null;
        modCount++;
        
        return removedElement;
    }

    /**
     * Returns a reference to the element at the front of the list. The element is not removed from the list.
     * @return a reference to the element at the front of the list.
     * @throws EmptyCollectionException exception to thrown if the list is empty
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return array[0];
    }

    /**
     * Returns a reference to the element at the rear of the list. The element is not removed from the list.
     * @return Returns a reference to the element at the rear of the list.
     * @throws EmptyCollectionException exception to thrown if the list is empty
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return array[counter - 1];
    }

    /**
     * Returns true if this list contains the specified element.
     * @param target the target that is being sought in the list
     * @return true if the list contains the specified element, false otherwise
     */
    @Override
    public boolean contains(T target) {
        return find(target) != -1;
    }

    /**
     * Returns true if this list is empty and false otherwise.
     * @return true if this list is empty and false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    /**
     * Returns the number of elements currently in this list.
     * @return the number of elements currently in this list.
     */
    @Override
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator for the elements currently in this list.
     * @return an iterator for the elements currently in this list.
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    /**
     * Creates a new array to store the contents of the list with twice the capacity of the old one.
     */
    protected void expandCapacity() {
        T[] newArray = (T[]) new Object[array.length + INCREMENT_VALUE];

        for (int i = 0; i < counter; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    /**
     * Returns a string representation of this list.
     * @return a string representation of this list.
     */
    @Override
    public String toString() {
        String txt = "";
        for (int i = 0; i < counter; i++) {
            if (array[i] != null) {
                txt += array[i].toString() + "\n";
            }
        }
        return txt;
    }

    private class BasicIterator<E> implements Iterator<E> {
        private int expectedModCount;
        private int current;
        private boolean okToRemove;

        /**
         * Creates an empty iterator
         */
        public BasicIterator() {
            current = 0;
            expectedModCount = modCount;
            okToRemove = false;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return this.current < counter;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() throws ConcurrentModificationException, NoSuchElementException {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E element = (E) array[current];
            current++;

            okToRemove = true;

            return element ;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() throws ConcurrentModificationException, IllegalStateException, NoSuchElementException {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (!okToRemove) {
                throw new IllegalStateException("Next must be called before remove.");
            }

            try {
                ArrayList.this.remove(array[current]);
            } catch (ElementNotFoundException ex) {
                throw new NoSuchElementException();
            }

            expectedModCount++;
            okToRemove = false;
            current--;
        }
    }

}
