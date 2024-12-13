package GameMode;

import ADT.HeapADT;
import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import core.*;
import core.Character;
import implementations.*;


import java.util.Iterator;

public class Automatic {
    private static final int INF = Integer.MAX_VALUE;

    private Jogo jogo;

    /**
     * Creates an empty automatic game
     */
    public Automatic() {
        jogo = new Jogo();
    }

    /**
     * Creates an automatic game specifying the game
     * @param jogo the game
     */
    public Automatic(Jogo jogo) {
        this.jogo = jogo;
    }

    /**
     * Searches for the optimal starting division
     * @return the optimal starting division
     */
    private Divisao startDivision() {
        Iterator<Divisao> it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        Divisao start = null;
        int minDamage = Integer.MIN_VALUE;

        while (it.hasNext()) {
            Divisao div = it.next();
            if (div.isEntradaSaida() && div.getInimigos().isEmpty()) {
                start = div;
                break;
            }
        }

        if (start == null) {
            it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());

            while (it.hasNext()) {
                Divisao div = it.next();
                if (div.isEntradaSaida()) {
                    int damage = 0;
                    Iterator<Character> inimigosIterator = div.getInimigos().iterator();

                    while (inimigosIterator.hasNext()) {
                        Inimigo inimigo = (Inimigo) inimigosIterator.next();
                        damage += inimigo.getPoder();
                    }

                    if (damage < minDamage) {
                        minDamage = damage;
                        start = div;
                    }
                }
            }
        }

