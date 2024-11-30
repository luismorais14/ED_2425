package core;

public class Jogo {
    private Missao missao;
    private TiposSimulacao tipoSimulacao;
    private Edificio edificio;

    public Jogo() {
        this.missao = new Missao();
        this.tipoSimulacao = null;
        this.edificio = new Edificio();
    }

    public Jogo(Missao missao, TiposSimulacao tipoSimulacao, Edificio edificio) {
        this.missao = missao;
        this.tipoSimulacao = tipoSimulacao;
        this.edificio = edificio;
    }

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    public TiposSimulacao getTipoSimulacao() {
        return tipoSimulacao;
    }

    public void setTipoSimulacao(TiposSimulacao tipoSimulacao) {
        this.tipoSimulacao = tipoSimulacao;
    }

    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }
}
