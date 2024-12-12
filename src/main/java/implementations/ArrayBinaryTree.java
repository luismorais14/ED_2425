package implementations;

import ADT.BinaryTreeADT;
import ADT.QueueADT;
import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class ArrayBinaryTree<T> implements BinaryTreeADT<T> {
    protected int count;
    protected T[] tree;
    private final int CAPACITY = 50;

    /**
     * Creates an empty binary tree.
     */
    public ArrayBinaryTree() {
        count = 0;
        tree = (T[]) new Object[CAPACITY];
    }

    /**
     * Creates a binary tree with the specified element as its root.
     *
     * @param element the element which will become the root
     *                of the new tree
     */
    public ArrayBinaryTree(T element) {
        count = 1;
        tree = (T[]) new Object[CAPACITY];
        tree[0] = element;
    }

    /**
     * Returns a reference to the root element
     *
     * @return a reference to the root
     */
    @Override
    public T getRoot() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Empty tree");
        }

        return tree[0];
    }

    /**
     * Returns true if this binary tree is empty and false otherwise.
     *
     * @return true if this binary tree is empty
     */
    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * Returns the number of elements in this binary tree.
     *
     * @return the integer number of elements in this tree
     */
    @Override
    public int size() {
        return count;
    }

    /**
     * Returns true if the binary tree contains an element that
     * matches the specified element and false otherwise.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the tree contains the target element
     */
    @Override
    public boolean contains(T targetElement) {
        T temp;
        boolean found = false;

        try {
            temp = find(targetElement);
            found = true;
        } catch (ElementNotFoundException ex) {
            found = false;
        }

        return found;
    }

    /**
     * Returns a reference to the specified target element if it is
     * found in this binary tree. Throws a NoSuchElementException if
     * the specified target element is not found in the binary tree.
     *
     * @param targetElement the element being sought in the tree
     * @return true if the element is in the tree
     * @throws ElementNotFoundException if an element not found
     *                                  exception occurs
     */
    public T find(T targetElement) throws ElementNotFoundException {
        T temp = null;
        boolean found = false;

        for (int ct = 0; ct < count && !found; ct++)
            if (targetElement.equals(tree[ct])) {
                found = true;
                temp = tree[ct];
            }
        if (!found)
            throw new ElementNotFoundException("binary tree");

        return temp;
    }

    /**
     * Performs a recursive inorder traversal.
     *
     * @param node     the node used in the traversal
     * @param templist the temporary list used in the traversal
     */
    private void inorder(int node, ArrayUnorderedList<T> templist) {
        if (node < tree.length)
            if (tree[node] != null) {
                inorder(node * 2 + 1, templist);
                templist.addToRear(tree[node]);
                inorder((node + 1) * 2, templist);
            }
    }

    /**
     * Performs an inorder traversal on this binary tree by
     * calling an overloaded, recursive inorder method
     * that starts with the root.
     *
     * @return an iterator over the binary tree
     */
    public Iterator<T> iteratorInOrder() {
        ArrayUnorderedList<T> templist = new ArrayUnorderedList<T>();
        inorder(0, templist);
        return templist.iterator();
    }

    /**
     * Performs a recursive preorder traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    private void preorder(int node, UnorderedListADT<T> tempList) {
        if (node < tree.length) {
            if (tree[node] != null) {
                tempList.addToRear(tree[node]);
                preorder(node * 2 + 1, tempList);
                preorder((node + 1) * 2, tempList);
            }
        }
    }

    /**
     * Performs a preorder traversal on this binary tree by calling an
     * overloaded, recursive preorder method that starts
     * with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorPreOrder() {
        UnorderedListADT<T> tempList = new ArrayUnorderedList<T>();
        preorder(0, tempList);

        return tempList.iterator();
    }

    /**
     * Performs a recursive postorder traversal.
     *
     * @param node     the node to be used as the root
     *                 for this traversal
     * @param tempList the temporary list for use in this traversal
     */
    private void postorder(int node, UnorderedListADT<T> tempList) {
        if (node < tree.length) {
            if (tree[node] != null) {
                preorder(node * 2 + 1, tempList);
                preorder((node + 1) * 2, tempList);
                tempList.addToRear(tree[node]);
            }
        }
    }

    /**
     * Performs a postorder traversal on this binary tree by
     * calling an overloaded, recursive postorder
     * method that starts with the root.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorPostOrder() {
        UnorderedListADT<T> tempList = new ArrayUnorderedList<T>();
        postorder(0, tempList);
        return tempList.iterator();
    }

    /**
     * Performs a recursive levelorder traversal.
     *
     * @param tempList the temporary list for use in this traversal
     */
    private void levelOrder(UnorderedListADT<T> tempList) {
        int node = 0;

        if (count != 0 && tree[node] != null) {
            QueueADT<Integer> queue = new CircularArrayQueue<Integer>();
            queue.enqueue(node);

            while (!queue.isEmpty()) {
                node = queue.dequeue();

                tempList.addToRear(tree[node]);

                if (tempList.size() != tree.length) {
                    if (tree[node * 2 + 1] != null) {
                        queue.enqueue(node * 2 + 1);
                    }

                    if (tree[(node + 1) * 2] != null) {
                        queue.enqueue((node + 1) * 2);
                    }
                }
            }
        }
    }


    /**
     * Performs a levelorder traversal on the binary tree,
     * using a queue.
     *
     * @return an iterator over the elements of this binary tree
     */
    @Override
    public Iterator<T> iteratorLevelOrder() {
        UnorderedListADT<T> tempList = new ArrayUnorderedList<T>();
        levelOrder(tempList);

        return tempList.iterator();
    }

}
