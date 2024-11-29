package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class Edificio {
    private UnorderedListADT<String> divisoes;

    public Edificio() {
        divisoes = new ArrayUnorderedList<>();
    }

    public void setDivisao(String divisao) {
        this.divisoes.addToFront(divisao);
    }
}
