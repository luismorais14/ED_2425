package core;

import ADT.GraphADT;
import implementations.Graph;

public class Edificio {
    private GraphADT<Divisao> divisoes;

    /**
     * Creates an empty building
     */
    public Edificio() {
        divisoes = new Graph<Divisao>();
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
     * Adds a connection between two divisions
     *
     * @param divisao1 the first division
     * @param divisao2 the second division
     */
    public void addConnection(Divisao divisao1, Divisao divisao2) {
        this.divisoes.addEdge(divisao1, divisao2);
    }
}
