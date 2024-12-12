package implementations;

import ADT.HeapADT;
import Exceptions.EmptyCollectionException;

public class ArrayHeap<T> extends ArrayBinaryTree<T> implements HeapADT<T> {
    private final static int INCREMENT_VALUE = 2;

    /**
     * Constructs an empty heap with an initial capacity.
     * This constructor calls the superclass constructor to initialize the heap's internal tree array.
     */
    public ArrayHeap() {
        super();
    }

    /**
     * Expands the capacity of the heap's internal tree array by creating a new array
     * with a larger size. The size of the new array is determined by multiplying the
     * current array size by the `INCREMENT_VALUE`. The elements from the current tree
     * array are then copied to the new array.
     */
    private void expandCapacity() {
        T[] newTree = (T[]) (new Object[super.tree.length * INCREMENT_VALUE]);

        if (super.count >= 0) {
            System.arraycopy(super.tree, 0, newTree, 0, super.count);
        }

        super.tree = newTree;
    }

    /**
     * Adds the specified element to this heap in the appropriate
     * position according to its key value.
     * Note that equal elements are added to the right.
     *
     * @param obj the element to be added to this heap
     */
    public void addElement(T obj) {
        if (count == tree.length)
            expandCapacity();
        tree[count] = obj;
        count++;
        if (count > 1)
            heapifyAdd();
    }

    /**
     * Reorders this heap to maintain the ordering property after
     * adding a node.
     */
    private void heapifyAdd() {
        T temp;
        int next = count - 1;

        temp = tree[next];

        while ((next != 0) && (((Comparable) temp).compareTo
                (tree[(next - 1) / 2]) < 0)) {
            tree[next] = tree[(next - 1) / 2];
            next = (next - 1) / 2;
        }
        tree[next] = temp;
    }

    /**
     * Remove the element with the lowest value in this heap and
     * returns a reference to it.
     * Throws an EmptyCollectionException if the heap is empty.
     *
     * @return a reference to the element with the
     * lowest value in this head
     * @throws EmptyCollectionException if an empty collection
     *                                  exception occurs
     */
    public T removeMin() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Empty Heap");
        T minElement = tree[0];
        tree[0] = tree[count - 1];
        heapifyRemove();
        count--;

        return minElement;
    }

    /**
     * Returns a reference to the element with the lowest value in
     * this heap.
     *
     * @return a reference to the element with the lowest value
     * in this heap
     */
    @Override
    public T findMin() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty Heap");
        }

        return super.tree[0];
    }

    /**
     * Reorders this heap to maintain the ordering property.
     */
    private void heapifyRemove() {
        T temp;
        int node = 0;
        int left = 1;
        int right = 2;
        int next;

        if ((tree[left] == null) && (tree[right] == null))
            next = count;
        else if (tree[left] == null)
            next = right;
        else if (tree[right] == null)
            next = left;
        else if (((Comparable) tree[left]).compareTo(tree[right]) < 0)
            next = left;
        else
            next = right;
        temp = tree[node];
        while ((next < count) && (((Comparable) tree[next]).compareTo
                (temp) < 0)) {
            tree[node] = tree[next];
            node = next;
            left = 2 * node + 1;
            right = 2 * (node + 1);
            if ((tree[left] == null) && (tree[right] == null))
                next = count;
            else if (tree[left] == null)
                next = right;
            else if (tree[right] == null)
                next = left;
            else if (((Comparable) tree[left]).compareTo(tree[right]) < 0)
                next = left;
            else
                next = right;
        }
        tree[node] = temp;
    }
}
