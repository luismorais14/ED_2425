package core;

import ADT.StackADT;
import implementations.LinkedStack;

public class ToCruz {
    private int vida;
    private StackADT<Item> mochila;
    private int poder;

    /**
     * Creates an empty player
     */
    public ToCruz() {
        this.vida = 100;
        mochila = new LinkedStack<Item>();
        this.poder = 0;
    }

    /**
     * Creates a player specifying the power
     * @param poder the player's power
     */
    public ToCruz (int poder) {
        this.vida = 100;
        mochila = new LinkedStack<Item>();
        this.poder = poder;
    }

    /**
     * Getter for the player's hit points
     * @return the player's hit points
     */
    public int getVida() {
        return vida;
    }

    /**
     * Setter for the player's hit points
     * @param vida the player's hit points
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Getter for the player's power
     * @return the player's power
     */
    public int getPoder() {
        return poder;
    }

    /**
     * Setter for the player's power
     * @param poder the player's power
     */
    public void setPoder(int poder) {
        this.poder = poder;
    }

}
