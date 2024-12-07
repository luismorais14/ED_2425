package core;

import ADT.StackADT;
import implementations.LinkedStack;

public class Player extends Character implements Attack{
    private final static int DEFAULT_VIDA = 100;

    private StackADT<Item> mochila;

    /**
     * Creates an empty player
     */
    public Player() {
        super("Tó Cruz", 0, DEFAULT_VIDA);
        mochila = new LinkedStack<Item>();
    }

    /**n
     * Creates a player specifying the power
     * @param poder the player's power
     */
    public Player(int poder) {
        super("Tó Cruz", poder, DEFAULT_VIDA);
        mochila = new LinkedStack<Item>();
    }

    /**
     * Performs an attack
     */
    @Override
    public void attack(Character alvo) {
        alvo.receberDano(getPoder());
    }
}
