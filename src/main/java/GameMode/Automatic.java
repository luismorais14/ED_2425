package GameMode;

import ADT.UnorderedListADT;
import core.*;
import core.Character;


import java.util.Arrays;
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
        Divisao start = startDivision();

        if (start == null) {
            System.out.println("Not valid starting division.");
            return;
        }

        System.out.println("Starting automatic simulation: \n");
        System.out.println("Starting division: " + start.getNome());

        game(start);
    }

    private void game(Divisao start) {
        Divisao currentDivision = start;

        //while (true) {
            System.out.println("\n Current Division:" + currentDivision.getNome());
            dijkstra(this.matrix, 0);

        //}

    }

    private void populateMatrix(int[][] matrix) {

    }

    /**
     * Dijkstra's algorithm to get the path with the minor cost
     * From: https://medium.com/@kirti07arora/dijkstras-algorithm-in-java-a-journey-through-shortest-paths-cc2fd76104b2 , adapted
     * @param graph the weight matrix
     * @param source
     */
    public static void dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] distance = new int[n];
        boolean[] visited = new boolean[n];

        Arrays.fill(distance, INF);
        distance[source] = 0;

        for (int count = 0; count < n - 1; count++) {
            int u = minDistance(distance, visited);
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && graph[u][v] != 0 && distance[u] != INF &&
                        distance[u] + graph[u][v] < distance[v]) {
                    distance[v] = distance[u] + graph[u][v];
                }
            }
        }

        printSolution(distance);
    }

    /**
     * Calculates the minimum distance
     * From: https://medium.com/@kirti07arora/dijkstras-algorithm-in-java-a-journey-through-shortest-paths-cc2fd76104b2 , adapted
     * @param distance wight array of the vertex
     * @param visited array of visited
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


    /**
     * Prints the distance between two vertex
     * From: https://medium.com/@kirti07arora/dijkstras-algorithm-in-java-a-journey-through-shortest-paths-cc2fd76104b2 , adapted
     * @param distance between two vertex
     */
    private static void printSolution(int[] distance) {
        System.out.println("Shortest Distances from Source:");
        for (int i = 0; i < distance.length; i++) {
            System.out.println("To " + i + ": " + distance[i]);
        }
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
        return points;
    }


}
