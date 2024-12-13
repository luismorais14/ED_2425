package implementations;

import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {
    public ArrayUnorderedList() {
        super();
    }

    /**
     * Constructs an `ArrayUnorderedList` with the specified array of elements.
     * This constructor initializes the list with the elements from the provided array.
     * It calls the superclass constructor to set up the underlying array for the list.
     *
     * @param array The array of elements to initialize the unordered list with the elements from this array are copied into the list.
     */
    public ArrayUnorderedList(T[] array) {
        super(array);
    }

    /**
     * Adds the specified element to the front of this list.
     *
     * @param element the element to be added to the front of this list
     */
    @Override
    public void addToFront(T element) {
        if (counter == array.length - 1) {
            expandCapacity();
        }

        for (int i = counter; i >= 0; i--) {
            array[i + 1] = array[i];
        }

        array[0] = element;
        counter++;
        super.modCount++;
    }

    /**
     * Adds the specified element to the counter of this list.
     *
     * @param element the element to be added to the counter of this list
     */
    @Override
    public void addToRear(T element) {
        if (counter == array.length - 1) {
            expandCapacity();
        }

        array[counter] = element;
        counter++;
        super.modCount++;
    }

    /**
     * Adds the specified element after the specified target.
     *
     * @param element the element to be added after the target
     * @param target  the target is the item that the element will be added after
     */
    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        if (counter == array.length - 1) {
            expandCapacity();
        }

        int index = find(target);

        if (index == -1) {
            throw new ElementNotFoundException("Element Not found");
        }

        for (int j = counter; j > index; j--) {
            array[j] = array[j + 1];
        }

        array[index + 1] = element;
        counter++;
        super.modCount++;
    }
}
