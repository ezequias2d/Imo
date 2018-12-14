package elfoAPI.exception.sale;

import elfoAPI.exception.ElfoException;

public class MonthsForRentOrFinanceInvalidException extends ElfoException {
    public MonthsForRentOrFinanceInvalidException() {
        super("Need at least 1 month to make a rental!");
    }
}
