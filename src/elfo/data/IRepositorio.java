package elfo.data;

import java.io.IOException;

public interface IRepositorio<T> {

    public abstract boolean add(T object) throws IOException;
    public abstract boolean remove(T object) throws IOException;
    public abstract T get(int index);
    public abstract T get(String indent);
    public abstract int get(T object);
    public abstract T[] toArray();
    public abstract void update() throws IOException;

}
