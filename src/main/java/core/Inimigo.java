package core;

public class Inimigo {
    private String nome;
    private int poder;

    /**
     * Creates an empty enemy
     */
    public Inimigo() {
        this.nome = "";
        this.poder = 0;
    }

    /**
     * Creates an empty enemy specifying the name and the power
     * @param nome the enemy name
     * @param poder the enemy power
     */
    public Inimigo(String nome, int poder) {
        this.nome = nome;
        this.poder = poder;
    }

    /**
     * Getter for the enemy name
     * @return the enemy name
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter for the enemy name
     * @param nome the enemy name
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter for the enemy power
     * @return the enemy power
     */
    public int getPoder() {
        return poder;
    }

    /**
     * Setter for the enemy power
     * @param poder the enemmy power
     */
    public void setPoder(int poder) {
        this.poder = poder;
    }

}
