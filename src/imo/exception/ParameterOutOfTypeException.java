package imo.exception;

import elfo.exception.ElfoException;
import elfo.number.DeltaNumber;

public class ParameterOutOfTypeException extends ElfoException {

    private String parameter;
    private DeltaNumber limit;

    public ParameterOutOfTypeException(String parameter, DeltaNumber limit) {
        super("Parameter Out of Type");
        this.setMessage(mensageGen(parameter,limit));
    }
    private String mensageGen(String parameter, DeltaNumber limit){
        String message = "Out of type limit: The parameter '" + parameter + "' is not in limit";
        double min = limit.getMin();
        if(min == -1){
            message += " infinite negative";
        }else{
            message += " to " + min;
        }
        double max = limit.getMax();
        if(max == -1){
            message += " infinite positive";
        }else{
            message += " to " + max;
        }
        return message;
    }
}
