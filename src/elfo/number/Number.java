package elfo.number;

public class Number {
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
        setValue(getValue()+1);
    }
    public boolean inDeltaNumber(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(this);
    }
    public boolean equals(Number number){
        return number.value == number.value;
    }
}
