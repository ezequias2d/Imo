package elfo.number;

public class DeltaNumber {
    private double min;
    private double max;
    public DeltaNumber(double min, double max){
        setDelta(min,max);
    }
    public void setMin(double min){
        this.min = min;
    }
    public void setMax(double max){
        this.max = max;
    }
    public void setDelta(double min, double max){
        setMin(min);
        setMax(max);
    }
    public double getMin(){
        return min;
    }
    public double getMax(){
        return max;
    }
    public boolean isInDeltaNumber(Number number){
        return (number.value >= min || min == -1) && (number.value <= max || max == -1);
    }
}
