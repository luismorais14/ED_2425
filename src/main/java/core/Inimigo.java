package core;

public class Inimigo extends Character implements Attack {
    public Inimigo(){
        super();
    }

    public Inimigo(String nome, int poder, int vida) {
        super(nome, poder, vida);
    }

}
