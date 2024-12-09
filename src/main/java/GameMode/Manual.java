package GameMode;

import ADT.ListADT;
import ADT.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import core.*;
import core.Character;
import implementations.ArrayUnorderedList;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class Manual {
    private Jogo jogo;

    public Manual() {
        jogo = new Jogo();
    }

    public Manual(Jogo jogo) {
        this.jogo = jogo;
    }

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

    public void startGame() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";
        Divisao chooseDivisao = null;

        while (!aux || inputNum != 3) {
            inputNum = 0;
            System.out.println("\nChoose the entrance through which you want to enter the building: ");
            listEntradaSaida();
            try {
                inputNum = input.nextInt();
                chooseDivisao = getDivisaoByIndex(inputNum);
                aux = true;

                if (chooseDivisao == null) {
                    System.out.println("Invalid Option. Please choose a valid entrance.");
                } else {
                    System.out.println("Entered \"" + chooseDivisao.getNome() + "\"");
                    chooseDivisao.addCharacter(this.jogo.getPlayer());
                }
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine(); //limpar o buffer
            }

            game(chooseDivisao);
        }
    }

    private void game(Divisao currentDivisao) {
        Scanner input = new Scanner(System.in);
        boolean itemOnRoom = false;

        while (true) {
            System.out.println("\nCurrent Division: " + currentDivisao.getNome());


            if (currentDivisao.getInimigos() != null && !currentDivisao.getInimigos().isEmpty()) {
                System.out.println("You encounter " + currentDivisao.getInimigos().size() + " enemies in this division!");
                if (currentDivisao.getItem() != null && !currentDivisao.getItem().getTipo().isEmpty()) {
                    System.out.println("On this division you have a " + currentDivisao.getItem().getTipo());
                    itemOnRoom = true;
                }

                if (itemOnRoom) {
                    this.jogo.getPlayer().addItemToMochila(currentDivisao.getItem());
                }

                handleEnemies(currentDivisao);

            }

            if (!currentDivisao.getAlvo().getTipo().isEmpty()) {
                System.out.println("You found the target!");
                return;
            }

            System.out.println("\nShortest Path to target: \n");
            showShortestPathToTarget(currentDivisao);

            System.out.println("\nShortest Path to nearest Medkit: \n");
            showShortestPathToMedkit(currentDivisao);

            enemyMovement();

            System.out.println("\nChoose your next move: ");

            try {
                int moveChoice = input.nextInt();
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

    private void enemyMovement() {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current != null && ((current.getNumCharacters() - current.getInimigos().size()) == 0)) {
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

    private void showShortestPathToTarget(Divisao startDivision) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();
        Divisao alvoDivision = null;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (!current.getAlvo().getTipo().isEmpty()) {
                alvoDivision = current;
                break;
            }
        }

        if (alvoDivision == null) {
            System.out.println("No target found.");
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

    private void showShortestPathToMedkit(Divisao startDivision) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator();
        Divisao alvoDivision = null;

        while (iterator.hasNext()) {
            Divisao current = iterator.next();
            if (current.getItem().getTipo().equals("kit de vida")) {
                alvoDivision = current;
                break;
            }
        }

        if (alvoDivision == null) {
            System.out.println("No target found.");
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

    private Divisao getDivisionByIndex(Divisao currentDivisao, int index) {
        // Implement logic to fetch a division by its index
        return null;
    }
}
