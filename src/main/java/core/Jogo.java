package core;

import ADT.QueueADT;
import implementations.LinkedQueue;

public class Jogo {
    private final static int PLAYER_PODER = 50;

    private Missao missao;
    private Edificio edificio;
    private Player player;
    private QueueADT<Divisao> paths;

    /**
     * Creates an empty game
     */
    public Jogo() {
        this.missao = new Missao();
        this.edificio = new Edificio();
        this.player = new Player(PLAYER_PODER);
        this.paths = new LinkedQueue<Divisao>();
    }


    /**
     * Creates a new game, specifying the mission, the simulation type and the building
     * @param missao the game mission
     * @param edificio the game building
     */
    public Jogo(Missao missao, Edificio edificio, Player player) {
        this.missao = missao;
        this.edificio = edificio;
        this.player = player;
        this.paths = new LinkedQueue<Divisao>();
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

    /**
     * Getter for the game player
     * @return the game player
     */
    public Player getPlayer() {
        return this.player;
    }

    public void addPath(Divisao divisao) {
        this.paths.enqueue(divisao);
    }

    public QueueADT<Divisao> getPaths() {
        return this.paths;
    }
}
