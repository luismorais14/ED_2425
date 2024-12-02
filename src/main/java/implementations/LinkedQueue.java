package implementations;

import ADT.QueueADT;
import Exceptions.EmptyCollectionException;

public class LinkedQueue<T> implements QueueADT<T> {
    private LinearNode<T> front, rear;
    private int count;

    public LinkedQueue() {
        front = rear = null;
        count = 0;
    }

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

    @Override
    public T first() {
        return front.getElement();
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

        LinearNode<T> current = front;

        while (current != null) {
            txt += current.getElement().toString() + "\n";
            current = current.getNext();
        }

        return txt;
    }
}
