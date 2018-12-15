package elfoAPI.data;

import elfoAPI.exception.data.DataCannotBeAccessedException;

/**
 * Interface de repositorio generico
 * @param <T> IIdentificable
 */
public interface IRepositorio<T extends IIdentificable> {

    public abstract boolean add(T object) throws DataCannotBeAccessedException;
    public abstract boolean remove(T object) throws DataCannotBeAccessedException;
    public abstract T get(int index);
    public abstract T get(String indent);
    public abstract int get(T object);
    public abstract T[] toArray();
    public abstract void update() throws DataCannotBeAccessedException;

}
