package GameMode;

import ADT.UnorderedListADT;
import core.*;
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

        if (start == null) {
            System.out.println("Not valid starting division.");
            return;
        }
        System.out.println("Starting automatic simulation: \n");
        System.out.println("Starting division: " + start.getNome());

        game(start);
    }

    private void game(Divisao start) {
        Divisao currentDivision = start;
        boolean targFound = false;

        while (true) {
            System.out.println("\n Current Division:" + currentDivision.getNome());



        }

    }

    private void dijkstraAlgorithm() {

    }

    private void showOptimalPath(Divisao div) {
        Iterator<Divisao> divIterator = this.jogo.getEdificio().getDivisoesIterator(div);
        Divisao alvoDivision = null;

        while (divIterator.hasNext()) {
            Divisao div2 = divIterator.next();
            if (div2.getAlvo() != null && div2.getAlvo().getTipo().equals("quimico")) {
                alvoDivision = div2;
            }
        }
    }


    private void handleItems(Divisao div) {
        if (div.getItem() != null && !div.getItem().getTipo().isEmpty()) {
            System.out.println("Item encontrado: " + div.getItem().getTipo());
            if (div.getItem().getTipo().equals("colete")) {
                jogo.getPlayer().setVida(jogo.getPlayer().getVida() + div.getItem().getPontos());
            } else {
                jogo.getPlayer().addItemToMochila(div.getItem());
            }
            div.removeItem();
        }
    }

    private boolean handleTarget(Divisao div) {
        if (div.getAlvo() != null && !div.getAlvo().getTipo().isEmpty()) {
            System.out.println("Alvo encontrado nesta divisão, corra!");
            div.removeTarget();
            return true;
        }
        return false;
    }

    private boolean handleEnemies(Divisao div) {
        int totalDmg = 0;
        Iterator<Character> inimigosIterator = div.getInimigos().iterator();

        while (inimigosIterator.hasNext()) {
            Inimigo inimigo = (Inimigo) inimigosIterator.next();
            totalDmg += inimigo.getPoder();
            System.out.println("A lutar contra o inimigo: " + inimigo.getNome() + "Dano:" + inimigo.getPoder());
        }

        jogo.getPlayer().setVida(jogo.getPlayer().getVida() - totalDmg);

        System.out.println("Dano total recebido: " + totalDmg);
        System.out.println("Vida restante: " + jogo.getPlayer().getVida());

        if (jogo.getPlayer().getVida() <= 0) {
            return false;
        }

        return true;
    }


    private void enemiesMovement(Divisao div) {
        Iterator<Divisao> iterator = this.jogo.getEdificio().getDivisoesIterator(div);

        while (iterator.hasNext()) {
            Divisao current = iterator.next();

            if (current != null && !current.equals(div)) {
                Iterator<Character> enemyIterator = current.getInimigos().iterator();

                while (enemyIterator.hasNext()) {
                    Character enemy = enemyIterator.next();

                    UnorderedListADT<Divisao> adjacentDivisions = this.jogo.getEdificio().getAdjacentDivisions(current);

                    if (!adjacentDivisions.isEmpty()) {
                        int size = 0;
                        Iterator<Divisao> adjIterator = adjacentDivisions.iterator();
                        while (adjIterator.hasNext()) {
                            adjIterator.next();
                            size++;
                        }

                        int randomIndex = (int) (Math.random() * size);

                        adjIterator = adjacentDivisions.iterator();
                        Divisao newDivision = null;
                        for (int i = 0; i <= randomIndex; i++) {
                            newDivision = adjIterator.next();
                        }

                        try {
                            current.removeCharacter(enemy);
                            newDivision.addCharacter(enemy);
                        } catch (Exception e) {
                            System.out.println("Enemy not found.");
                        }
                    }
                }
            }
        }
    }

    private Divisao findNextMove(Divisao currentDiv, boolean targFound) {
        UnorderedListADT<Divisao> adjacentDivision = jogo.getEdificio().getAdjacentDivisions(currentDiv);
        Iterator<Divisao> neighbours = adjacentDivision.iterator();
        Divisao bestChoice = null;
        int maxPoints = Integer.MIN_VALUE;

        while (neighbours.hasNext()) {
            Divisao neighbour = neighbours.next();
            if (neighbour == null)
                continue;
            int divisionPoint = calculateDivisionValue(neighbour);
            if (divisionPoint > maxPoints) {
                maxPoints = divisionPoint;
                bestChoice = neighbour;
            }
        }
        return bestChoice;
    }


    private int calculateDivisionValue(Divisao div) {
        int points = 0;

        Iterator<Character> inimigosIterator = div.getInimigos().iterator();

        while (inimigosIterator.hasNext()) {
            Inimigo inimigo = (Inimigo) inimigosIterator.next();
            points += inimigo.getPoder();
        }

        if (div.getItem() != null) {
            points -= div.getItem().getPontos();
        }
        return points;
    }


}
