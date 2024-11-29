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
    protected static int modCount;

    public ArrayList() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        counter = 0;
        modCount = 0;
    }

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

    protected int find(T element) {
        for (int i = 0; i < counter - 1; i++) {
            if (array[i] != null && array[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

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

    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return array[0];
    }

    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return array[counter - 1];
    }

    @Override
    public boolean contains(T target) {
        return find(target) != -1;
    }

    @Override
    public boolean isEmpty() {
        return counter == 0;
    }

    @Override
    public int size() {
        return counter;
    }

    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    protected void expandCapacity() {
        T[] newArray = (T[]) new Object[array.length + INCREMENT_VALUE];

        for (int i = 0; i < counter; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

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
