package imo.property;
import elfo.number.Number;
public class Room {
    public static final int BEDROOM = 0;
    public static final int LIVING_ROOM = 1;
    public static final int DINING_ROOM = 2;
    public static final int KITCHEN = 3;
    public static final int CLOSET = 4;
    public static final int BATHROOM = 5;
    public static final int GARAGE = 6;
    public static final int OFFICE = 7;
    public static final int GARDEN = 8;
    public static final int BALCONY = 9;
    public static final int LOBBY = 10;

    private int type;
    private Number area;
    private Number width;
    private Number lenght;

    public Room(int type, double area){
        this.type = type;
        this.area = new Number(area);
        this.width = new Number(-1);
        this.lenght = new Number(-1);
    }
    public Room(int type, double width, double lenght){
        this(type,width*lenght);
        this.width = new Number(width);
        this.lenght = new Number(lenght);
    }

    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public double getArea(){
        return type;
    }
    public void setArea(double area){
        this.area.setValue(area);
        this.lenght.setValue(-1);
        this.width.setValue(-1);
    }
    public void setArea(double width, double lenght){
        this.area.setValue(width*lenght);
        this.width.setValue(width);
        this.lenght.setValue(lenght);
    }

    public Number getWidth(){
        return width;
    }
    public Number getLenght(){
        return lenght;
    }

    public boolean isType(int type){
        return this.type == type;
    }
    public boolean equals(Room room){
        return (this.type == room.getType() &&
                this.area.equals(room.getArea()) &&
                this.width.equals(room.getWidth()) &&
                this.lenght.equals(room.getLenght()));
    }
}