        return start;
    }

    /**
     * Starts the automatic game
     * @throws ElementNotFoundException if the element is not found
     */
    public void startGame() throws ElementNotFoundException {
        Divisao start = startDivision();
        Divisao target = findTargetDivision();
        UnorderedListADT<Divisao> path = calculateOptimalPath(start, target);

        if (start == null) {
            System.out.println("No valid starting division found.");
            return;
        }

        Divisao current = start;
        System.out.println("\nBest path to target division:");
        showBestPath(path, current);

        current = target;
        target = startDivision();

        path = calculateOptimalPath(current, target);

        System.out.println("\nBest path to the exit:");
        showBestPath(path, current);

    }

    /**
     * Shows the best path to the target division or the exit, simulating all the movements
     * @param path the path to be shown
     * @param current the current division
     * @throws ElementNotFoundException if the Character is not found
     */
    private void showBestPath(UnorderedListADT<Divisao> path, Divisao current) throws ElementNotFoundException {
        while (!path.isEmpty()) {
            Divisao next = path.removeFirst();
            playerMovement(current, next);
            handleItems(current);
            enemiesMovement(current);
            handleEnemies(current);
            current = next;

            System.out.print("" + current.getNome() + " -> ");
            if (path.isEmpty()) {
                System.out.print("End\n");
            }
        }
    }

    /**
     * Gets the index of the division
     * @param div the division to be serached
     * @return the index of the division
     */
    private int getDivisionIndex(Divisao div) {
        Iterator<Divisao> divisions = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        int index = 0;
        while (divisions.hasNext()) {
            Divisao current = divisions.next();
            if (current.equals(div)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Dijkstra's algorithm to get the path with the minor cost.
     *
     * Author: Professor Ricardo Santos / rjs@estg.ipp.pt
     * Date: 12/12/2024
     * Type: Algorithm
     * Availability: Provided notes from Data Structures class. Graphs class subject.
     *
     * This algorithm was adapted.
     *
     * @param startDiv  the starting division
     * @param targetDiv the target index
     */
    private UnorderedListADT<Divisao> calculateOptimalPath(Divisao startDiv, Divisao targetDiv) {
        int numDivisions = jogo.getEdificio().getDivisoes().size();
        int startIndex = getDivisionIndex(startDiv);
        int targetIndex = getDivisionIndex(targetDiv);

        int[] distance = new int[numDivisions];
        int[] previous = new int[numDivisions];

        HeapADT<PriorityQueueNode<Divisao>> priorityQueue = new PriorityQueue<Divisao>();

        distance[startIndex] = 0;
        for (int i = 0; i < numDivisions; i++) {
            if (i != startIndex) {
                distance[i] = INF;
            }
            previous[i] = -1;
            Divisao div = getDivisaoByIndex(i);
            PriorityQueueNode<Divisao> newNode = new PriorityQueueNode<Divisao>(div, distance[i]);
            priorityQueue.addElement(newNode);
        }

        while (!priorityQueue.isEmpty()) {
            PriorityQueueNode<Divisao> current = priorityQueue.removeMin();

            Divisao currentDiv = current.getElement();
            int currentIndex = getDivisionIndex(currentDiv);

            Iterator<Divisao> adjIterator = this.jogo.getEdificio().getAdjacentDivisions(currentDiv).iterator();

            while (adjIterator.hasNext()) {
                Divisao adjDiv = adjIterator.next();
                int adjIndex = getDivisionIndex(adjDiv);

                int newDistance = distance[currentIndex] + calculateDivisionValue(adjDiv);

                if (newDistance < distance[adjIndex]) {
                    distance[adjIndex] = newDistance;
                    previous[adjIndex] = currentIndex;
                    PriorityQueueNode<Divisao> newNode = new PriorityQueueNode<Divisao>(adjDiv, newDistance);
                    priorityQueue.addElement(newNode);
                }
            }
        }

        // Backtracking to find the path, and build it in correct order
        ArrayUnorderedList<Divisao> path = new ArrayUnorderedList<>();
        int current = targetIndex;
        while (current != -1) {
            path.addToFront(getDivisaoByIndex(current));
            current = previous[current];
        }

        return path;
    }

    /**
     * Handles the player movement between divisions
     * @param currentDiv the current division
     * @param nextDiv the next division
     * @throws ElementNotFoundException if the Character is not found
     */
    private void playerMovement(Divisao currentDiv, Divisao nextDiv) throws ElementNotFoundException {
        if (currentDiv == null || nextDiv == null) {
            System.out.println("No valid path to follow.");
            return;
        }

        nextDiv.addCharacter(jogo.getPlayer());
        currentDiv.removeCharacter(jogo.getPlayer());
    }

    /**
     * Retrieves the division by its index among the entraces/exits
     *
     * @param index the index of the disered division
     * @return the division corresponding to the index, or null if not found
     */
    private Divisao getDivisaoByIndex(int index) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();
        int i = 0;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (i == index) {
                return current;
            }
            i++;
        }

        return null;
    }

    /**
     * Handles the items in the building
     *
     * @param div the current division
     */
    private void handleItems(Divisao div) {
        if (div.getItem() != null && !div.getItem().getTipo().isEmpty()) {
            if (div.getItem().getTipo().equals("colete")) {
                jogo.getPlayer().setVida(jogo.getPlayer().getVida() + div.getItem().getPontos());
            } else {
                jogo.getPlayer().addItemToMochila(div.getItem());
            }
            div.removeItem();
        }
    }

    /**
     * Handles interactions between the player and the enemies in the current division
     *
     * @param div the division where the interaction takes place
     */
    private void handleEnemies(Divisao div) {
        int totalDmg = 0;
        Iterator<Character> inimigosIterator = div.getInimigos().iterator();

        while (inimigosIterator.hasNext()) {
            Inimigo inimigo = (Inimigo) inimigosIterator.next();
            totalDmg += inimigo.getPoder();
        }

        jogo.getPlayer().setVida(jogo.getPlayer().getVida() - totalDmg);

        if (jogo.getPlayer().getVida() <= 0) {
            return;
        }
    }

    /**
     * Searches for the target division
     * @return the target division
     */
    private Divisao findTargetDivision() {
        Iterator<Divisao> it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        Divisao target = null;

        while (it.hasNext()) {
            Divisao div = it.next();
            if (div.getAlvo() != null && div.getAlvo().getTipo().equals("quimico")) {
                target = div;
                break;
            }
        }

        return target;
    }


    /**
     * Handles interactions between the player and the enemies in the current division
     *
     * @param div the division where the interaction takes place
     */
    private void enemiesMovement(Divisao div) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current.equals(div)) {
                continue;
            }

            Iterator<Character> enemyIterator = current.getInimigos().iterator();

            while (enemyIterator.hasNext()) {
                Character enemy = enemyIterator.next();

                UnorderedListADT<Divisao> adjacentDivisions = this.jogo.getEdificio().getAdjacentDivisions(current);
                UnorderedListADT<Divisao> validDivisions = new ArrayUnorderedList<>();
                Iterator<Divisao> adjIterator = adjacentDivisions.iterator();

                while (adjIterator.hasNext()) {
                    Divisao potentialDivision = adjIterator.next();
                    int distance = this.jogo.getEdificio().calculateDistance(current, potentialDivision);

                    if (distance <= 2) {
                        validDivisions.addToFront(potentialDivision);
                    }
                }

                if (!validDivisions.isEmpty()) {
                    int randomIndex = (int) (Math.random() * validDivisions.size());

                    adjIterator = validDivisions.iterator();
                    Divisao newDivision = null;
                    for (int i = 0; i <= randomIndex; i++) {
                        newDivision = adjIterator.next();
                    }

                    try {
                        current.removeCharacter(enemy);
                        newDivision.addCharacter(enemy);
                    } catch (Exception e) {
                        System.out.println("Error moving the enemy: " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Calculates the weight of the edges between divisions
     *
     * @param div the division
     * @return the weight of the edge
     */
    private int calculateDivisionValue(Divisao div) {
        int points = 0;

        Iterator<Character> inimigosIterator = div.getInimigos().iterator();
        while (inimigosIterator.hasNext()) {
            Inimigo inimigo = (Inimigo) inimigosIterator.next();
            points += inimigo.getPoder();
        }

        if (div.getItem() != null) {
            points -= div.getItem().getPontos();
        }

        return Math.max(0, points);
    }


}