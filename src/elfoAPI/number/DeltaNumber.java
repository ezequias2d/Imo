package elfoAPI.number;

import java.io.Serializable;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class DeltaNumber implements Serializable, Cloneable {
    private final int deltaNull;
    private double min;
    private double max;
    public DeltaNumber(){
        this(-1,-1);
    }

    /**
     * @param min Minimum
     * @param max Maximum
     */
    public DeltaNumber(double min, double max){
        deltaNull = -1;
        setDelta(min,max);
    }

    /**
     * Set Minimum
     * @param min Minimum
     */
    public void setMin(double min){
        if(min < 0){
            this.min = - 1;
        }else{
            this.min = min;
        }
    }

    /**
     * Set Maximum
     * @param max Maximum
     */
    public void setMax(double max){
        if(max < 0){
            this.max = - 1;
        }else{
            this.max = max;
        }
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
    public double getMin(){
        return min;
    }

    /**
     * @return Maximum
     */
    public double getMax(){
        return max;
    }

    /**
     * Checks whether the elfoNumber is between the
     * @param num ElfoNumber
     * @return true or false
     */
    public boolean isInDeltaNumber(double num){
        return (num >= min || min == deltaNull) && (num <= max || max == deltaNull);
    }

    public DeltaNumber getClone() {
        try{
            return (DeltaNumber)this.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
