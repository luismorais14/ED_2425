package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class Divisao {
    private String nome;
    private UnorderedListADT<Character> character;
    private Alvo alvo;
    private Item item;
    private boolean isEntradaSaida;


    /**
     * Creates an empty division
     */
    public Divisao() {
        this.nome = "";
        this.character = new ArrayUnorderedList<>();
        this.alvo = new Alvo();
        this.item = new Item();
        this.isEntradaSaida = false;
    }

    /**
     * Creates a division specifying the name, enemy, target and item
     *
     * @param nome    name of the division
     * @param character enemy present on the division
     * @param alvo    target (if exists) on the division
     * @param item    item (if exists) on the division
     */
    public Divisao(String nome, UnorderedListADT<Character> character, Alvo alvo, Item item) {
        this.nome = nome;
        this.character = character;
        this.alvo = alvo;
        this.item = item;
        this.isEntradaSaida = false;
    }

    /**
     * Getter for the division name
     *
     * @return the divisio name
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter for the division name
     *
     * @param nome the division name
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Adds an enemy on the division
     *
     * @param character the enemy to be added
     */
    public void addCharacter(Character character) {
        this.character.addToFront(character);
    }

    /**
     * Getter for the target on the division
     *
     * @return the target on the division
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Setter for the target on the division
     *
     * @param alvo the target on the division
     */
    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    /**
     * Getter for the item on the division
     *
     * @return the item on the division
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter for the item on the division
     *
     * @param item the item on the division
     */
    public void setItem(Item item) {
        this.item = item;
    }


    public boolean isEntradaSaida() {
        return isEntradaSaida;
    }

    public void setEntradaSaida(boolean isEntradaSaida) {
        this.isEntradaSaida = isEntradaSaida;
    }

    public int getNumCharacters() {
        return character.size();
    }
}
