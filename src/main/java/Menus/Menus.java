package Menus;

import Exceptions.JogoException;
import GameMode.Manual;
import core.Jogo;
import io.JSONHandler;

import java.util.Scanner;

public class Menus {
    private Jogo jogo;
    private JSONHandler handler;
    private Manual manual;

    /**
     * Instantiates the objects required by the program
     */
    public Menus() throws JogoException {
        this.jogo = new Jogo();
        this.handler = new JSONHandler();
        this.handler.importData(jogo);
        this.manual = new Manual(jogo);
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
                    simulationTypeMenu();
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    private void simulationTypeMenu() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 3) {
            inputNum = 0;
            System.out.println("=========================================================");
            System.out.println("|       Choose the type of simulation:                  |");
            System.out.println("=========================================================");
            System.out.println("| Options:                                              |");
            System.out.println("|                     1. Manual                         |");
            System.out.println("|                     2. Auto                           |");
            System.out.println("|                     3. Go Back                        |");
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
                    showMap();
                    manual.startGame();
                    break;
                case 2:
                    showMap();
                    //TODO implementar modo automatico do jogo
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }

    /**
     * Shows the map in the console
     */
    private void showMap() {
        String[] map = {
                "           |-----------------------------------------------------------------------------------|",
                "           |           |                         Heliporto                                     |",
                "           |  Escada 6 |-----------------------------------------------------------------------|",
                "           |           |        Camaratas      |          |          |           Armazem       |",
                "           |-----------------------------------|          | Escada 5 |--------------|----------|",
                "           |             Laboratorio           |          |          | Escritorio 3 |          |",
                "           |------------------------------------------------------------------------| Escada 4 |",
                "           |     WC    |                    Corredor 2                              |          |",
                "           |-----------------------------------------------------------------------------------|",
                "           |             Seguranca             |           Escritorio 3             |          |",
                "|----------|------------------------------------------------------------------------|          |",
                "|          |             Escritorio 1          |           Escritorio 2             | Escada 3 |",
                "|          |------------------------------------------------------------------------|          |",
                "|  Escada  |           |                    Corredor 1                              |          |",
                "|    de    |  Escada 2 |-----------------------------------------------------------------------|",
                "|Emergencia|           |                    Porteiro                                |          |",
                "|          |------------------------------------------------------------------------| Escada 1 |",
                "|          |                                Garagem                                 |          |",
                "|----------|-----------------------------------------------------------------------------------|",
        };

        for (String linha : map) {
            System.out.println(linha);
        }
    }
}

