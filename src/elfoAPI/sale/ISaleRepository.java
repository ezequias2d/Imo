package elfoAPI.sale;

import elfoAPI.data.IRepositorio;

public interface ISaleRepository extends IRepositorio<Sale> {
    public abstract void setSellableRepository(ISellableRepository sellableRepository);
}
