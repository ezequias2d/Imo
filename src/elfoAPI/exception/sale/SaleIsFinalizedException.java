package elfoAPI.exception.sale;

import elfoAPI.exception.ElfoException;
import elfoAPI.sale.Sale;

/**
 * Representa exceçao de Sale esta finalizada, impedindo a auteraçao de seu estado.
 * @version 0.0.1
 * @author Ezequias Moises dos Santos Silva
 */
public class SaleIsFinalizedException extends ElfoException {
    public SaleIsFinalizedException(Sale sale) {
        super("The sale is canceled, " + sale.getIdentity());
    }
}
