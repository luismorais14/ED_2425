package core;

public class Missao {
    private String codMissao;
    private Alvo alvo;
    private int versao;
    private String chave;

    public String getCodMissao() {
        return codMissao;
    }

    public void setCodMissao(String codMissao) {
        this.codMissao = codMissao;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
