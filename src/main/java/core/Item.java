package core;

public class Item {
    private String tipo;
    private int pontos;

    /**
     * Creates an empty item
     */
    public Item() {
        this.tipo = "";
        this.pontos = 0;
    }

    /**
     * Creates an item specifying the type and the points
     * @param tipo the type of the item
     * @param pontos the points that the item will heal/protect
     */
    public Item(String tipo, int pontos) {
        this.tipo = tipo;
        this.pontos = pontos;
    }

    /**
     * Getter for the item type
     * @return the item type
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Setter for the item type
     * @param tipo the item type
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Getter for the item points
     * @return the item points
     */
    public int getPontos() {
        return pontos;
    }

    /**
     * Setter for the item points
     * @param pontos the item points
     */
    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
