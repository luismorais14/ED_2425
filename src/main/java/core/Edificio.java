package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class Edificio {
    private UnorderedListADT<Divisao> divisoes;

    /**
     * Creates an empty building
     */
    public Edificio() {
        divisoes = new ArrayUnorderedList<>();
    }

    /**
     * Adds a new division on the building
     * @param divisao
     */
    public void addDivison(Divisao divisao) {
        this.divisoes.addToFront(divisao);
    }

    /**
     * Verifies if the building contains the division parameterized
     * @param divisao the division to be checked
     * @return true if the division exists, false otherwise
     */
    public boolean containsDivisao(Divisao divisao) {
        return divisoes.contains(divisao);
    }

}
