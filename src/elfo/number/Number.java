package elfo.number;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class Number {
    public static final int NULL = -1;
    double value;

    public Number(){
        this(0);
    }
    public Number(double value){
        setValue(value);
    }
    public double getValue(){
        return value;
    }
    public int getIntValue(){
        return (int)(value - (value % 1.0));
    }
    public void setValue(double value){
        if(value < 0){
            this.value = -1;
        }else{
            this.value = value;
        }
    }
    public void increase(Number number){
        setValue(getValue() + number.getValue());
    }
    public void increase(double value){
        setValue(getValue() + value);
    }
    public void increase(){
        increase(1);
    }
    public boolean inDeltaNumber(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(this);
    }
    public boolean isNull(){
        return value == NULL;
    }
    public boolean equals(Object obj){
        if(obj instanceof Number){
            Number number = (Number)obj;
            return value == number.value;
        }
        return false;
    }
}
