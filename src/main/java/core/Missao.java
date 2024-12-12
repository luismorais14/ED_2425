package core;

import ADT.OrderedListADT;
import implementations.ArrayOrderedList;

import java.util.Iterator;

public class Missao {
    private String codMissao;
    private Alvo alvo;
    private int versao;
    private OrderedListADT<MissionResult> result;

    /**
     * Creates an empty mission
     */
    public Missao() {
        codMissao = "";
        alvo = new Alvo();
        versao = 0;
        result = new ArrayOrderedList<MissionResult>();
    }

    /**
     * Creates a mission specifying the mission code, the target, version and mission key
     * @param codMissao the mission code
     * @param alvo the mission target
     * @param versao the mission version
     */
    public Missao(String codMissao, Alvo alvo,String versao) {
        this.codMissao = codMissao;
        this.alvo = alvo;
        this.versao = Integer.parseInt(versao);
        result = new ArrayOrderedList<MissionResult>();
    }

    /**
     * Getter for the mission code
     * @return the mission code
     */
    public String getCodMissao() {
        return codMissao;
    }

    /**
     * Setter for the mission code
     * @param codMissao the mission code
     */
    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    /**
     * Getter for the mission target
     * @return the mission target
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Setter for the mission target
     * @param alvo the mission target
     */
    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    /**
     * Getter for the mission version
     * @return the mission version
     */
    public int getVersao() {
        return versao;
    }

    /**
     * Setter for the mission version
     * @param versao the mission version
     */
    public void setVersao(int versao) {
        this.versao = versao;
    }

    public OrderedListADT<MissionResult> getResult() {
        return this.result;
    }

    public Iterator<MissionResult> getResultsIterator() {
        return this.result.iterator();
    }

    public void setResult(MissionResultEnum result, int vidaRestante) {
        MissionResult missionResult = new MissionResult(this.versao, result, vidaRestante);

        this.result.add(missionResult);
    }
}
