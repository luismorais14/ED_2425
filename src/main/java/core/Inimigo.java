package core;

public class Inimigo extends Character implements Attack {
    public Inimigo(){
        super();
    }

    public Inimigo(String nome, int poder, int vida) {
        super(nome, poder, vida);
    }

    /**
     * Performs an attack
     * @param alvo the attack target
     */
    @Override
    public void attack(Character alvo) {
        alvo.receberDano(getPoder());
    }

}
