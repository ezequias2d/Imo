package elfoAPI.sale;

import elfoAPI.data.IIdentifiable;

public interface ISellable extends IIdentifiable {
    public abstract boolean isAvaliable();
    public abstract void setAvaliable(boolean bool);
    public abstract double getBuyPrice();
    public double getRentPrice();
    public String getIdentity();
}
