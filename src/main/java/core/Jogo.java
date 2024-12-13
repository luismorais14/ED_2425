package core;

import ADT.OrderedListADT;
import ADT.QueueADT;
import ADT.UnorderedListADT;
import implementations.ArrayOrderedList;
import implementations.ArrayUnorderedList;
import implementations.LinkedQueue;

import java.util.Iterator;
import java.util.Scanner;

public class Jogo {
    private final static int PLAYER_PODER = 50;

    private UnorderedListADT<Missao> missao;
    private Edificio edificio;
    private Player player;
    private QueueADT<Divisao> paths;
    private static OrderedListADT<MissionResult> results = new ArrayOrderedList<MissionResult>();
    private UnorderedListADT<MissionPath> missionPaths;

    /**
     * Creates an empty game
     */
    public Jogo() {
        this.missao = new ArrayUnorderedList<Missao>();
        this.edificio = new Edificio();
        this.player = new Player(PLAYER_PODER);
        this.paths = new LinkedQueue<Divisao>();
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
        results.add(result);
    }

    /**
     * Displays the sorted simulation results based on remaining life points.
     * The results are printed in ascending order of life points remaining.
     */
    public void displaySortedResults() {
        Iterator<Missao> allMissions = this.missao.iterator();
        int i = 1;
        UnorderedListADT<Missao> availableMissions = new ArrayUnorderedList<>();

        System.out.println("Choose a mission to see the results: ");
        while (allMissions.hasNext()) {
            Missao missao = allMissions.next();
            availableMissions.addToFront(missao);
            System.out.println(i + ". " + missao.getCodMissao());
            i++;
        }

        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();

        if (choice < 1 || choice > availableMissions.size()) {
            System.out.println("Invalid choice!");
            return;
        }

        Missao missaoSelecionada = null;
        i = 1;
        Iterator<Missao> missionIterator = availableMissions.iterator();
        while (missionIterator.hasNext()) {
            Missao missao = missionIterator.next();
            if (i == choice) {
                missaoSelecionada = missao;
                break;
            }
            i++;
        }

        UnorderedListADT<MissionResult> filteredResults = new ArrayUnorderedList<>();
        Iterator<MissionResult> resultIterator = results.iterator();
        while (resultIterator.hasNext()) {
            MissionResult result = resultIterator.next();
            if (result.getVersao() == missaoSelecionada.getVersao()) {
                filteredResults.addToFront(result);
            }
        }

        OrderedListADT<MissionResult> sortedResults = new ArrayOrderedList<>();
        Iterator<MissionResult> filteredIterator = filteredResults.iterator();
        while (filteredIterator.hasNext()) {
            MissionResult result = filteredIterator.next();
            sortedResults.add(result);
        }

        System.out.println("Mission results: " + missaoSelecionada.getCodMissao());
        System.out.println("Results:");
        Iterator<MissionResult> sortedIterator = sortedResults.iterator();
        while (sortedIterator.hasNext()) {
            MissionResult result = sortedIterator.next();
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
