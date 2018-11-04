package elfo.number;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class DeltaNumber {
    private Number min;
    private Number max;
    public DeltaNumber(){
        setDelta(-1,-1);
    }

    /**
     * @param min Minimum
     * @param max Maximum
     */
    public DeltaNumber(double min, double max){
        setDelta(min,max);
    }

    /**
     * Set Minimum
     * @param min Minimum
     */
    public void setMin(double min){
        if(this.min == null){
            this.min = new Number();
        }
        this.min.setValue(min);
    }

    /**
     * Set Maximum
     * @param max Maximum
     */
    public void setMax(double max){
        if(this.max == null){
            this.max = new Number();
        }
        this.max.setValue(max);
    }

    /**
     * Set Delta(Minimum and Maximum)
     * @param min Minimum
     * @param max Maximum
     */
    public void setDelta(double min, double max){
        setMin(min);
        setMax(max);
    }

    /**
     * @return Minimum
     */
    public Number getMin(){
        return min;
    }

    /**
     * @return Maximum
     */
    public Number getMax(){
        return max;
    }

    /**
     * Checks whether the number is between the
     * @param number Number
     * @return true or false
     */
    public boolean isInDeltaNumber(Number number){
        return (number.value >= min.getValue() || min.isNull()) && (number.value <= max.getValue() || max.isNull());
    }
}
