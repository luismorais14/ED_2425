package core;

public class Jogo {
    private Missao missao;
    private TiposSimulacao tipoSimulacao;
    private Edificio edificio;

    /**
     * Creates an empty game
     */
    public Jogo() {
        this.missao = new Missao();
        this.tipoSimulacao = null;
        this.edificio = new Edificio();
    }


    /**
     * Creates a new game, specifying the mission, the simulation type and the building
     * @param missao the game mission
     * @param tipoSimulacao the game type of simulation
     * @param edificio the game building
     */
    public Jogo(Missao missao, TiposSimulacao tipoSimulacao, Edificio edificio) {
        this.missao = missao;
        this.tipoSimulacao = tipoSimulacao;
        this.edificio = edificio;
    }

    /**
     * Getter for the game mission
     * @return the game mission
     */
    public Missao getMissao() {
        return missao;
    }

    /**
     * Setter for the game mission
     * @param missao the game mission
     */
    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    /**
     * Getter for game the simulation types
     * @return the game simulation types
     */
    public TiposSimulacao getTipoSimulacao() {
        return tipoSimulacao;
    }

    /**
     * Setter for the game simulation types
     * @param tipoSimulacao the game simulation types
     */
    public void setTipoSimulacao(TiposSimulacao tipoSimulacao) {
        this.tipoSimulacao = tipoSimulacao;
    }

    /**
     * Getter for the game buiding
     * @return the game bulding
     */
    public Edificio getEdificio() {
        return edificio;
    }

    /**
     * Setter for the game building
     * @param edificio the game building
     */
    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }
}
