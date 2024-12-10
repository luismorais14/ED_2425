package GameMode;

import core.Divisao;
import core.Inimigo;
import core.Jogo;
import core.Character;


import java.util.Iterator;

public class Automatic {
    private Jogo jogo;

    public Automatic() {
        jogo = new Jogo();
    }

    public Automatic(Jogo jogo) {
        this.jogo = jogo;
    }

    private Divisao startDivision() {
        Iterator<Divisao> it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());
        Divisao start = null;
        int minDamage = Integer.MIN_VALUE;

        while (it.hasNext()) {
            Divisao div = it.next();
            if (div.isEntradaSaida() && div.getInimigos().isEmpty()) {
                start = div;
                break;
            }
        }

        if (start == null) {
            it = jogo.getEdificio().getDivisoes().iteratorBFS(jogo.getEdificio().getStartVertex());

            while (it.hasNext()) {
                Divisao div = it.next();
                if (div.isEntradaSaida()) {
                    int damage = 0;
                    Iterator<Character> inimigosIterator = div.getInimigos().iterator();

                    while (inimigosIterator.hasNext()) {
                        Inimigo inimigo = (Inimigo) inimigosIterator.next();
                        damage += inimigo.getPoder();
                    }

                    if (damage < minDamage) {
                        minDamage = damage;
                        start = div;
                    }
                }
            }
        }
        return start;
    }

    public void startGame() {
        Divisao start = startDivision();
        System.out.println("Starting automatic simulation: \n");

        System.out.println("Starting division: " + start.getNome());


    }



}
