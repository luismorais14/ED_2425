package implementations;

import Exceptions.EmptyCollectionException;

import java.util.ArrayList;
import java.util.Arrays;

public class DoublyLinkedList<T> {
    private final int INITIAL_SIZE = 5;

    private int counter;
    private SequencialNode<T> head, tail;

    /**
     * Creates an empty doubly linked list.
     */
    public DoublyLinkedList() {
        counter = 0;
        head = tail = null;
    }


    /**
     * Adds a new element to the beginning of the list.
     *
     * @param element the element to be added
     */
    public void add(T element) {
        SequencialNode<T> newNode = new SequencialNode<T>(element);

        if (head == null) {
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
        }
        head = newNode;

        counter++;
    }

    /**
     * Removes the first node from the list.
     *
     * @throws EmptyCollectionException if the list is empty
     */
    public void removeFirstNode() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }

        if (counter == 1) {
            head = tail = null;
        } else {
            head = head.getNext();
        }
        head.setPrevious(null);
        counter--;
    }

    public void removeLastNode() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty List");
        }
        if (counter == 1) {
            head = tail = null;
        } else {
            tail = tail.getPrevious();
        }
        tail.setNext(null);
        counter--;
    }

    public boolean isEmpty() {
        if (head == null && tail == null) {
            return true;
        }
        return false;
    }

    public T[] elementsArray() {
        SequencialNode<T> current = head;
        ArrayList<T> array = new ArrayList<>(INITIAL_SIZE);

        while (current != null) {
            array.add(current.getElement());
            current = current.getNext();
        }
        T[] finalArray = (T[]) new Object[array.size()];
        finalArray = array.toArray(finalArray);

        return finalArray;
    }

    public T[] elementsUntilPosition(int position) {
        SequencialNode<T> current = head;
        int counter = 0;
        ArrayList<T> array = new ArrayList<>(INITIAL_SIZE);

        while (current != null && counter != position) {
            array.add(current.getElement());
            current = current.getNext();
            counter++;
        }
        T[] finalArray = (T[]) new Object[array.size()];
        finalArray = array.toArray(finalArray);

        return finalArray;
    }

    public T[] elementsAfterPosition(int position) {
        int counter = 0;
        SequencialNode<T> current = head;
        ArrayList<T> array = new ArrayList<>(INITIAL_SIZE);

        while (current != null) {
            if (counter > position) {
                array.add(current.getElement());
            }
            current = current.getNext();
            counter++;
        }
        T[] finalArray = (T[]) new Object[array.size()];
        finalArray = array.toArray(finalArray);

        return finalArray;
    }

    public T[] elementsBetweenPositions(int first, int second) {
        int counter = 0;
        SequencialNode<T> current = head;
        ArrayList<T> array = new ArrayList<>(INITIAL_SIZE);

        while (current != null) {
            if (counter > first && counter < second) {
                array.add(current.getElement());
            }
            current = current.getNext();
            counter++;
        }
        T[] finalArray = (T[]) new Object[array.size()];
        finalArray = array.toArray(finalArray);

        return finalArray;
    }

    private String printFromHead(SequencialNode<T> node) {
        String txt = "";

        if (node == null) {
            txt += "";
        } else {
            txt += node.getElement().toString() + "\n" + printFromHead(node.getNext());
        }
        return txt;
    }

    private String printFromTail(SequencialNode<T> node) {
        String txt = "";

        if (node == null) {
            txt += "";
        } else {
            txt += node.getElement().toString() + "\n" + printFromTail(node.getPrevious());
        }
        return txt;
    }

    public void printFromHead() {
        System.out.println(printFromHead(head));
    }

    public void printFromTail() {
        System.out.println(printFromTail(tail));
    }

    private T[] returnInvertedElements(SequencialNode<T> node, T[] elements, int count) {
        if (node == null) {
            return elements;
        }

        elements[count] = node.getElement();
        return returnInvertedElements(node.getPrevious(), elements, count + 1);
    }

    public void printInvertedElements() {
        T[] elements = (T[]) new Object[counter];
        elements = returnInvertedElements(tail, elements, 0);

        System.out.println(Arrays.toString(elements));
    }


    public int getCounter() {
        return this.counter;
    }

    @Override
    public String toString() {
        String txt = "";
        SequencialNode<T> current = head;

        while (current != null) {
            txt += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return txt;
    }

}
