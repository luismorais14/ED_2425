package core;

import ADT.GraphADT;
import ADT.QueueADT;
import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;
import implementations.GraphImpl;
import implementations.LinkedQueue;

import java.util.Iterator;

public class Edificio {
    private GraphADT<Divisao> divisoes;
    private Divisao startVertex;

    /**
     * Creates an empty building
     */
    public Edificio() {
        divisoes = new GraphImpl<Divisao>();
        startVertex = null;
    }

    /**
     * Adds a new division on the building
     *
     * @param divisao the new division
     */
    public void addDivison(Divisao divisao) {
        this.divisoes.addVertex(divisao);
    }

    /**
     * Sets the starting vertex for the graph traversal.
     * This method assigns the given `startVertex` to the `startVertex` field of the graph.
     * The `startVertex` will be used as the starting point for traversals such as BFS (Breadth-First Search).
     *
     * @param startVertex the vertex to be set as the starting point for traversals
     */
    public void setStartVertex(Divisao startVertex) {
        this.startVertex = startVertex;
    }

    /**
     * Gets the starting vertex for the graph traversal.
     * This method returns the current `startVertex` field, which represents the vertex
     * that is used as the starting point for graph traversal operations.
     *
     * @return the current starting vertex of the graph
     */
    public Divisao getStartVertex() {
        return this.startVertex;
    }

    /**
     * Adds a connection between two divisions
     *
     * @param divisao1 the first division
     * @param divisao2 the second division
     */
    public void addConnection(Divisao divisao1, Divisao divisao2) {
        this.divisoes.addEdge(divisao1, divisao2);
    }

    /**
     * Returns an iterator that iterates over the graph's vertices using BFS starting from the current `startVertex`.
     * This method returns an iterator that can be used to traverse all the vertices in the graph
     * starting from the vertex set by the `startVertex` field. The traversal follows the Breadth-First Search (BFS) algorithm.
     *
     * @return an iterator for traversing the graph's vertices using BFS from the current `startVertex`
     */
    public Iterator getDivisoesIterator() {
        return this.divisoes.iteratorBFS(startVertex);
    }

    /**
     * Returns an iterator that iterates over the graph's vertices using BFS starting from a specified vertex.
     * This method allows the caller to specify a starting vertex for the traversal. It returns an iterator
     * that follows the Breadth-First Search (BFS) algorithm, starting from the given `startVertex`.
     *
     * @param startVertex the vertex from which the BFS traversal will start
     * @return an iterator for traversing the graph's vertices using BFS from the specified `startVertex`
     */
    public Iterator getDivisoesIterator(Divisao startVertex) {
        return this.divisoes.iteratorBFS(startVertex);
    }


    /**
     * Gets the graph's collection of divisions (vertices).
     * This method returns the Graph instance that represents the entire graph of divisions.
     * It provides access to all the vertices and their relationships in the graph.
     *
     * @return the graph containing all the divisions (vertices)
     */
    public GraphADT<Divisao> getDivisoes() {
        return divisoes;
    }

    /**
     * Checks if there is an edge between two divisions.
     *
     * @param divisao1 the first division
     * @param divisao2 the second division
     * @return true if there is an edge between the two divisions, false otherwise
     */
    private boolean hasEdge(Divisao divisao1, Divisao divisao2) {
        boolean aux = false;

        if (this.divisoes instanceof GraphImpl) {
            aux = ((GraphImpl<Divisao>) this.divisoes).hasEdge(divisao1, divisao2);
        }
        return aux;
    }

    /**
     * Gets the adjacent divisions for a given division.
     *
     * @param current the division for which to find adjacent divisions
     * @return a list of adjacent divisions
     */
    public UnorderedListADT<Divisao> getAdjacentDivisions(Divisao current) {
        UnorderedListADT<Divisao> adjacentDivisions = new ArrayUnorderedList<Divisao>();

        Iterator<Divisao> iterator = divisoes.iteratorBFS(startVertex);
        while (iterator.hasNext()) {
            Divisao division = iterator.next();
            if (!division.equals(current) && hasEdge(current, division)) {
                adjacentDivisions.addToFront(division);
            }
        }

        return adjacentDivisions;
    }

    /**
     * Returns the number of entrances/exits in the building.
     * @return the number of entrances/exits in the building
     */
    public int getNumEntradasSaidas() {
        int numEntradasSaidas = 0;
        Iterator<Divisao> iterator = this.divisoes.iteratorBFS(startVertex);

        while (iterator.hasNext()) {
            Divisao divisao = iterator.next();
            if (divisao.isEntradaSaida()) {
                numEntradasSaidas++;
            }
        }

        return numEntradasSaidas;
    }

    /**
     * Calculates the distance between two divisions.
     * @param start the starting division
     * @param target the target division
     * @return the distance between the two divisions
     */
    public int calculateDistance(Divisao start, Divisao target) {
        QueueADT<Divisao> queue = new LinkedQueue<Divisao>();
        UnorderedListADT<Divisao> visited = new ArrayUnorderedList<Divisao>();

        queue.enqueue(start);
        visited.addToFront(start);

        int distance = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (int i = 0; i < levelSize; i++) {
                Divisao current = queue.dequeue();

                if (current.equals(target)) {
                    return distance;
                }

                Iterator<Divisao> iterator = divisoes.iteratorBFS(current);
                while (iterator.hasNext()) {
                    Divisao neighbor = iterator.next();
                    if (!visited.contains(neighbor)) {
                        queue.enqueue(neighbor);
                        visited.addToFront(neighbor);
                    }
                }
            }

            distance++;
        }

        return Integer.MAX_VALUE;
    }


}
