package imo.cashier;
import elfo.number.Number;

public class Money extends Number {
    public Money(double value){
        super(value);
    }
    public String getFormated(){
        return String.format("$.2f%",getValue());
    }
}
