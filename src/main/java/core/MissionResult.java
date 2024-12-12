package core;

public class MissionResult implements Comparable<MissionResult> {
    private int versao;
    private MissionResultEnum result;
    private int vidaRestante;

    public MissionResult() {
        this.versao = 0;
        this.result = null;
        this.vidaRestante = 0;
    }

    public MissionResult(int versao, MissionResultEnum result, int vidaRestante) {
        this.versao = versao;
        this.result = result;
        this.vidaRestante = vidaRestante;
    }

    public int getVersao() {
        return versao;
    }

    public int getPontosVidaRestantes() {
        return this.vidaRestante;
    }

    @Override
    public int compareTo(MissionResult o) {
        return Integer.compare(this.vidaRestante, o.vidaRestante);
    }

    @Override
    public String toString() {
        return "\nVersion: " + this.versao +
                "\nResult: " + this.result +
                "\nRemaining Life Points: " + this.vidaRestante;
    }
}
