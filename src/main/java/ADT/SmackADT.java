package ADT;

public interface SmackADT<T> extends SmackStackADT<T> {
    /**
     * Eliminates and returns the last element of the stack
     * @return The last element of the stack
     */
    public T smack();
}
