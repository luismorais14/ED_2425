package implementations;

import ADT.GraphADT;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    private final static int DEFAULT_CAPACITY = 20;
    private final static int RESIZE_FACTOR = 2;

    protected boolean[][] adjMatrix;
    protected T[] vertex;
    protected int numVertices;

    /**
     * Creates and empty graph
     */
    public Graph() {
        this.vertex = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        initializeAdjMatrix();
        this.numVertices = 0;
    }

    /**
     * Creates a graph specifying the inicial capacity
     *
     * @param initialCapacity the inicial capacity
     */
    public Graph(int initialCapacity) {
        this.vertex = (T[]) (new Object[initialCapacity]);
        this.adjMatrix = new boolean[initialCapacity][initialCapacity];
        initializeAdjMatrix();
        this.numVertices = 0;
    }

    /**
     * Initializes the adjacency matrix with all values false
     */
    private void initializeAdjMatrix() {
        for (int i = 0; i < this.vertex.length; i++) {
            for (int j = 0; j < this.vertex.length; j++) {
                this.adjMatrix[i][j] = false;
            }
        }
    }

    /**
     * Increases the capacity of the vertex array
     */
    private void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[this.vertex.length * RESIZE_FACTOR]);
        boolean[][] largerAdjMatrix = new boolean[this.vertex.length * RESIZE_FACTOR][this.vertex.length * RESIZE_FACTOR];
        for (int i = 0; i < this.vertex.length; i++) {
            for (int j = 0; j < this.vertex.length; j++) {
                largerAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
            largerVertices[i] = this.vertex[i];
        }
        this.vertex = largerVertices;
        this.adjMatrix = largerAdjMatrix;
    }

    /**
     * Adds a vertex to this graph, associating object with vertex.
     *
     * @param vertex the vertex to be added to this graph
     */
    @Override
    public void addVertex(T vertex) {
        if (numVertices == this.vertex.length)
            expandCapacity();
        this.vertex[numVertices] = vertex;
        for (int i = 0; i <= numVertices; i++) {
            adjMatrix[numVertices][i] = false;
            adjMatrix[i][numVertices] = false;
        }
        numVertices++;
    }

    /**
     * Searches for a vertex and returns its position
     *
     * @param vertex The vertex to look for
     * @return -1 if the vertex does not exist, his position otherwise
     */
    protected int findVertex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (this.vertex[i].equals(vertex)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes a single vertex with the given value from this graph.
     *
     * @param vertex the vertex to be removed from this graph
     */
    @Override
    public void removeVertex(T vertex) {
        int index = findVertex(vertex);

        if (index > -1) {
            for (int i = index; i < this.numVertices - 1; i++) {
                this.vertex[i] = this.vertex[i + 1];
            }
            this.vertex[this.numVertices - 1] = null;
            this.numVertices--;
        }
    }

    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    public void addEdge(T vertex1, T vertex2) {
        addEdge(findVertex(vertex1), findVertex(vertex2));
    }


    /**
     * Inserts an edge between two vertices of the graph.
     *
     * @param index1 the first index
     * @param index2 the second index
     */
    public void addEdge(int index1, int index2) {
        if (indexIsValid(index1) && indexIsValid(index2)) {
            adjMatrix[index1][index2] = true;
            adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Removes an edge between two vertex of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int index1 = findVertex(vertex1);
        int index2 = findVertex(vertex2);

        if (index1 > -1 && index2 > -1) {
            this.adjMatrix[index1][index2] = false;
            this.adjMatrix[index2][index1] = false;
        }
    }

    /**
     * Checks if a given index is valid for the current graph.
     * An index is considered valid if it is within the range of existing vertices.
     *
     * @param index the index to validate
     * @return true if the index is valid, false otherwise
     */
    private boolean indexIsValid(int index) {
        return ((index < numVertices) && (index >= 0));
    }


    /**
     * Returns a breadth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a breadth first iterator beginning at
     * the given vertex
     */
    @Override
    public Iterator iteratorBFS(T startVertex) {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        int startIndex = findVertex(startVertex);

        if (!indexIsValid(startIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();

            resultList.addToRear(vertex[x.intValue()]);

            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }

        return resultList.iterator();
    }

    /**
     * Returns a depth first iterator starting with the given vertex.
     *
     * @param startVertex the starting vertex
     * @return a depth first iterator starting at the
     * given vertex
     */
    @Override
    public Iterator iteratorDFS(T startVertex) {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(findVertex(startVertex)))
            return resultList.iterator();

        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalStack.push(new Integer(findVertex(startVertex)));
        resultList.addToRear(vertex[findVertex(startVertex)]);
        visited[findVertex(startVertex)] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            /** Find a vertex adjacent to x that has not been visited
             and push it on the stack */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertex[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty())
                traversalStack.pop();
        }
        return resultList.iterator();
    }

    /**
     * Returns an iterator that contains the shortest path between
     * the two vertex.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest
     * path between the two vertex
     */
    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        int startIndex = findVertex(startVertex);
        int targetIndex = findVertex(targetVertex);
        int index = startIndex;
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex) || !indexIsValid(targetIndex) ||
                (startIndex == targetIndex))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(startIndex));
        visited[startIndex] = true;
        pathLength[startIndex] = 0;
        predecessor[startIndex] = -1;

        while (!traversalQueue.isEmpty() && (index != targetIndex)) {
            index = (traversalQueue.dequeue()).intValue();

            /** Update the pathLength for each unvisited vertex adjacent
             to the vertex at the current index. */
            for (int i = 0; i < numVertices; i++) {
                if (adjMatrix[index][i] && !visited[i]) {
                    pathLength[i] = pathLength[index] + 1;
                    predecessor[i] = index;
                    traversalQueue.enqueue(new Integer(i));
                    visited[i] = true;
                }
            }
        }
        if (index != targetIndex)  // no path must have been found
            return resultList.iterator();

        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        index = targetIndex;
        stack.push(new Integer(index));
        do {
            index = predecessor[index];
            stack.push(new Integer(index));
        } while (index != startIndex);

        while (!stack.isEmpty()) {
            int vertexIndex = ((Integer) stack.pop());
            resultList.addToRear(vertex[vertexIndex]);
        }

        return resultList.iterator();
    }

    /**
     * Returns true if this graph is empty, false otherwise.
     *
     * @return true if this graph is empty
     */
    @Override
    public boolean isEmpty() {
        return numVertices == 0;
    }

    /**
     * Returns true if this graph is connected, false otherwise.
     *
     * @return true if this graph is connected
     */
    @Override
    public boolean isConnected() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("Graph is empty.");

        Iterator<T> it = iteratorBFS(vertex[0]);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    /**
     * Returns the number of vertex in this graph.
     *
     * @return the integer number of vertex in this graph
     */
    @Override
    public int size() {
        return this.numVertices;
    }

    /**
     * Returns a string representation of the graph.
     * Includes the adjacency matrix and vertex values, formatted for readability.
     *
     * @return a formatted string containing the adjacency matrix and vertex values
     */
    @Override
    public String toString() {
        if (numVertices == 0)
            return "Graph is empty";

        String result = new String("");

        result += "Adjacency Matrix\n";
        result += "----------------\n";
        result += "index\t";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i;
            if (i < 10)
                result += " ";
        }
        result += "\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                if (adjMatrix[i][j])
                    result += "1 ";
                else
                    result += "0 ";
            }
            result += "\n";
        }

        result += "\n\nVertex Values";
        result += "\n-------------\n";
        result += "index\tvalue\n\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";
            result += vertex[i].toString() + "\n";
        }
        result += "\n";
        return result;
    }
}
