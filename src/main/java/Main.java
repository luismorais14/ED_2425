import Menus.Menus;

public class Main {
    public static void main(String[] args) {
        try {
            Menus menu = new Menus();

            menu.mainMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
