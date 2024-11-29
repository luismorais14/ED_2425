package core;

public class Jogo {
    private Missao missao;
    private TiposSimulacao tipoSimulacao;
    private Edificio edificio;

    public Jogo() {
        this.missao = new Missao();
        this.tipoSimulacao = null;
    }

    public Jogo(Missao missao, TiposSimulacao tipoSimulacao) {
        this.missao = missao;
        this.tipoSimulacao = tipoSimulacao;
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
