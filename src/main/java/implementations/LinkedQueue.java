package implementations;

import ADT.QueueADT;
import Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private LinearNode<T> front, rear;
    private int count;

    /**
     * Creates an empty queue.
     */
    public LinkedQueue() {
        front = rear = null;
        count = 0;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param element the element to be added to the queue
     */
    @Override
    public void enqueue(T element) {
        LinearNode<T> newNode = new LinearNode<>(element);

        if (isEmpty()) {
            front = newNode;
        } else {
            rear.setNext(newNode);
        }
        rear = newNode;

        count++;
    }

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the element removed from the front of the queue
     * @throws EmptyCollectionException if the queue is empty
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        T removedItem = front.getElement();

        front = front.getNext();

        count--;

        return removedItem;
    }

    /**
     * Returns the element at the front of the queue without removing it.
     *
     * @return the element at the front of the queue
     */
    @Override
    public T first() {
        return front.getElement();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in the queue.
     *
     * @return the size of the queue
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of the queue, showing all elements
     * from front to rear, separated by newlines.
     *
     * @return a string representation of the queue
     */
    @Override
    public String toString() {
        String txt = "";

        LinearNode<T> current = front;

        while (current != null) {
            txt += current.getElement().toString() + "\n";
            current = current.getNext();
        }

        return txt;
    }
}
