package elfo.sale;

import elfo.data.IIdentifiable;

public interface ISellable extends IIdentifiable {
    public abstract boolean isAvaliable();
    public abstract double getBuyPrice();
    public double getRentPrice();
    public String getIdentity();
}
