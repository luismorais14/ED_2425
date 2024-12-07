package core;

import ADT.GraphADT;
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

    public Divisao searchDivisao(String div) {
        Iterator<Divisao> iterator = getDivisoesIterator();
        int i = 1;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current != null && current.getNome().equals(div)) {
                return current;
            }
        }

        return null;
    }
}
