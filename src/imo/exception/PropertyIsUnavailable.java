package imo.exception;

import elfoAPI.exception.ElfoException;
import imo.property.Property;

/**
 * Prorpiedede indisponivel
 * @author Jose Romulo
 * @version 0.0.1
 */
public class PropertyIsUnavailable extends ElfoException {
    public PropertyIsUnavailable(Property property) {
        super("The property '" + property.getIdentity() + "' is unavailable!");
    }
}
