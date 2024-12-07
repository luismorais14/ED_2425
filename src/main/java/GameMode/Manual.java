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

    public void startGame() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 3) {
            inputNum = 0;
            System.out.println("\nChoose the entrance through which you want to enter the building: ");
            listEntradaSaida();
            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine(); //limpar o buffer
            }

            switch (inputNum) {
                case 1:
                    System.out.println("Entered \"Heliport\"");

                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }
}
