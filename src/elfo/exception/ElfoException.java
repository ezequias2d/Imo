package elfo.exception;

public class ElfoException extends Exception{
    protected String message;
    public ElfoException(String message){
        super();
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }
}
