package core;

import ADT.StackADT;
import implementations.LinkedStack;

public class Player extends Character implements Attack {
    private final static int DEFAULT_VIDA = 100;

    private StackADT<Item> mochila;

    /**
     * Creates an empty player
     */
    public Player() {
        super("Tó Cruz", 0, DEFAULT_VIDA);
        mochila = new LinkedStack<Item>();
    }

    /**
     * n
     * Creates a player specifying the power
     *
     * @param poder the player's power
     */
    public Player(int poder) {
        super("Tó Cruz", poder, DEFAULT_VIDA);
        mochila = new LinkedStack<Item>();
    }

    /**
     * Performs an attack
     *
     * @param alvo the attack target
     */
    @Override
    public void attack(Character alvo) {
        alvo.receberDano(getPoder());
    }

    /**
     * Adds an item to his packpack
     *
     * @param item the item to be added
     */
    public void addItemToMochila(Item item) {
        mochila.push(item);
    }

    /**
     * Retrieves the number of items currently in the backpack.
     *
     * @return The number of items in the backpack.
     */
    public int getNumberOfItems() {
        return mochila.size();
    }

    /**
     * Displays all items currently in the backpack
     */
    public void showItems() {
        System.out.println("Items on the backpack: ");

        while (!mochila.isEmpty()) {
            System.out.println(mochila.pop().toString());
        }
    }

    /**
     * Uses an item from the backpack to restore the player's health.
     * If the backpack is empty, displays a message indicating no items are available.
     */
    public void useItem() {
        if (!mochila.isEmpty()) {
            Item item = mochila.pop();

            this.setVida(this.getVida() + item.getPontos());

            if (this.getVida() >= 100) {
                this.setVida(100);
            }

            System.out.println("Using item: " + item.getTipo().toString());
        } else {
            System.out.println("No items on the backpack");
        }
    }
}
