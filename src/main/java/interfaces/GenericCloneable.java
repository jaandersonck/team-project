package interfaces;

/**
 * Produces clone of an instance of T
 * @param <T> The Type implementing Cloneable
*/
public interface GenericCloneable<T> {
    /** 
     * @return clone of this object
     */
    public T clone();
}
