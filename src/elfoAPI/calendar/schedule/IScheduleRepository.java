package elfoAPI.calendar.schedule;

import elfoAPI.exception.data.DataCannotBeAccessedException;

/**
 * Interface de Repositorio de Schedules
 */
public interface IScheduleRepository {

    /**
     * Pega index de um Schedule dentro do repositorio
     * @param object Schedule
     * @return Index
     */
    public abstract int get(Schedule object);

    /**
     * Pega Schedule por um identificador
     * @param identity Identificador
     * @return Schedule of Identity
     */
    public abstract Schedule get(String identity) throws DataCannotBeAccessedException;

    /**
     * Pega Schedule de uma identidade e de um ano especifico
     * @param identity Identity
     * @param year Year
     * @return Schedule
     */
    public abstract Schedule get(String identity, int year) throws DataCannotBeAccessedException;

    /**
     * Remove Schedule do repositorio
     * @param object Schedule
     * @return If remove
     */
    public abstract boolean remove(Schedule object) throws DataCannotBeAccessedException;

    /**
     * Update repository
     */
    public void update() throws DataCannotBeAccessedException;
}
