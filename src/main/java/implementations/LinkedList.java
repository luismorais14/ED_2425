package implementations;


import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class LinkedList<T> {
    private int counter;
    private LinearNode<T> head;
    private LinearNode<T> tail;

    /**
     * Default constructor
     */
    public LinkedList() {
        counter = 0;
        head = tail = null;
    }

    /**
     * Add a new element to the list
     *
     * @param data the element to be added
     */
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

    /**
     * Add a new element to the list
     *
     * @param element the element to be added
     * @throws EmptyCollectionException if the list is empty
     * @throws ElementNotFoundException if the element is not found
     */
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

    /**
     * Recursively constructs a string representation of a linked list starting from the given node.
     * The method traverses the list and appends the element of each node to the result string.
     *
     * @param node The node from which to start printing the list.
     * @return A string representing the elements of the linked list, each followed by a newline.
     */
    private String print(LinearNode<T> node) {
        String txt = "";

        if (node == null) {
            txt += "";
        } else {
            txt += node.getElement() + "\n" + print(node.getNext());
        }

        return txt;
    }

    /**
     * Prints the list to the standard output.
     */
    public void print() {
        System.out.println(print(head));
    }

    /**
     * Replaces an existing element in the list with a new element.
     *
     * @param existingElement the element to be replaced
     * @param newElement      the element to replace with
     * @throws EmptyCollectionException if the list is empty
     */
    public void replace(T existingElement, T newElement) throws EmptyCollectionException {
        if (head == null) {
            throw new EmptyCollectionException("Empty Collection");
        }
        replaceRecursive(existingElement, newElement, head);
    }

    /**
     * Recursively replaces an existing element in the list with a new element.
     *
     * @param existingElement the element to be replaced
     * @param newElement      the element to replace with
     * @param node            the current node being checked
     */
    public void replaceRecursive(T existingElement, T newElement, LinearNode<T> node) {
        if (node == null) {
            return;
        }

        if (node.getElement().equals(existingElement)) {
            node.setElement(newElement);
        }

        replaceRecursive(existingElement, newElement, node.getNext());
    }

    /**
     * Returns a string representation of the list.
     * Elements are displayed in order, separated by newlines.
     *
     * @return a string representation of the list
     */
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
