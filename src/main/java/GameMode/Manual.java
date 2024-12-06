package GameMode;

import core.Jogo;

import java.util.Scanner;

public class Manual {
    private Jogo jogo;

    public Manual() {
        jogo = new Jogo();
    }

    public Manual(Jogo jogo) {
        this.jogo = jogo;
    }

    public void startGame() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 3) {
            inputNum = 0;
            System.out.println("Choose the entrance through which you want to enter the building: ");

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
