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

    private void difficultyMenu() {
        Scanner input = new Scanner(System.in);
        boolean aux = false;
        int inputNum = 0;
        String lixo = "";

        while (!aux || inputNum != 4) {
            inputNum = 0;
            System.out.println("=========================================================");
            System.out.println("|       Please choose simulation difficulty:            |");
            System.out.println("=========================================================");
            System.out.println("| Options:                                              |");
            System.out.println("|                     1. Easy                           |");
            System.out.println("|                     2. Normal                         |");
            System.out.println("|                     3. God Mode                       |");
            System.out.println("|                     4. Go Back                       |");
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
                    //TODO instanciar jogo, colocando o tocruz com grande poder de ataque
                    break;
                case 2:
                    //TODO instanciar jogo, reduzindo o poder de ataque do tocruz para um pouco menos do que o facil
                    break;
                case 3:
                    //TODO instanciar jogo, colocando o poder de ataque do tocruz para bastante reduzido
                    break;
                case 4:
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
                    difficultyMenu();
                    break;
                case 2:
                    //TODO instanciar jogo, reduzindo o poder de ataque do tocruz para um pouco menos do que o facil
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid Option");
                    break;
            }
        }
    }
}
