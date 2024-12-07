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
        while (this.jogo.getEdificio().getDivisoesIterator().hasNext()) {
            Divisao current = null;
            if (this.jogo.getEdificio().getDivisoesIterator().next() instanceof Divisao) {
                current = (Divisao) this.jogo.getEdificio().getDivisoesIterator().next();
            }

            if (current != null)
                System.out.println(current.getNome());
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
        }

    }
}
