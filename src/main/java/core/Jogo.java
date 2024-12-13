package core;

import ADT.OrderedListADT;
import ADT.QueueADT;
import ADT.UnorderedListADT;
import implementations.ArrayOrderedList;
import implementations.ArrayUnorderedList;
import implementations.LinkedQueue;

import java.util.Iterator;

public class Jogo {
    private final static int PLAYER_PODER = 50;

    private UnorderedListADT<Missao> missao;
    private Edificio edificio;
    private Player player;
    private QueueADT<Divisao> paths;
    private OrderedListADT<MissionResult> results;
    private UnorderedListADT<MissionPath> missionPaths;

    /**
     * Creates an empty game
     */
    public Jogo() {
        this.missao = new ArrayUnorderedList<Missao>();
        this.edificio = new Edificio();
        this.player = new Player(PLAYER_PODER);
        this.paths = new LinkedQueue<Divisao>();
        this.results = new ArrayOrderedList<MissionResult>();
        this.missionPaths = new ArrayUnorderedList<MissionPath>();
    }


    /**
     * Creates a new game, specifying the mission, the simulation type and the building
     *
     * @param missao   the game mission
     * @param edificio the game building
     */
    public Jogo(Missao missao, Edificio edificio, Player player) {
        this.missao = new ArrayUnorderedList<>();
        this.missao.addToFront(missao);
        this.edificio = edificio;
        this.player = player;
        this.paths = new LinkedQueue<Divisao>();
        this.results = new ArrayOrderedList<MissionResult>();
        this.missionPaths = new ArrayUnorderedList<MissionPath>();
    }

    /**
     * Getter for the game mission
     *
     * @return the game mission
     */
    public Iterator getMissaoIterator() {
        return this.missao.iterator();
    }

    /**
     * Setter for the game mission
     *
     * @param missao the game mission
     */
    public void addMissao(Missao missao) {
        this.missao.addToFront(missao);
    }


    /**
     * Getter for the game buiding
     *
     * @return the game bulding
     */
    public Edificio getEdificio() {
        return edificio;
    }

    /**
     * Setter for the game building
     *
     * @param edificio the game building
     */
    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

    /**
     * Getter for the game player
     *
     * @return the game player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Adds a division (path) to the game player's path.
     *
     * @param divisao the division (path) to be added
     */
    public void addPath(Divisao divisao) {
        this.paths.enqueue(divisao);
    }

    /**
     * Getter for the game player's paths queue.
     *
     * @return the queue of divisions (paths) the player is on
     */
    public QueueADT<Divisao> getPaths() {
        return this.paths;
    }

    /**
     * Clears the paths of the player.
     * This method resets the paths queue to an empty state.
     */
    public void clearPaths() {
        this.paths = new LinkedQueue<Divisao>();
    }

    /**
     * Adds a simulation result to the game.
     *
     * @param result the result of the simulation to be added
     */
    public void addResult(MissionResult result) {
        this.results.add(result);
    }

    /**
     * Displays the sorted simulation results based on remaining life points.
     * The results are printed in ascending order of life points remaining.
     */
    public void displaySortedResults() {
        System.out.println("Simulation Results (sorted by remaining life points):");
        Iterator<MissionResult> iterator = this.results.iterator();
        while (iterator.hasNext()) {
            MissionResult result = iterator.next();
            System.out.println(result.toString());
        }
    }

    /**
     * Getter for the game mission paths.
     * @return the game mission paths
     */
    public UnorderedListADT<MissionPath> getMissionPaths() {
        return missionPaths;
    }

    /**
     * Adds a mission path to the game.
     * @param missao the mission
     * @param paths the path to be added
     */
    public void addMissionPath(Missao missao, QueueADT<Divisao> paths) {
        UnorderedListADT<Divisao> pathsList = new ArrayUnorderedList<>();
        QueueADT<Divisao> tempQueue = new LinkedQueue<>();
        while (!paths.isEmpty()) {
            Divisao divisao = paths.dequeue();
            tempQueue.enqueue(divisao);
            pathsList.addToFront(divisao);
        }

        while (!tempQueue.isEmpty()) {
            paths.enqueue(tempQueue.dequeue());
        }

        this.missionPaths.addToFront(new MissionPath(missao.getCodMissao(), pathsList));
    }

}
