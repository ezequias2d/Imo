package elfo.exception.sale;

import elfo.exception.ElfoException;

/**
 * Representa exceçao lansada ao tentar criar nova compra com um produto nao disponivel.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class ProductNotAvaliableException extends ElfoException {
    public ProductNotAvaliableException() {
        super("Product not be avaliable!");
    }
}
