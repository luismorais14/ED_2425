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
     * Adds one element to the rear of this queue.
     *
     * @param element the element to be added to
     * the rear of this queue
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
     * Removes and returns the element at the front of
     * this queue.
     *
     * @return the element at the front of this queue
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        T removedItem = front.getElement();

        front = front.getNext();

        count--;

        return removedItem;
    }

    /**
     * Returns without removing the element at the front of
     * this queue.
     *
     * @return the first element in this queue
     */
    @Override
    public T first() {
        return front.getElement();
    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in this queue.
     *
     * @return the integer representation of the size
     * of this queue
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the string representation of this queue
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
