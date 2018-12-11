package imo.property;

import java.io.Serializable;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Room implements Serializable {
    public static final String[] TYPE_NAME = {"Bedroom","Living Room","Dining Room",
            "Kitchen","Closet","Bathroom",
            "Garage","Office","Garden",
            "Balcony","Lobby"};

    private String type;
    private double area;
    private double width;
    private double lenght;

    /**
     * Constructor
     * @param type Type
     */
    public Room(String type){
        this.type = type;
        this.area = area;
        this.width = -1;
        this.lenght = -1;
    }
    /**
     * Get Type
     * @return Type
     */
    public String getType(){
        return type;
    }

    /**
     * Set Type
     * @param type Type
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * @return Area
     */
    public double getArea(){
        return area;
    }

    /**
     * Set Area
     * @param area Area
     */
    public void setArea(double area){
        this.area = area;
        this.lenght = -1;
        this.width = -1;
    }

    /**
     * Set Area
     * @param width Width
     * @param lenght Lenght
     */
    public void setArea(double width, double lenght){
        this.area = width*lenght;
        this.width = width;
        this.lenght = lenght;
    }


    /**
     * @return Width
     */
    public double getWidth(){
        return width;
    }

    /**
     * @return Lenght
     */
    public double getLenght(){
        return lenght;
    }

    /**
     * @param type Type
     * @return if it is type
     */
    public boolean isType(String type){
        return this.type.equals(type);
    }

    /**
     * @param room Room
     * @return if the room is equivalent to another
     */
    public boolean equals(Room room){
        return (this.type.equals(room.getType()) &&
                this.area == room.getArea() &&
                this.width == room.getWidth() &&
                this.lenght == room.getLenght());
    }

}
