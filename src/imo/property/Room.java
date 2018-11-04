package imo.property;
import elfo.number.Number;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
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

    public static final String[] TYPE_NAME = {"Bedroom","Living Room","Dining Room",
                                                "Kitchen","Closet","Bathroom",
                                                    "Garage","Office","Garden",
                                                            "Balcony","Lobby"};

    private int type;
    private Number area;
    private Number width;
    private Number lenght;

    /**
     * Constructor
     * @param type Type
     * @param area Area
     */
    public Room(int type, double area){
        this.type = type;
        this.area = new Number(area);
        this.width = new Number(-1);
        this.lenght = new Number(-1);
    }
    /**
     * Constructor
     * @param type Type
     * @param width Width
     * @param lenght Lenght
     */
    public Room(int type, double width, double lenght){
        this(type,width*lenght);
        this.width = new Number(width);
        this.lenght = new Number(lenght);
    }

    /**
     * Get Type
     * @return Type
     */
    public int getType(){
        return type;
    }

    /**
     * Set Type
     * @param type Type
     */
    public void setType(int type){
        this.type = type;
    }

    /**
     * @return Area
     */
    public double getArea(){
        return area.getValue();
    }

    /**
     * Set Area
     * @param area Area
     */
    public void setArea(double area){
        this.area.setValue(area);
        this.lenght.setValue(-1);
        this.width.setValue(-1);
    }

    /**
     * Set Area
     * @param width Width
     * @param lenght Lenght
     */
    public void setArea(double width, double lenght){
        this.area.setValue(width*lenght);
        this.width.setValue(width);
        this.lenght.setValue(lenght);
    }


    /**
     * @return Width
     */
    public Number getWidth(){
        return width;
    }

    /**
     * @return Lenght
     */
    public Number getLenght(){
        return lenght;
    }

    /**
     * @param type Type
     * @return if it is type
     */
    public boolean isType(int type){
        return this.type == type;
    }

    /**
     * @param room Room
     * @return if the room is equivalent to another
     */
    public boolean equals(Room room){
        return (this.type == room.getType() &&
                this.area.equals(room.getArea()) &&
                this.width.equals(room.getWidth()) &&
                this.lenght.equals(room.getLenght()));
    }
}
