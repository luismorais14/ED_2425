package core;

import ADT.GraphADT;
import ADT.ListADT;
import ADT.UnorderedListADT;
import implementations.ArrayList;
import implementations.ArrayUnorderedList;
import implementations.Graph;

import java.util.Iterator;

public class Edificio {
    private GraphADT<Divisao> divisoes;
    private Divisao startVertex;

    /**
     * Creates an empty building
     */
    public Edificio() {
        divisoes = new Graph<Divisao>();
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

    public void setStartVertex(Divisao startVertex) {
        this.startVertex = startVertex;
    }

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

    public Iterator getDivisoesIterator() {
        return this.divisoes.iteratorBFS(startVertex);
    }

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
        try {
            divisoes.removeEdge(divisao1, divisao2);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            divisoes.addEdge(divisao1, divisao2);
        }
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



}
