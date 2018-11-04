package elfo.number;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class Number {
    public static final int NULL = -1;
    double value;

    public Number(){
        this(0);
    }

    /**
     * @param value Value
     */
    public Number(double value){
        setValue(value);
    }

    /**
     * @return Value
     */
    public double getValue(){
        return value;
    }

    /**
     * Get integer part of the value
     * @return Integer Part
     */
    public int getIntValue(){
        return (int)(value - (value % 1.0));
    }

    /**
     * Set Value
     * @param value Value
     */
    public void setValue(double value){
        if(value < 0){
            this.value = -1;
        }else{
            this.value = value;
        }
    }

    /**
     * Increse a Number
     * @param number Number
     */
    public void increase(Number number){
        setValue(getValue() + number.getValue());
    }

    /**
     * Increase a double
     * @param value Value
     */
    public void increase(double value){
        setValue(getValue() + value);
    }

    /**
     * Increase 1
     */
    public void increase(){
        increase(1);
    }

    /**
     * Checks to see if it is undefined(Value = -1)
     * @return True if undefined(Value = -1)
     */
    public boolean isNull(){
        return value == NULL;
    }

    /**
     * @param obj Object
     * @return Return equivalence
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Number){
            Number number = (Number)obj;
            return value == number.value;
        }
        return false;
    }

    /**
     * @return String form
     */
    @Override
    public String toString(){
        return String.valueOf(getValue());
    }
}
