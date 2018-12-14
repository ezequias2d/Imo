package imo.exception;

import elfoAPI.exception.ElfoException;
import elfoAPI.number.DeltaNumber;


/**
 * Representa um erro lançado quando uma caracteristica nao esta dentro
 * do padrao definido de algo.
 * Usado em: Property para dizer que algum parametro fornecido esta fora do tipo.
 * Exemplo:
 *      Uma propriedade do tipo "Sobrado".
 *      Para ser um sobrado precisa ter ao menos 1 andar acima do terrio.
 *      Logo ao tentar cirar uma propriedade do tipo "Sobrado", mas eu disse que este sobrado
 *      possui 0 andares, ou seja apenas o terrio, a exceçao seria lançada informando que esta
 *      fora do tipo "Sobrado"
 */
public class ParameterOutOfTypeException extends ElfoException {

    private String parameter;
    private DeltaNumber limit;

    /**
     * @param parameter Paremetro fora dos limites
     * @param limit Limite propriamente dito
     */
    public ParameterOutOfTypeException(String parameter, DeltaNumber limit) {
        super("Parameter Out of Type");
        this.parameter = parameter;
        this.limit = limit;
        this.setMessage(mensageGen());
    }

    /**
     * Constroi mensagem
     * @return Mensagem com informaçoes do erro
     */
    private String mensageGen(){
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
