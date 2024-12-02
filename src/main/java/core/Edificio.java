package core;

import ADT.GraphADT;
import implementations.ArrayUnorderedList;
import implementations.Graph;
import implementations.LinkedQueue;

import java.util.Iterator;

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
     * @param divisao
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

    //TODO criar método para procurar divisao (talvez com iteradores???) (usar compareTo ou equals)

    public Divisao searchDivisao(Divisao divisao) {
        if (divisao == null) {
            return null;
        }

        Iterator it = divisoes.iteratorBFS(divisao);

        while (it.hasNext()) {
            Divisao current = (Divisao) it.next();
            if (current != null && current.equals(divisao)) {
                return current;
            }
        }
        return null;

    }


}
