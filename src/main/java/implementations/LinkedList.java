package implementations;

import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class LinkedList<T> {
    private int counter;
    private LinearNode<T> head;
    private LinearNode<T> tail;

    public LinkedList() {
        counter = 0;
        head = tail = null;
    }

    public void add(T data) {
        LinearNode<T> newNode = new LinearNode<T>(data);

        if (head == null) {
            tail = newNode;
        } else {
            newNode.setNext(head);
        }
        head = newNode;

        counter++;
    }

    public void remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (head == null) {
            throw new EmptyCollectionException("Empty List");
        }
        LinearNode<T> previous = null;
        LinearNode<T> current = head;

        boolean found = false;
        while (current != null && !found) {
            if (element.equals(current.getElement())) {
                found = true;
            } else {
                previous = current;
                current = current.getNext();
            }
        }
        if (!found) {
            throw new ElementNotFoundException("Element Not Found");
        }

        if (counter == 1) {
            head = tail = null;
        } else if (current.equals(head)) {
            head = current.getNext();
        } else if (current.equals(tail)) {
            tail = previous;
            tail.setNext(null);
        } else {
            previous.setNext(current.getNext());
        }

        counter--;
    }

    @Override
    public String toString() {
        String text = "";
        LinearNode<T> current = head;

        while (current != null) {
            text += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return text;
    }
}
