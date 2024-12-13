package GameMode;

import ADT.OrderedListADT;
import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import core.*;
import core.Character;
import implementations.ArrayOrderedList;
import implementations.ArrayUnorderedList;

import java.util.Iterator;
import java.util.Scanner;

public class Manual {
    private Jogo jogo;

    /**
     * Creates a new manual game
     */
    public Manual() {
        jogo = new Jogo();
        this.jogo.clearPaths();
    }

    /**
     * Creates a new manual game
     *
     * @param jogo the game to be played
     */
    public Manual(Jogo jogo) {
        this.jogo = jogo;
        this.jogo.clearPaths();
    }

    /**
     * Lists all divisions in the building that are marked as entrances/exits
     */
    private void listEntradaSaida() {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();
        int i = 1;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current != null && current.isEntradaSaida()) {
                System.out.println(i + ". " + current.getNome());
                i++;
            }
        }
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
     * Prints the possible moves from the current division
     *
     * @param currentDivisao the current division
     */

    private void printPossibleMoves(Divisao currentDivisao) {
        Divisao current = currentDivisao;
        int i = 1;
        UnorderedListADT<Divisao> adjacentDivisions = this.jogo.getEdificio().getAdjacentDivisions(current);

        if (adjacentDivisions != null && !adjacentDivisions.isEmpty()) {
            System.out.println("Divisions accessible in a movement from the current division:");

            Iterator<Divisao> adjIterator = adjacentDivisions.iterator();
            while (adjIterator.hasNext()) {
                Divisao adjacentDivision = adjIterator.next();
                System.out.println(i + "." + adjacentDivision.getNome());
                i++;
            }
        } else {
            System.out.println("0. Exit the building");
        }
    }

    /**
     * Starts the manual game mode
     */

    public void startGame() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";
        Divisao chooseDivisao = null;

        while (!aux) {
            inputNum = 0;
            System.out.println("\nChoose the entrance through which you want to enter the building: ");
            listEntradaSaida();
            try {
                inputNum = input.nextInt();
                if (inputNum >= 1 && inputNum <= this.jogo.getEdificio().getNumEntradasSaidas()) {
                    chooseDivisao = getDivisaoByIndex(inputNum);
                    aux = true;
                } else {
                    System.out.println("Invalid Option. Please choose a valid entrance.");
                    return;
                }

                if (chooseDivisao == null) {
                    System.out.println("Invalid Option. Please choose a valid entrance.");
                    return;
                } else {
                    System.out.println("Entered \"" + chooseDivisao.getNome() + "\"");
                    chooseDivisao.addCharacter(this.jogo.getPlayer());
                }
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine();//limpar o buffer
                return;
            }

            game(chooseDivisao);

            System.out.println("Simulation Ended");
        }
    }

    /**
     * Executes the game starting from a given divison
     *
     * @param currentDivisao the starting division of the game
     */
    private void game(Divisao currentDivisao) {
        Scanner input = new Scanner(System.in);
        boolean itemOnRoom = false;
        boolean useItem = false;
        boolean targetFound = false;

        while (true) {
            System.out.println("\nCurrent Division: " + currentDivisao.getNome());
            this.jogo.addPath(currentDivisao);
            System.out.println("\nPLAYER FASE: \n");

            if (currentDivisao.isEntradaSaida()) {
                System.out.println("Do you want to exit the building? (Y/N)");
                String answer = input.next();
                if (answer.equalsIgnoreCase("Y")) {
                    System.out.println("Exiting the building...");

                    if (targetFound) {
                        System.out.println("Mission accomplished!");
                        Iterator<Missao> missaoIterator = this.jogo.getMissaoIterator();
                        if (missaoIterator.hasNext()) {
                            Missao missao = missaoIterator.next();
                            int vidaRestante = this.jogo.getPlayer().getVida();
                            int versao = missao.getVersao();
                            this.jogo.addResult(new MissionResult(versao, MissionResultEnum.SUCCESS, vidaRestante));
                        }
                    } else {
                        System.out.println("Mission failed!");

                        Iterator<Missao> missaoIterator = this.jogo.getMissaoIterator();
                        if (missaoIterator.hasNext()) {
                            Missao missao = missaoIterator.next();
                            int vidaRestante = this.jogo.getPlayer().getVida();
                            int versao = missao.getVersao();
                            this.jogo.addResult(new MissionResult(versao, MissionResultEnum.FAILURE, vidaRestante));
                        }

                    }
                    return;
                }
            }

            itemOnRoom = false;

            if (this.jogo.getPlayer().getVida() < 80) {
                if (this.jogo.getPlayer().getNumberOfItems() > 0) {
                    System.out.println("You have " + this.jogo.getPlayer().getNumberOfItems() + " items in your backpack.");
                    System.out.println("Do you want to use any of them? (Y/N)");
                    String answer = input.next();

                    if (answer.equalsIgnoreCase("Y")) {
                        this.jogo.getPlayer().useItem();
                        useItem = true;
                    }
                }
            }

            if (currentDivisao.getItem() != null && !currentDivisao.getItem().getTipo().isEmpty()) {
                System.out.println("On this division you have a " + currentDivisao.getItem().getTipo());
                itemOnRoom = true;
            }

            if (itemOnRoom) {
                if (currentDivisao.getItem().getTipo().equals("colete")) {
                    this.jogo.getPlayer().setVida(this.jogo.getPlayer().getVida() + currentDivisao.getItem().getPontos());
                } else {
                    this.jogo.getPlayer().addItemToMochila(currentDivisao.getItem());
                }

                currentDivisao.removeItem();
            }

            if (currentDivisao.getInimigos() != null && !currentDivisao.getInimigos().isEmpty()) {
                System.out.println("You encounter " + currentDivisao.getInimigos().size() + " enemies in this division!");

                if (!useItem) {
                    handleEnemies(currentDivisao);
                }

                if (!currentDivisao.getInimigos().isEmpty()) {
                    System.out.println("ENEMY FASE: \n");
                    System.out.println("*Enemies moved out!*");

                    enemyMovement(currentDivisao);

                    Iterator<Character> characterIterator = currentDivisao.getInimigos().iterator();
                    while (characterIterator.hasNext()) {
                        Character currentCharacter = characterIterator.next();
                        this.jogo.getPlayer().receberDano(currentCharacter.getPoder());
                    }

                    if (this.jogo.getPlayer().getVida() <= 0) {
                        System.out.println("You have been defeated!");
                        return;
                    }
                } else {
                    System.out.println("You defeated all enemies in this division!");
                }
            } else {
                System.out.println("ENEMY FASE: \n");
                enemyMovement(currentDivisao);
            }

            if (currentDivisao.getInimigos().isEmpty()) {
                if (!currentDivisao.getAlvo().getTipo().isEmpty()) {
                    System.out.println("You found the target!");
                    currentDivisao.removeTarget();
                    targetFound = true;
                }

                System.out.println("\nShortest Path to target: \n");
                showShortestPathToTarget(currentDivisao);

                System.out.println("\nShortest Path to nearest Medkit: \n");
                showShortestPathToMedkit(currentDivisao);

                System.out.println("\nChoose your next move: ");
                printPossibleMoves(currentDivisao);

                try {
                    int moveChoice = input.nextInt();

                    if (moveChoice == 0) {
                        System.out.println("Exiting the building...");
                        return;
                    }

                    System.out.println("PLAYER FASE: \n");

                    Divisao nextDivisao = getDivisionByIndex(currentDivisao, moveChoice);

                    if (nextDivisao != null) {
                        currentDivisao = nextDivisao;
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                }
            }
        }
    }

    /**
     * Handles the movement logic of the enemies in the building
     *
     * @param currentDivisao the current division
     */
    private void enemyMovement(Divisao currentDivisao) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current.equals(currentDivisao)) {
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
     * Handles interactions between the player and the enemies in the current division
     *
     * @param divisao the division where the interaction takes place
     */

    private void handleEnemies(Divisao divisao) {
        Iterator<Character> iterator = divisao.getInimigos().iterator();
        int initialDivisionEnemies = divisao.getInimigos().size();

        while (iterator.hasNext()) {
            Character current = iterator.next();

            if (current instanceof Inimigo) {
                Inimigo inimigo = (Inimigo) current;
                Player player = this.jogo.getPlayer();

                inimigo.receberDano(player.getPoder());


                if (inimigo.getVida() <= 0) {
                    try {
                        divisao.removeCharacter(inimigo);
                    } catch (ElementNotFoundException ex) {
                        System.out.println("Enemy not found. Error!");
                    }

                    System.out.println(inimigo.getNome() + " has been defeated!");
                }
            }
        }

        System.out.println("You defeated " + (initialDivisionEnemies - divisao.getInimigos().size()) + " enemies in this division!");

    }


    /**
     * Shows the shortest path to the target from the starting division
     *
     * @param startDivision the starting division
     */

    private void showShortestPathToTarget(Divisao startDivision) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator(startDivision);
        Divisao alvoDivision = null;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (current.getAlvo() != null && current.getAlvo().getTipo().equals("quimico")) {
                alvoDivision = current;
                break;
            }
        }

        if (alvoDivision == null) {
            System.out.println("No target found or already picked up.");
            return;
        }

        Iterator<Divisao> iteratorShortestPath = this.jogo.getEdificio().getDivisoes().iteratorShortestPath(startDivision, alvoDivision);

        boolean hasPath = false;

        while (iteratorShortestPath.hasNext()) {
            Divisao current = iteratorShortestPath.next();
            if (current != null) {
                System.out.print(current.getNome() + " --> ");
                hasPath = true;
            }
        }

        if (!hasPath) {
            System.out.println("You have an item in this room.");
        } else {
            System.out.println("END");
        }
    }

    /**
     * Shows the shortest path to a medkit from the starting division
     *
     * @param startDivision the starting division
     */

    private void showShortestPathToMedkit(Divisao startDivision) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator(startDivision);
        Divisao alvoDivision = null;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (current.getItem() != null && current.getItem().getTipo().equals("kit de vida")) {
                alvoDivision = current;
                break;
            }
        }

        if (alvoDivision == null) {
            System.out.println("No medkit found or already picked up.");
            return;
        }

        Iterator<Divisao> iteratorShortestPath = this.jogo.getEdificio().getDivisoes().iteratorShortestPath(startDivision, alvoDivision);

        boolean hasPath = false;

        while (iteratorShortestPath.hasNext()) {
            Divisao current = iteratorShortestPath.next();
            if (current != null) {
                System.out.print(current.getNome() + " --> ");
                hasPath = true;
            }
        }

        if (!hasPath) {
            System.out.println("No path available.");
        } else {
            System.out.println("END");
        }
    }

    /**
     * Gets a division by its index among the adjacent division of the current division
     *
     * @param currentDivisao the current division
     * @param index          the index of the desired adjacent division
     * @return the division corresponding to the index, or null if not found
     */

    private Divisao getDivisionByIndex(Divisao currentDivisao, int index) {
        UnorderedListADT<Divisao> adjacentDivisions = this.jogo.getEdificio().getAdjacentDivisions(currentDivisao);

        if (index < 1 || index > adjacentDivisions.size()) {
            return null;
        }

        int i = 1;
        while (adjacentDivisions != null && !adjacentDivisions.isEmpty()) {
            Iterator<Divisao> adjIterator = adjacentDivisions.iterator();
            while (adjIterator.hasNext()) {
                Divisao adjacentDivision = adjIterator.next();
                if (i == index) {
                    return adjacentDivision;
                }
                i++;
            }
        }
        return null;
    }
}
