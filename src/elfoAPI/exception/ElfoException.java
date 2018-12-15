package elfoAPI.exception;

/**
 * Representa exceçao geral da ELFO API.
 * @author Ezequias Moises dos Santos Silva
 * @Version 0.0.1
 */
public class ElfoException extends Exception{
    protected String message;

    /**
     * Contrutor de ElfoException
     * @param message Mensagem
     */
    public ElfoException(String message){
        super();
        this.message = message;
    }

    /**
     *  Pega mensagem
     * @return
     */
    public String getMessage(){
        return message;
    }

    /**
     * Seta mensagem
     * @param message
     */
    public void setMessage(String message){
        this.message = message;
    }
}
