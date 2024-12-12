package implementations;

import ADT.OrderedListADT;
import Exceptions.NoComparableExeption;

public class DoubleLinkedOrderedList<T> extends DoubleLinkedList<T> implements OrderedListADT<T> {
    /**
     * Adds the specified element to this list at
     * the proper location
     *
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element)  {
        if (!(element instanceof Comparable)) {
            throw new NoComparableExeption("Element Not Comparable");
        }

        Comparable<T> comparable = (Comparable<T>) element;
        DoubleNode<T> newNode = new DoubleNode(comparable);

        if (head == null) {
            tail = newNode;
            head = newNode;
        } else {
            DoubleNode<T> current = head;

            if (comparable.compareTo(current.getElement()) >= 0) {
                newNode.setNext(current);
                current.setPrevious(newNode);
                head = newNode;
            } else {
                while (current.getNext() != null && comparable.compareTo(current.getNext().getElement()) > 0) {
                    current = current.getNext();
                }

                newNode.setNext(current.getNext());

                if (current.getNext() != null) {
                    current.getNext().setPrevious(newNode);
                } else {
                    tail = newNode;
                }
                newNode.setPrevious(current);
                current.setNext(newNode);
            }
            count++;
            modCount++;
        }
    }
}
