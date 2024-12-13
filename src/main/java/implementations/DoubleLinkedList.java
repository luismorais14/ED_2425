package implementations;

import ADT.ListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedList<T> implements ListADT<T> {
    protected DoubleNode<T> head, tail;
    protected int count;
    protected static int modCount;

    /**
     * Default constructor that initializes an empty list.
     * Sets head and tail to null, and count to 0.
     */
    public DoubleLinkedList() {
        head = tail = null;
        count = 0;
    }

    /**
     * Removes and returns the first element from the list.
     *
     * @return The first element in the list.
     * @throws EmptyCollectionException If the list is empty.
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }
        T element = null;

        if (count == 1) {
            head = tail = null;
        } else {
            element = head.getElement();

            head = head.getNext();
        }

        head.setPrevious(null);

        count--;
        modCount++;

        return element;
    }

    /**
     * Removes and returns the last element from the list.
     *
     * @return The last element in the list.
     * @throws EmptyCollectionException If the list is empty.
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T element = null;

        if (count == 1) {
            head = tail = null;
        } else {
            element = tail.getElement();
            tail = tail.getPrevious();
        }

        tail.setNext(null);

        count--;
        modCount++;

        return element;
    }

    /**
     * Removes the specified element from the list.
     *
     * @param element The element to be removed.
     * @return The removed element.
     * @throws EmptyCollectionException If the list is empty.
     * @throws ElementNotFoundException If the element is not found in the list.
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T removedElement = null;
        boolean found = false;

        DoubleNode<T> previous = null;
        DoubleNode<T> current = head;

        while (current != null && !found) {
            if (current.getElement().equals(element)) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Element not found");
        }

        if (count == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = head.getNext();
        } else if (current.equals(tail)) {
            tail = tail.getPrevious();
            tail.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }

        count--;
        modCount++;

        return removedElement;
    }

    /**
     * Returns the first element of the list without removing it.
     *
     * @return The first element in the list.
     * @throws EmptyCollectionException If the list is empty.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return head.getElement();
    }

    /**
     * Returns the last element of the list without removing it.
     *
     * @return The last element in the list.
     * @throws EmptyCollectionException If the list is empty.
     */
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        return tail.getElement();
    }

    /**
     * Checks whether the list contains a specific element.
     *
     * @param target The element to check for in the list.
     * @return True if the element is in the list, false otherwise.
     * @throws EmptyCollectionException If the list is empty.
     */
    @Override
    public boolean contains(T target) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        DoubleNode<T> previous = null;
        DoubleNode<T> current = head;
        boolean found = false;

        while (current != null && !found) {
            if (current.getElement().equals(target)) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }

        return found;
    }

    /**
     * Checks if the list is empty.
     *
     * @return True if the list is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return The size of the list.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns an iterator for the list.
     *
     * @return An iterator for the list.
     */
    @Override
    public Iterator<T> iterator() {
        return new BasicIterator<>();
    }

    /**
     * Returns a string representation of the list.
     *
     * @return A string containing all elements in the list, one per line.
     */
    @Override
    public String toString() {
        String txt = "";

        DoubleNode<T> current = head;

        while (current != null) {
            txt += current.getElement().toString() + "\n";
            current = current.getNext();
        }

        return txt;
    }

    /**
     * A basic iterator for the doubly linked list.
     *
     * @param <E> The type of elements the iterator will iterate over.
     */
    private class BasicIterator<E> implements Iterator<E> {
        private int expectedModCount;
        private DoubleNode<E> current;
        private boolean okToRemove;
        private int cursor;

        /**
         * Constructor for BasicIterator.
         * Initializes the iterator with the head of the list and sets the expected modification count.
         */
        public BasicIterator() {
            this.current = (DoubleNode<E>) head;
            expectedModCount = modCount;
            okToRemove = false;
            cursor = 0;
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
            return current.getNext() != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public E next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            } else if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E element = current.getElement();
            okToRemove = true;
            current = current.getNext();
            cursor++;

            return element;
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
        public void remove() throws UnsupportedOperationException {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            } else if (!okToRemove) {
                throw new IllegalStateException("Next must be called before remove.");
            }

            DoubleLinkedList.this.removeLast();

            cursor--;
            expectedModCount++;
            okToRemove = false;
        }
    }
}
