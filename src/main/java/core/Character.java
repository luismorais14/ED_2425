package core;

public class Character {
    private String nome;
    private int poder;
    private int vida;

    /**
     * Creates an empty enemy
     */
    public Character() {
        this.nome = "";
        this.poder = 0;
        this.vida = 0;
    }

    /**
     * Creates an empty enemy specifying the name and the power
     *
     * @param nome  the enemy name
     * @param poder the enemy power
     */
    public Character(String nome, int poder, int vida) {
        this.nome = nome;
        this.poder = poder;
        this.vida = vida;
    }

    /**
     * Setter for the enemy life points
     *
     * @param vida of the enemy
     */

    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Getter for the life points
     *
     * @return the life points of the enemy
     */
    public int getVida() {
        return vida;
    }

    /**
     * Getter for the enemy name
     *
     * @return the enemy name
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter for the enemy name
     *
     * @param nome the enemy name
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter for the enemy power
     *
     * @return the enemy power
     */
    public int getPoder() {
        return poder;
    }

    /**
     * Setter for the enemy power
     *
     * @param poder the enemmy power
     */
    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void receberDano(int dano) {
        this.vida -= dano;

        if (this.vida <= 0) {
            this.vida = 0;
        }
    }
}
