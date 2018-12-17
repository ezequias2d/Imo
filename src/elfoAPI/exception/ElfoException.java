package elfoAPI.exception;

/**
 * Representa exceçao geral da ELFO API.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.1
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
     * @return Message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Seta mensagem
     * @param message Messege
     */
    public void setMessage(String message){
        this.message = message;
    }
}
