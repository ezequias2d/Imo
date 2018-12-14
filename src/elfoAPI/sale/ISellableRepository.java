package elfoAPI.sale;

import elfoAPI.exception.data.DataCannotBeAccessedException;

public interface ISellableRepository<T extends ISellable> {
    public T get(String indetity);
    public void update() throws DataCannotBeAccessedException;
}
