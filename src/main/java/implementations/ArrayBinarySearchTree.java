package implementations;

import ADT.BinarySearchTreeADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class ArrayBinarySearchTree<T> extends ArrayBinaryTree<T> implements BinarySearchTreeADT<T> {
    private final static int INCREMENT_VALUE = 2;

    protected int height;
    protected int maxIndex;


    /**
     * Creates an empty binary search tree.
     */
    public ArrayBinarySearchTree() {
        super();
        height = 0;
        maxIndex = -1;
    }

    /**
     * Creates a binary search with the specified element as its root
     *
     * @param element the element that will become the root of
     *                the new tree
     */
    public ArrayBinarySearchTree(T element) {
        super(element);
        height = 1;
        maxIndex = 0;
    }

    private void expandCapacity() {
        T[] newArray = (T[]) (new Object[tree.length * INCREMENT_VALUE]);

        for (int i = 0; i < count; i++) {
            newArray[i] = tree[i];
        }

        tree = newArray;
    }

    /**
     * Adds the specified object to this binary search tree in the
     * appropriate position according to its key value. Note that
     * equal elements are added to the right. Also note that the
     * index of the left child of the current index can be found by
     * doubling the current index and adding 1. Finding the index
     * of the right child can be calculated by doubling the current
     * index and adding 2.
     *
     * @param element the element to be added to the search tree
     */
    public void addElement(T element) {
        if (tree.length < maxIndex*2+3)
            expandCapacity();

        Comparable<T> tempelement = (Comparable<T>)element;

        if (isEmpty()) {
            tree[0] = element;
            maxIndex = 0;
        }
        else
        {
            boolean added = false;
            int currentIndex = 0;

            while (!added)
            {
                if (tempelement.compareTo((tree[currentIndex]) ) < 0)
                {
                    // go left
                    if (tree[currentIndex*2+1] == null)
                    {
                        tree[currentIndex*2+1] = element;
                        added = true;
                        if (currentIndex*2+1 > maxIndex)
                            maxIndex = currentIndex*2+1;
                    }
                    else
                        currentIndex = currentIndex*2+1;
                }
                else {
                    // go right
                    if (tree[currentIndex*2+2] == null)
                    {
                        tree[currentIndex*2+2] = element;
                        added = true;
                        if (currentIndex*2+2 > maxIndex)
                            maxIndex = currentIndex*2+2;
                    }
                    else
                        currentIndex = currentIndex*2+2;
                }

            }
        }

        height = (int)(Math.log(maxIndex + 1) / Math.log(2)) + 1;
        count++;
    }

    private int findIndex(Comparable<T> element, int start) {
        for (int i = start; i < tree.length; i++) {
            if (tree[i] != null && element.compareTo((tree[i])) == 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Removes and returns the specified element from this tree.
     *
     * @param targetElement the element to be removed from this tree
     * @return the element removed from this tree
     */
    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {
        if (isEmpty()) {
            throw new ElementNotFoundException("binary search tree");
        }

        Comparable<T> tempElement = (Comparable<T>) targetElement;

        int targetIndex = findIndex(tempElement, 0);

        T result = tree[targetIndex];
        replace(targetIndex);
        count--;

        int temp = maxIndex;
        maxIndex = -1;
        for (int i = 0; i <= temp; i++) {
            if (tree[i] != null) {
                maxIndex = i;
            }
        }

        height = (int) (Math.log(maxIndex + 1) / Math.log(2)) + 1;

        return result;
    }

    /**
     * Removes the node specified for removal and shifts the tree array accordingly.
     *
     * @param targetIndex the node to be removed
     */
    protected void replace(int targetIndex) {
        int currentIndex, parentIndex, temp, oldIndex, newIndex;
        ArrayUnorderedList<Integer> oldlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> newlist = new ArrayUnorderedList<>();
        ArrayUnorderedList<Integer> templist = new ArrayUnorderedList<>();
        Iterator<Integer> oldIt, newIt;

        /**
         * if target node has no children
         */
        if ((targetIndex * 2 + 1 >= tree.length) || (targetIndex * 2 + 2 >= tree.length)) {
            tree[targetIndex] = null;
        } /**
         * if target node has no children
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] == null)) {
            tree[targetIndex] = null;
        } /**
         * if target node only has a left child
         */
        else if ((tree[targetIndex * 2 + 1] != null) && (tree[targetIndex * 2 + 2] == null)) {
            /**
             * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 1;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();
                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node only has a right child
         */
        else if ((tree[targetIndex * 2 + 1] == null) && (tree[targetIndex * 2 + 2] != null)) {
            /**
             * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
             */
            currentIndex = targetIndex * 2 + 2;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                newlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * fill oldlist
             */
            currentIndex = targetIndex;
            templist.addToRear(currentIndex);
            while (!templist.isEmpty()) {
                currentIndex = (templist.removeFirst());
                oldlist.addToRear(currentIndex);
                if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                    templist.addToRear(currentIndex * 2 + 1);
                    templist.addToRear(currentIndex * 2 + 2);
                }
            }

            /**
             * do replacement
             */
            oldIt = oldlist.iterator();
            newIt = newlist.iterator();
            while (newIt.hasNext()) {
                oldIndex = oldIt.next();

                newIndex = newIt.next();
                tree[oldIndex] = tree[newIndex];
                tree[newIndex] = null;
            }
        } /**
         * if target node has two children
         */
        else {
            currentIndex = targetIndex * 2 + 2;

            while (tree[currentIndex * 2 + 1] != null) {
                currentIndex = currentIndex * 2 + 1;
            }

            tree[targetIndex] = tree[currentIndex];

            /**
             * the index of the root of the subtree to be replaced
             */
            int currentRoot = currentIndex;

            /**
             * if currentIndex has a right child
             */
            if (tree[currentRoot * 2 + 2] != null) {
                /**
                 * fill newlist with indices of nodes that will replace the corresponding indices in oldlist
                 */
                currentIndex = currentRoot * 2 + 2;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    newlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * fill oldlist
                 */
                currentIndex = currentRoot;
                templist.addToRear(currentIndex);
                while (!templist.isEmpty()) {
                    currentIndex = (templist.removeFirst());
                    oldlist.addToRear(currentIndex);
                    if ((currentIndex * 2 + 2) <= (Math.pow(2, height) - 2)) {
                        templist.addToRear(currentIndex * 2 + 1);
                        templist.addToRear(currentIndex * 2 + 2);
                    }
                }

                /**
                 * do replacement
                 */
                oldIt = oldlist.iterator();
                newIt = newlist.iterator();
                while (newIt.hasNext()) {
                    oldIndex = oldIt.next();
                    newIndex = newIt.next();

                    tree[oldIndex] = tree[newIndex];
                    tree[newIndex] = null;
                }
            } else {
                tree[currentRoot] = null;
            }
        }
    }

    /**
     * Removes all occurences of the specified element from this tree.
     *
     * @param targetElement the element that the list will
     *                      have all instances of it removed
     */
    @Override
    public void removeAllOccurrences(T targetElement) throws ElementNotFoundException {
        removeElement(targetElement);

        while (true) {
            try {
                removeElement(targetElement);
            } catch (ElementNotFoundException e) {
                break;
            }
        }
    }

    /**
     * Removes and returns the smallest element from this tree.
     *
     * @return the smallest element from this tree.
     */
    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T result = findMin();

        try {
            removeElement(result);
        } catch (ElementNotFoundException e) {

        }

        return result;
    }

    /**
     * Removes and returns the largest element from this tree.
     *
     * @return the largest element from this tree
     */
    @Override
    public T removeMax() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T result = findMax();

        try {
            removeElement(result);
        } catch (ElementNotFoundException e) {}

        return result;
    }

    /**
     * Returns a reference to the smallest element in this tree.
     *
     * @return a reference to the smallest element in this tree
     */
    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T result = null;
        int index = 0;

        while (index * 2 + 1 <= maxIndex && tree[index * 2 + 1] != null) {
            index = index * 2 + 1;
        }

        result = tree[index];

        return result;
    }

    /**
     * Returns a reference to the largest element in this tree.
     *
     * @return a reference to the largest element in this tree
     */
    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty collection");
        }

        T result = null;
        int index = 0;

        while (index * 2 + 2 <= maxIndex && tree[index * 2 + 2] != null) {
            index = index * 2 + 2;
        }

        result = tree[index];

        return result;
    }
}

