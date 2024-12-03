package implementations;

import ADT.GraphADT;
import Exceptions.EmptyCollectionException;

import java.util.Iterator;

public class Graph<T> implements GraphADT<T> {
    private final static int INITIAL_CAPACITY = 20;
    private final static int RESIZE_FACTOR = 2;

    private boolean[][] adjMatrix;
    private T[] vertices;
    private int numVertices;

    public Graph() {
        this.vertices = (T[]) (new Object[INITIAL_CAPACITY]);
        this.adjMatrix = new boolean[INITIAL_CAPACITY][INITIAL_CAPACITY];
        initializeAdjMatrix();
        this.numVertices = 0;
    }

    public Graph(int initialCapacity) {
        this.vertices = (T[]) (new Object[initialCapacity]);
        this.adjMatrix = new boolean[initialCapacity][initialCapacity];
        initializeAdjMatrix();
        this.numVertices = 0;
    }

    private void initializeAdjMatrix() {
        for (int i = 0; i < this.vertices.length; i++) {
            for (int j = 0; j < this.vertices.length; j++) {
                this.adjMatrix[i][j] = false;
            }
        }
    }

    private void expandCapacity() {
        T[] largerVertices = (T[]) (new Object[this.vertices.length * RESIZE_FACTOR]);
        boolean[][] largerAdjMatrix = new boolean[this.vertices.length * RESIZE_FACTOR][this.vertices.length * RESIZE_FACTOR];
        for (int i = 0; i < this.vertices.length; i++) {
            for (int j = 0; j < this.vertices.length; j++) {
                largerAdjMatrix[i][j] = this.adjMatrix[i][j];
            }
            largerVertices[i] = this.vertices[i];
        }
        this.vertices = largerVertices;
        this.adjMatrix = largerAdjMatrix;
    }

    /**
     * Adds a vertex to this graph, associating object with vertex.
     *
     * @param vertex the vertex to be added to this graph
     */
    @Override
    public void addVertex(T vertex) {
        if (this.numVertices == this.vertices.length) {
            expandCapacity();
        }

        vertices[this.numVertices] = vertex;
        this.numVertices++;
    }

    protected int findVertex(T vertex) {
        for (int i = 0; i < this.numVertices; i++) {
            if (vertices[i].equals(vertex)) {
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
                vertices[i] = vertices[i + 1];
            }
            vertices[this.numVertices - 1] = null;
            this.numVertices--;
        }
    }

    /**
     * Inserts an edge between two vertices of this graph.
     *
     * @param vertex1 the first vertex
     * @param vertex2 the second vertex
     */
    @Override
    public void addEdge(T vertex1, T vertex2) {
        int index1 = findVertex(vertex1);
        int index2 = findVertex(vertex2);

        if (index1 > -1 && index2 > -1) {
            this.adjMatrix[index1][index2] = true;
            this.adjMatrix[index2][index1] = true;
        }
    }

    /**
     * Removes an edge between two vertices of this graph.
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

        if (!indexIsValid(findVertex(startVertex)))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(findVertex(startVertex)));
        visited[findVertex(startVertex)] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x.intValue()]);

            /** Find all vertices adjacent to x that have not been visited
             and queue them up */
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
        resultList.addToRear(vertices[findVertex(startVertex)]);
        visited[findVertex(startVertex)] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            /** Find a vertex adjacent to x that has not been visited
             and push it on the stack */
            for (int i = 0; (i < numVertices) && !found; i++) {
                if (adjMatrix[x.intValue()][i] && !visited[i]) {
                    traversalStack.push(new Integer(i));
                    resultList.addToRear(vertices[i]);
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
     * the two vertices.
     *
     * @param startVertex  the starting vertex
     * @param targetVertex the ending vertex
     * @return an iterator that contains the shortest
     * path between the two vertices
     */
    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        int index = findVertex(startVertex);
        int[] pathLength = new int[numVertices];
        int[] predecessor = new int[numVertices];
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<Integer> resultList =
                new ArrayUnorderedList<Integer>();

        if (!indexIsValid(findVertex(startVertex)) || !indexIsValid(findVertex(targetVertex)) ||
                (findVertex(startVertex) == findVertex(targetVertex)))
            return resultList.iterator();

        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++)
            visited[i] = false;

        traversalQueue.enqueue(new Integer(findVertex(startVertex)));
        visited[findVertex(startVertex)] = true;
        pathLength[findVertex(startVertex)] = 0;
        predecessor[findVertex(startVertex)] = -1;

        while (!traversalQueue.isEmpty() && (index != findVertex(startVertex))) {
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
        if (index != findVertex(targetVertex))  // no path must have been found
            return resultList.iterator();

        LinkedStack<Integer> stack = new LinkedStack<Integer>();
        index = findVertex(targetVertex);
        stack.push(new Integer(index));
        do {
            index = predecessor[index];
            stack.push(new Integer(index));
        } while (index != findVertex(startVertex));

        while (!stack.isEmpty())
            resultList.addToRear(((Integer) stack.pop()));

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

        Iterator<T> it = iteratorBFS(vertices[0]);
        int count = 0;

        while (it.hasNext())
        {
            it.next();
            count++;
        }
        return (count == numVertices);
    }

    /**
     * Returns the number of vertices in this graph.
     *
     * @return the integer number of vertices in this graph
     */
    @Override
    public int size() {
        return 0;
    }
}
