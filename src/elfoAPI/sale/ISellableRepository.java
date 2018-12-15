package elfoAPI.sale;

import elfoAPI.exception.data.DataCannotBeAccessedException;

/**
 * Repositorio de coisas vendiveis
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.2
 * @param <T>
 */
public interface ISellableRepository<T extends ISellable> {
    /**
     * Pega por identificador
     * @param indetity Identity
     * @return Sellable of Identity
     */
    public T get(String indetity);

    /**
     * Atualiza dados do repositorio
     */
    public void update() throws DataCannotBeAccessedException;
}
