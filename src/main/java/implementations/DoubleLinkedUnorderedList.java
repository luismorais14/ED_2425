package implementations;

import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class DoubleLinkedUnorderedList<T> extends DoubleLinkedList<T> implements UnorderedListADT<T> {
    /**
     * Adds the specified element to the front of this list.
     *
     * @param element the element to be added to the front of this list
     */
    @Override
    public void addToFront(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (head == null) {
            tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
        }
        head = newNode;
        head.setPrevious(null);
        modCount++;
        count++;
    }

    /**
     * Adds the specified element to the counter of this list.
     *
     * @param element the element to be added to the counter of this list
     */
    @Override
    public void addToRear(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (head == null) {
            head = newNode;
        } else {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
        }
        tail = newNode;
        tail.setNext(null);
        modCount++;
        count++;
    }

    /**
     * Adds the specified element after the specified target.
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element will be added after
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Collection");
        }

        DoubleNode<T> newNode = new DoubleNode<>(element);
        DoubleNode<T> current = head;
        boolean found = false;

        while (current != null && !found) {
            if (current.getElement().equals(target)) {
                found = true;
                if (current == tail) {
                    newNode.setPrevious(tail);
                    tail.setNext(newNode);
                    tail = newNode;
                } else {
                    newNode.setNext(current.getNext());
                    newNode.setPrevious(current);
                    current.getNext().setPrevious(newNode);
                    current.setNext(newNode);
                }
            } else {
                current = current.getNext();
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Element not found");
        }
        count++;
        modCount++;
    }
}
