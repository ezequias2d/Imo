package elfoAPI.data;

import elfoAPI.exception.data.DataCannotBeAccessedException;

import java.io.IOException;

public interface IRepositorio<T extends IIdentifiable> {

    public boolean add(T object) throws DataCannotBeAccessedException;
    public boolean remove(T object) throws DataCannotBeAccessedException;
    public T get(int index);
    public T get(String indent);
    public int get(T object);
    public T[] toArray();
    public void update() throws DataCannotBeAccessedException;

}
