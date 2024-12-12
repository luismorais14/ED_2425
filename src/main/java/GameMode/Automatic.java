package GameMode;

import ADT.StackADT;
import ADT.UnorderedListADT;
import core.*;
import core.Character;
import implementations.LinkedStack;
import GameMode.Manual;


import java.util.Iterator;

public class Automatic {
    private static final int INF = Integer.MAX_VALUE;

    private Jogo jogo;
    private int matrix[][];

    public Automatic() {
        jogo = new Jogo();
        this.matrix = new int[this.jogo.getEdificio().getDivisoes().size()][this.jogo.getEdificio().getDivisoes().size()];
    }

    public Automatic(Jogo jogo) {
        this.jogo = jogo;
        this.matrix = new int[this.jogo.getEdificio().getDivisoes().size()][this.jogo.getEdificio().getDivisoes().size()];
    }

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

    public void startGame() {
        populateMatrix();

        Divisao start = startDivision();
        int startIndex = getDivisionIndex(start);
        Divisao target = findTargetDivision();
        int targetIndex = getDivisionIndex(target);

        calculateOptimalPath(startIndex, targetIndex);

        if (start == null) {
            System.out.println("No valid starting division found.");
            return;
        }

        System.out.println("Starting from: " + start.getNome());
        Divisao current = start;
        boolean success = false;

        while (!success && jogo.getPlayer().getVida() > 0) {
            Divisao nextMove = findNextMove(current);

            System.out.println("Moving to: " + nextMove.getNome());
            success = handleTarget(nextMove);
            handleItems(nextMove);
            boolean survived = handleEnemies(nextMove);

            if (!survived) {
                System.out.println("Tó Cruz has been defeated.");
                break;
            }

            enemiesMovement(current);
            current = nextMove;
        }

        if (success) {
            System.out.println("Mission successful!");
        } else {
            System.out.println("Mission failed.");
        }
    }

