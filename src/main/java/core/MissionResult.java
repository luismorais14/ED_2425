package core;

public class MissionResult implements Comparable<MissionResult> {
    private int versao;
    private MissionResultEnum result;
    private int vidaRestante;

    /**
     * Creates an empty mission result with default values.
     * The version is set to 0, the result is set to null, and the remaining life points are set to 0.
     */
    public MissionResult() {
        this.versao = 0;
        this.result = null;
        this.vidaRestante = 0;
    }

    /**
     * Creates a mission result specifying the version, result status, and remaining life points.
     *
     * @param versao       the version of the mission result
     * @param result       the result of the mission (MissionResultEnum)
     * @param vidaRestante the remaining life points after the mission
     */
    public MissionResult(int versao, MissionResultEnum result, int vidaRestante) {
        this.versao = versao;
        this.result = result;
        this.vidaRestante = vidaRestante;
    }

    /**
     * Getter for the mission result version.
     *
     * @return the version of the mission result
     */
    public int getVersao() {
        return versao;
    }

    /**
     * Getter for the remaining life points after the mission.
     *
     * @return the remaining life points
     */
    public int getPontosVidaRestantes() {
        return this.vidaRestante;
    }

    /**
     * Compares this mission result to another based on the remaining life points.
     *
     * @param o the mission result to compare to
     * @return a negative integer, zero, or a positive integer as this result has fewer,
     * equal, or more remaining life points than the specified result
     */
    @Override
    public int compareTo(MissionResult o) {
        return Integer.compare(this.vidaRestante, o.vidaRestante);
    }

    /**
     * Returns a string representation of the mission result, including the version,
     * result status, and remaining life points.
     *
     * @return a string representation of the mission result
     */
    @Override
    public String toString() {
        return "\nVersion: " + this.versao +
                "\nResult: " + this.result +
                "\nRemaining Life Points: " + this.vidaRestante;
    }
}
