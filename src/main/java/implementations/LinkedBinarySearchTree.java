package implementations;

import ADT.BinarySearchTreeADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {
    /**
     * Creates an empty binary search tree.
     */
    public LinkedBinarySearchTree() {
        super();
    }

    /**
     * Creates a binary search with the specified element as its root.
     *
     * @param element the element that will be the root of the new
     *                binary search tree
     */
    public LinkedBinarySearchTree(T element) {
        super(element);
    }

    /**
     * Adds the specified object to the binary search tree in the
     * appropriate position according to its key value. Note that
     * equal elements are added to the right.
     *
     * @param element the element to be added to the binary search
     *                tree
     */
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;
        if (isEmpty())
            root = temp;
        else {
            BinaryTreeNode<T> current = root;
            boolean added = false;
            while (!added) {
                if (comparableElement.compareTo(current.getElement()) < 0) {
                    if (current.getLeft() == null) {
                        current.setLeft(temp);
                        added = true;
                    } else
                        current = current.getLeft();
                } else {
                    if (current.getRight() == null) {
                        current.setRight(temp);
                        added = true;
                    } else
                        current = current.getRight();
                }
            }
        }
        count++;
    }

    /**
     * Removes the first element that matches the specified target
     * element from the binary search tree and returns a reference to
     * it. Throws a ElementNotFoundException if the specified target
     * element is not found in the binary search tree.
     *
     * @param targetElement the element being sought in the binary
     *                      search tree
     * @throws ElementNotFoundException if an element not found
     *                                  exception occurs
     */
    public T removeElement(T targetElement) throws ElementNotFoundException {
        T result = null;
        if (!isEmpty()) {
            if (((Comparable) targetElement).equals(root.getElement())) {
                result = root.getElement();
                root = replacement(root);
                count--;
            } else {
                BinaryTreeNode<T> current, parent = root;
                boolean found = false;
                if (((Comparable) targetElement).compareTo(root.getElement()) < 0)
                    current = root.getLeft();
                else
                    current = root.getRight();
                while (current != null && !found) {
                    if (targetElement.equals(current.getElement())) {
                        found = true;
                        count--;
                        result = current.getElement();
                        if (current == parent.getLeft()) {
                            parent.setLeft(replacement(current));
                        } else {
                            parent.setRight(replacement(current));
                        }
                    } else {
                        parent = current;
                        if (((Comparable) targetElement).compareTo(current.getElement()) < 0)
                            current = current.getLeft();
                        else
                            current = current.getRight();
                    }
                } //while
                if (!found)
                    throw new ElementNotFoundException("binary search tree");
            }
        } // end outer if
        return result;
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

        if (root.getLeft() == null) {
            result = root.getElement();
        } else {
            BinaryTreeNode<T> current = root.getLeft();

            while (current.getLeft() != null) {
                current = current.getLeft();
            }

            result = current.getElement();
        }

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

        if (root.getRight() == null) {
            result = root.getElement();
        } else {
            BinaryTreeNode<T> current = root.getRight();

            while (current.getRight() != null) {
                current = current.getRight();
            }

            result = current.getElement();
        }

        return result;
    }

    /**
     * Returns a reference to a node that will replace the one
     * specified for removal. In the case where the removed node has
     * two children, the inorder successor is used as its replacement.
     *
     * @param node the node to be removeed
     * @return a reference to the replacing node
     */
    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;
        if ((node.getLeft() == null) && (node.getRight() == null))
            result = null;
        else if ((node.getLeft() != null) && (node.getRight() == null))
            result = node.getLeft();
        else if ((node.getLeft() == null) && (node.getRight() != null))
            result = node.getRight();
        else {
            BinaryTreeNode<T> current = node.getRight();
            BinaryTreeNode<T> parent = node;
            while (current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            if (node.getRight() == current)
                current.setLeft(node.getLeft());
            else {
                parent.setLeft(current.getRight());
                current.setRight(node.getRight());
                current.setLeft(node.getLeft());
            }
            result = current;
        }
        return result;
    }
}
