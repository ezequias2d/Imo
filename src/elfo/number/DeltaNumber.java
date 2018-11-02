package elfo.number;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class DeltaNumber {
    private Number min;
    private Number max;
    public DeltaNumber(){
        setDelta(-1,-1);
    }
    public DeltaNumber(double min, double max){
        setDelta(min,max);
    }
    public void setMin(double min){
        if(this.min == null){
            this.min = new Number();
        }
        this.min.setValue(min);
    }
    public void setMax(double max){
        if(this.max == null){
            this.max = new Number();
        }
        this.max.setValue(max);
    }
    public void setDelta(double min, double max){
        setMin(min);
        setMax(max);
    }
    public Number getMin(){
        return min;
    }
    public Number getMax(){
        return max;
    }
    public boolean isInDeltaNumber(Number number){
        return (number.value >= min.getValue() || min.isNull()) && (number.value <= max.getValue() || max.isNull());
    }
}
