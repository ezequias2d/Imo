package elfo.calendar.schedule;

public interface IScheduleRepository {

    public abstract int get(Schedule object);
    public abstract Schedule get(String ident);
    public abstract Schedule get(String ident, int year);
    public abstract boolean remove(Schedule object);

}
