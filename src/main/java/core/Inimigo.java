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
     */
    @Override
    public void attack(Character alvo) {
        alvo.receberDano(getPoder());
    }

}
