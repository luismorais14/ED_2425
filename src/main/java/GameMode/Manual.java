package GameMode;

import core.Divisao;
import core.Jogo;

import java.util.Iterator;
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
                }
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine(); //limpar o buffer
            }

            switch (inputNum) {
                case 1:
                    System.out.println("Entered \"Heliporto\"");

                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private void game(Divisao currentDivisao) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\nCurrent Division: " + currentDivisao.getNome());

            if (currentDivisao.getNumCharacters() != null && !currentDivisao.getInimigos().isEmpty()) {
                handleEnemies(currentDivisao);
            }

            if (currentDivisao.getAlvo() != null) {
                System.out.println("You found the target! Mission successful.");
                return;
            }

            System.out.println("\nChoose your next move:");
            listConnectedDivisions(currentDivisao);

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

    private void handleEnemies(Divisao divisao) {
        System.out.println("You encounter enemies in this division!");
        // Calculate and handle combat here, reducing the player's health and removing enemies if defeated
    }

    private void listConnectedDivisions(Divisao currentDivisao) {
        // Implement logic to list connected divisions
    }

    private Divisao getDivisionByIndex(Divisao currentDivisao, int index) {
        // Implement logic to fetch a division by its index
        return null;
    }
}
