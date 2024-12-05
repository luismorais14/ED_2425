package Menus;

import core.Jogo;
import io.JSONHandler;

import java.util.Scanner;

public class Menus {
    private Jogo jogo;
    private JSONHandler handler;

    /**
     * Instantiates the objects required by the program
     */
    public void menu() {
        this.jogo = new Jogo();
        this.handler = new JSONHandler();
    }

    public void mainMenu() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 2) {
            inputNum = 0;
            System.out.println("=========================================================");
            System.out.println("|       Improbable Mission Force Simulation Test        |");
            System.out.println("=========================================================");
            System.out.println("| Options:                                              |");
            System.out.println("|                     1. Start Simulation               |");
            System.out.println("|                     2. Exit                           |");
            System.out.println("=========================================================");
            System.out.println("Enter your option: ");

            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine(); //limpar o buffer
            }

            switch (inputNum) {
                case 1:
                    startSimulationMenu();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private void startSimulationMenu() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 2) {
            inputNum = 0;
            System.out.println("=========================================================");
            System.out.println("|       Improbable Mission Force Simulation Test        |");
            System.out.println("=========================================================");
            System.out.println("| Options:                                              |");
            System.out.println("|                     1. Start Simulation               |");
            System.out.println("|                     2. Exit                           |");
            System.out.println("=========================================================");
            System.out.println("Enter your option: ");

            try {
                inputNum = input.nextInt();
                aux = true;
            } catch (Exception e) {
                System.out.println("Invalid Option");
                lixo = input.nextLine(); //limpar o buffer
            }
    }
}
