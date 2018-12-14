package elfoAPI.exception;

/**
 * Representa exceçao geral da ELFO API.
 * @author Ezequias Moises dos Santos Silva
 * @Version 0.0.1
 */
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
