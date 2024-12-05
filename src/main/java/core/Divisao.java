package core;

import ADT.UnorderedListADT;
import implementations.ArrayUnorderedList;

public class Divisao {
    private String nome;
    private UnorderedListADT<Inimigo> inimigo;
    private Alvo alvo;
    private Item item;

    /**
     * Creates an empty division
     */
    public Divisao() {
        this.nome = "";
        this.inimigo = new ArrayUnorderedList<Inimigo>();
        this.alvo = new Alvo();
        this.item = new Item();
    }

    /**
     * Creates a division specifying the name, enemy, target and item
     * @param nome name of the division
     * @param inimigo enemy present on the division
     * @param alvo target (if exists) on the division
     * @param item item (if exists) on the division
     */
    public Divisao(String nome, UnorderedListADT<Inimigo> inimigo, Alvo alvo, Item item) {
        this.nome = nome;
        this.inimigo = inimigo;
        this.alvo = alvo;
        this.item = item;
    }

    /**
     * Getter for the division name
     * @return the divisio name
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter for the division name
     * @param nome the division name
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Setter for the enemy on the division
     * @param inimigo the enemy on the devision
     */
    public void setInimigo(Inimigo inimigo) {
        this.inimigo.addToFront(inimigo);
    }

    /**
     * Getter for the target on the division
     * @return the target on the division
     */
    public Alvo getAlvo() {
        return alvo;
    }

    /**
     * Setter for the target on the division
     * @param alvo the target on the division
     */
    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    /**
     * Getter for the item on the division
     * @return the item on the division
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter for the item on the division
     * @param item the item on the division
     */
    public void setItem(Item item) {
        this.item = item;
    }
}
