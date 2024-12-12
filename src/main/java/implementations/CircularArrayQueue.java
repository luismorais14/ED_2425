package implementations;

import ADT.QueueADT;
import Exceptions.EmptyCollectionException;

public class CircularArrayQueue<T> implements QueueADT<T> {
    private static final int INITIAL_CAPACITY = 10;
    private static final int INCREMENT_FACTOR = 2;

    private int front, rear;
    private int count;
    private T[] queue;

    public CircularArrayQueue() {
        count = 0;
        front = rear = 0;
        queue = (T[]) (new Object[INITIAL_CAPACITY]);
    }

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

    @Override
    public void enqueue(T element) {
        if (count == queue.length) {
            expandCapacity();
        }

        queue[rear] = element;
        rear = (rear + 1) % queue.length;
        count++;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        T element = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;

        count--;

        return element;
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Queue");
        }

        return queue[front];
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

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