    private void populateMatrix() {
        int numDivisions = jogo.getEdificio().getDivisoes().size();

        for (int i = 0; i < numDivisions; i++) {
            for (int j = 0; j < numDivisions; j++) {
                matrix[i][j] = INF;
            }
        }

        Iterator<Divisao> divisions = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        while (divisions.hasNext()) {
            Divisao current = divisions.next();
            int currentIndex = getDivisionIndex(current);

            Iterator<Divisao> adjIterator =  this.jogo.getEdificio().getAdjacentDivisions(current).iterator();

            while (adjIterator.hasNext()) {
                Divisao adjacent = adjIterator.next();
                int adjacentIndex = getDivisionIndex(adjacent);
                int edgeWeight = calculateDivisionValue(current);

                matrix[currentIndex][adjacentIndex] = edgeWeight;
                matrix[adjacentIndex][currentIndex] = edgeWeight;
            }
        }
    }

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
     * Dijkstra's algorithm to get the path with the minor cost
     * From: https://medium.com/@kirti07arora/dijkstras-algorithm-in-java-a-journey-through-shortest-paths-cc2fd76104b2,
     * adapted
     *
     * @param sourceIndex the source index
     * @param targetIndex the target index
     */
    private void calculateOptimalPath(int sourceIndex, int targetIndex) {
        int n = matrix.length;
        int[] distance = new int[n];
        int[] previous = new int[n];
        boolean[] visited = new boolean[n];

        for (int i = 0; i < n; i++) {
            distance[i] = INF;
            previous[i] = -1;
            visited[i] = false;
        }
        distance[sourceIndex] = 0;

        for (int i = 0; i < n - 1; i++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && matrix[u][v] != INF &&
                        distance[u] + matrix[u][v] < distance[v]) {
                    distance[v] = distance[u] + matrix[u][v];
                    previous[v] = u;
                }
            }
        }

        printPath(previous, targetIndex);
    }

    /**
     * Calculates the minimum distance
     * From: https://medium.com/@kirti07arora/dijkstras-algorithm-in-java-a-journey-through-shortest-paths-cc2fd76104b2 , adapted
     *
     * @param distance wight array of the vertex
     * @param visited  array of visited
     * @return the minimum index of the vertex visited
     */
    private static int minDistance(int[] distance, boolean[] visited) {
        int min = INF, minIndex = -1;
        for (int v = 0; v < distance.length; v++) {
            if (!visited[v] && distance[v] <= min) {
                min = distance[v];
                minIndex = v;
            }
        }
        return minIndex;
    }

    private void printPath(int[] previous, int targetIndex) {
        if (previous[targetIndex] == -1) {
            System.out.println("No path to target.");
            return;
        }

        StackADT<Integer> pathStack = new LinkedStack<Integer>();

        for (int at = targetIndex; at != -1; at = previous[at]) {
            pathStack.push(at);
        }

        System.out.println("Optimal Path:");
        while (!pathStack.isEmpty()) {
            int index = pathStack.pop();
            System.out.print(getDivisaoByIndex(index).getNome());
            if (!pathStack.isEmpty()) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    /**
     * Retrieves the division by its index among the entraces/exits
     *
     * @param index the index of the disered division
     * @return the division corresponding to the index, or null if not found
     */
    private Divisao getDivisaoByIndex(int index) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();
        int i = 1;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (current != null && current.isEntradaSaida()) {
                if (i == index) {
                    return current;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * Handles the itens in the building
     *
     * @param div the current division
     */
    private void handleItems(Divisao div) {
        if (div.getItem() != null && !div.getItem().getTipo().isEmpty()) {
            System.out.println("Item encontrado: " + div.getItem().getTipo());
            if (div.getItem().getTipo().equals("colete")) {
                jogo.getPlayer().setVida(jogo.getPlayer().getVida() + div.getItem().getPontos());
            } else {
                jogo.getPlayer().addItemToMochila(div.getItem());
            }
            div.removeItem();
        }
    }

    /**
     * Handles the target in the building
     *
     * @param div the current division
     * @return true if the target is in that division, false if not
     */
    private boolean handleTarget(Divisao div) {
        if (div.getAlvo() != null && !div.getAlvo().getTipo().isEmpty()) {
            System.out.println("Alvo encontrado nesta divisão, corra!");
            div.removeTarget();
            return true;
        }
        return false;
    }

    /**
     * Handles interactions between the player and the enemies in the current division
     *
     * @param div the division where the interaction takes place
     */
    private boolean handleEnemies(Divisao div) {
        int totalDmg = 0;
        Iterator<Character> inimigosIterator = div.getInimigos().iterator();

        while (inimigosIterator.hasNext()) {
            Inimigo inimigo = (Inimigo) inimigosIterator.next();
            totalDmg += inimigo.getPoder();
            System.out.println("A lutar contra o inimigo: " + inimigo.getNome() + "Dano:" + inimigo.getPoder());
        }

        jogo.getPlayer().setVida(jogo.getPlayer().getVida() - totalDmg);

        System.out.println("Dano total recebido: " + totalDmg);
        System.out.println("Vida restante: " + jogo.getPlayer().getVida());

        if (jogo.getPlayer().getVida() <= 0) {
            return false;
        }

        return true;
    }

    private Divisao findTargetDivision() {
        Iterator<Divisao> it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        Divisao target = null;

        while (it.hasNext()) {
            Divisao div = it.next();
            if (div.getAlvo() != null && !div.getAlvo().getTipo().equals("quimico")) {
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
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator(div);

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current != null && !current.equals(div)) {
                Iterator<Character> enemyIterator = current.getInimigos().iterator();

                while (enemyIterator.hasNext()) {
                    Character enemy = enemyIterator.next();

                    UnorderedListADT<Divisao> adjacentDivisions = this.jogo.getEdificio().getAdjacentDivisions(current);

                    if (!adjacentDivisions.isEmpty()) {
                        int size = 0;
                        Iterator<Divisao> adjIterator = adjacentDivisions.iterator();
                        while (adjIterator.hasNext()) {
                            adjIterator.next();
                            size++;
                        }

                        int randomIndex = (int) (Math.random() * size);

                        adjIterator = adjacentDivisions.iterator();
                        Divisao newDivision = null;
                        for (int i = 0; i <= randomIndex; i++) {
                            newDivision = adjIterator.next();
                        }

                        try {
                            current.removeCharacter(enemy);
                            newDivision.addCharacter(enemy);
                        } catch (Exception e) {
                            System.out.println("Enemy not found.");
                        }
                    }
                }
            }
        }
    }

    /**
     * Finds the next best move for the player
     *
     * @param currentDiv the current division
     * @return the best move
     */
    private Divisao findNextMove(Divisao currentDiv) {
        UnorderedListADT<Divisao> adjacentDivision = jogo.getEdificio().getAdjacentDivisions(currentDiv);
        Iterator<Divisao> neighbours = adjacentDivision.iterator();
        Divisao bestChoice = null;
        int maxPoints = Integer.MIN_VALUE;

        while (neighbours.hasNext()) {
            Divisao neighbour = neighbours.next();
            if (neighbour == null)
                continue;
            int divisionPoint = calculateDivisionValue(neighbour);
            if (divisionPoint > maxPoints) {
                maxPoints = divisionPoint;
                bestChoice = neighbour;
            }
        }
        return bestChoice;
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