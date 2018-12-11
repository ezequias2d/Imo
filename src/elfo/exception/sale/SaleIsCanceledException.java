package elfo.exception.sale;

import elfo.exception.ElfoException;
import elfo.sale.Sale;

public class SaleIsCanceledException extends ElfoException {
    public SaleIsCanceledException(Sale sale) {
        super("The sale is canceled, " + sale.getIdentity());
    }
}
