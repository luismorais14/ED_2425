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

    /**
     * Removes the last node from the list.
     *
     * @throws EmptyCollectionException if the list is empty
     */
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

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        if (head == null && tail == null) {
            return true;
        }
        return false;
    }

    /**
     * Returns an array of all elements in the list.
     *
     * @return an array containing all elements in the list
     */
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

    /**
     * Returns an array of elements up to the specified position.
     *
     * @param position the position up to which elements are retrieved
     * @return an array containing elements up to the specified position
     */
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

    /**
     * Returns an array of elements after the specified position.
     *
     * @param position the position after which elements are retrieved
     * @return an array containing elements after the specified position
     */
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

    /**
     * Returns an array of elements between two specified positions.
     *
     * @param first  the starting position (exclusive)
     * @param second the ending position (exclusive)
     * @return an array containing elements between the specified positions
     */
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

    /**
     * Recursively prints elements of the list starting from the head.
     *
     * @param node the node to start printing from
     * @return a string representation of the list
     */
    private String printFromHead(SequencialNode<T> node) {
        String txt = "";

        if (node == null) {
            txt += "";
        } else {
            txt += node.getElement().toString() + "\n" + printFromHead(node.getNext());
        }
        return txt;
    }

    /**
     * Recursively prints elements of the list starting from the tail.
     *
     * @param node the node to start printing from
     * @return a string representation of the list
     */
    private String printFromTail(SequencialNode<T> node) {
        String txt = "";

        if (node == null) {
            txt += "";
        } else {
            txt += node.getElement().toString() + "\n" + printFromTail(node.getPrevious());
        }
        return txt;
    }

    /**
     * Prints the list from head to tail.
     */
    public void printFromHead() {
        System.out.println(printFromHead(head));
    }

    /**
     * Prints the list from tail to head.
     */
    public void printFromTail() {
        System.out.println(printFromTail(tail));
    }

    /**
     * Recursively collects elements in reverse order.
     *
     * @param node     the current node
     * @param elements the array to store elements
     * @param count    the current index in the array
     * @return the array with elements in reverse order
     */
    private T[] returnInvertedElements(SequencialNode<T> node, T[] elements, int count) {
        if (node == null) {
            return elements;
        }

        elements[count] = node.getElement();
        return returnInvertedElements(node.getPrevious(), elements, count + 1);
    }

    /**
     * Prints the elements of the list in reverse order.
     */
    public void printInvertedElements() {
        T[] elements = (T[]) new Object[counter];
        elements = returnInvertedElements(tail, elements, 0);

        System.out.println(Arrays.toString(elements));
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * Returns a string representation of the list.
     *
     * @return a string containing all elements of the list
     */
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
