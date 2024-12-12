package core;

public class Inimigo extends Character implements Attack {

    /**
     * Default constructor for creating a new instance of the enemy with default attributes.
     * This constructor calls the superclass's default constructor to initialize the enemy's
     * attributes with default values.
     */
    public Inimigo() {
        super();
    }


    /**
     * Constructor for creating a new instance of the enemy with the specified name, power, and life points.
     * This constructor allows you to initialize the `Inimigo` with custom values for its attributes.
     *
     * @param nome  the name of the enemy character
     * @param poder the power of the enemy, representing its attack strength
     * @param vida  the life points of the enemy, representing its health
     */
    public Inimigo(String nome, int poder, int vida) {
        super(nome, poder, vida);
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

}
