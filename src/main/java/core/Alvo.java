package core;

public class Alvo {
    private String tipo;

    /**
     * Creates an empty target
     */
    public Alvo()  {
        this.tipo = "";
    }

    /**
     * Creates a target specifying the type
     * @param tipo the type of the target
     */
    public Alvo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Getter for the type of the target
     * @return the type of the target
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Setter for the type of the target
     * @param tipo the type of the target
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
