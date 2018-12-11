package elfo.data;

import elfo.exception.data.DataCannotBeAccessedException;

import java.io.IOException;

public interface IRepositorio<T extends IIdentifiable> {

    public boolean add(T object) throws IOException, DataCannotBeAccessedException;
    public boolean remove(T object) throws IOException, DataCannotBeAccessedException;
    public T get(int index);
    public T get(String indent);
    public int get(T object);
    public T[] toArray();
    public void update() throws IOException, DataCannotBeAccessedException;

}
