package elfoAPI.exception.sale;

import elfoAPI.exception.ElfoException;

/**
 * Representa quantidade de meses invalida(abaixo de 1) para Alugar ou fazer um financiamento
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
 */
public class MonthsForRentOrFinanceInvalidException extends ElfoException {
    public MonthsForRentOrFinanceInvalidException() {
        super("Need at least 1 month to make a rental!");
    }
}
