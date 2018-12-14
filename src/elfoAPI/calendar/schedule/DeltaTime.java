package elfoAPI.calendar.schedule;

import java.io.Serializable;

/**
 * Representa um intervalo de tempo, como a duraçao de um evento
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class DeltaTime implements Serializable {
    private int[] time;

    /**
     * Incializa DeltaTime com uma variaçao de hora e de minutos
     * @param dHours Delta hours
     * @param dMinutes Delta minutes
     */
    public DeltaTime(int dHours, int dMinutes){
        while(dMinutes >= 60){
            dHours += 1;
            dMinutes -= 60;
        }
        time = new int[]{dHours, dMinutes};
    }

    /**
     * Pega a parte "hora" da variaçao de tempo
     * @return Delta hours
     */
    public int getDeltaHours(){
        return time[0];
    }

    /**
     * Pega a parte "minutos" da variaçao de tempo
     * @return Delta minutes
     */
    public int getDeltaMinutes(){
        return time[1];
    }

    /**
     * Pega variaçao de hora e minutos completa em um vetor
     * Exemplo:
     *          hora = 1
     *          minutos = 30
     *
     *          Retorno = int[]{1, 30}
     * @return int[] = {Delta hours, Delta Minutes}
     */
    public int[] getTime(){
        return time;
    }

    /**
     * Pega tempo absoluto em minutos
     * Exemplo:
     *          hora = 1
     *          minutos = 30
     *
     *          Retorno = 60(horas em minutos) + 30(minutos) = 90
     * @return Absolute Delta Time in Double
     */
    public double getDeltaTime(){
        double dt = time[1];
        dt += time[0] * 60;
        return dt;
    }

    /**
     * Formata intevalo no formato padrao de hora "horas:minutos"
     * @return
     */
    @Override
    public String toString(){
        return getDeltaHours() + ":" + getDeltaMinutes();
    }

    /**
     * Compara equivalencia
     * @param object
     * @return if equals
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof DeltaTime){
            DeltaTime deltaTime = (DeltaTime)object;
            return deltaTime.getDeltaHours() == getDeltaHours() && deltaTime.getDeltaMinutes() == getDeltaMinutes();
        }
        return false;
    }
}
