package elfoAPI.sale;

import elfoAPI.data.IIdentificable;

/**
 * Objeto vendivel em Sale
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.5
 */
public interface ISellable extends IIdentificable {
    /**
     * Se esta disponivel
     * @return Disponibilidade
     */
    public abstract boolean isAvaliable();

    /**
     * Seta disponibilidade
     * @param bool Boolean
     */
    public abstract void setAvaliable(boolean bool);

    /**
     * Pega preço de compra
     * @return buy price
     */
    public abstract double getBuyPrice();

    /**
     * Pega preço de alugar
     * @return rent price
     */
    public abstract double getRentPrice();

    /**
     * Pega identidade
     * @return Identity
     */
    public abstract String getIdentity();
}
