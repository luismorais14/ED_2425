package implementations;

import ADT.QueueADT;
import Exceptions.EmptyCollectionException;

public class CircularArrayQueue<T> implements QueueADT<T> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int INCREMENT_FACTOR = 2;

    private int front, rear;
    private int count;
    private T[] queue;

    /**
     * Default constructor that initializes an empty queue.
     * The queue has an initial capacity and starts with both front and rear pointers at 0.
     */
    public CircularArrayQueue() {
        count = 0;
        front = rear = 0;
        queue = (T[]) (new Object[INITIAL_CAPACITY]);
    }

    /**
     * Expands the capacity of the queue when it is full.
     * The new capacity is double the current capacity.
     * Elements are re-arranged in the new array starting from the front of the queue.
     */
    private void expandCapacity() {
        T[] newArray = (T[]) (new Object[queue.length * INCREMENT_FACTOR]);
        int i;
        for (i = 0; i < queue.length; i++) {
            if (queue[front + i] != null) {
                newArray[i] = queue[front + i];
            }
        }
        front = 0;
        rear = i;
        queue = newArray;
    }

    /**
     * Adds an element to the rear of the queue.
     * If the queue is full, the capacity is expanded before adding the new element.
     *
     * @param element The element to be added to the queue.
     */
    @Override
    public void enqueue(T element) {
        if (count == queue.length) {
            expandCapacity();
        }

        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return The element at the front of the queue.
     * @throws EmptyCollectionException If the queue is empty.
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        T element = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;

        count--;

        return element;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return The element at the front of the queue.
     * @throws EmptyCollectionException If the queue is empty.
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        return queue[front];
    }

    /**
     * Checks whether the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return The size of the queue.
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of the elements in the queue.
     * Each element is followed by a newline.
     *
     * @return A string representing the elements in the queue.
     */
    @Override
    public String toString() {
        String txt = "";

        for (int i = 0; i < size(); i++) {
            if (queue[front + i] != null) {
                txt += queue[front + i].toString() + "\n";
            }
        }
        return txt;
    }
}
