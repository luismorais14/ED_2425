package implementations;

import ADT.OrderedListADT;
import Exceptions.NoComparableExeption;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    /**
     * Adds the specified element to this list at
     * the proper location
     *
     * @throws NoComparableExeption no comparable exeption
     * @param element the element to be added to this list
     */
    @Override
    public void add(T element) throws NoComparableExeption {
        if (!(element instanceof Comparable)) {
            throw new NoComparableExeption("Element Not Comparable");
        }

        Comparable<T> comparable = (Comparable<T>) element;

        if (counter == 0) {
            array[counter] = element;
        } else {
            if (counter == array.length) {
                expandCapacity();
            }

            int i = 0;

            while (i < size() && comparable.compareTo(array[i]) < 0) {
                i++;
            }

            for (int j = size(); j > i; j--) {
                array[j] = array[j - 1];
            }

            array[i] = element;
        }
        counter++;
        modCount++;
    }
}
