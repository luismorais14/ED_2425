package core;

public class Jogo {
    private Missao missao;
    private Edificio edificio;

    /**
     * Creates an empty game
     */
    public Jogo() {
        this.missao = new Missao();
        this.edificio = new Edificio();
    }


    /**
     * Creates a new game, specifying the mission, the simulation type and the building
     * @param missao the game mission
     * @param edificio the game building
     */
    public Jogo(Missao missao, Edificio edificio) {
        this.missao = missao;
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
