package elfoAPI.exception.sale;

import elfoAPI.exception.ElfoException;
import elfoAPI.sale.Sale;

public class SaleIsFinalizedException extends ElfoException {
    public SaleIsFinalizedException(Sale sale) {
        super("The sale is canceled, " + sale.getIdentity());
    }
}
