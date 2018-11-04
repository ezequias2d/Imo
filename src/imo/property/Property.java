package imo.property;

import java.util.ArrayList;
import elfo.number.Number;
import elfo.number.DeltaNumber;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Property {
    private Number area;
    private Number width;
    private Number length;
    private ArrayList<Room> rooms;
    private ArrayList<Floor> floors;
    private Number price;
    private String name;
    private boolean availability;

    /**
     * Constructor
     * @param area Area
     * @param price Price
     */
    public Property(double area, double price) {
        this.area = new Number(area);
        this.floors = new ArrayList<Floor>();
        this.width = new Number(-1);
        this.length = new Number(-1);
        this.availability = true;
        setPrice(new Number(price));
    }

    /**
     * Create Floor
     * @return New Floor
     */
    public Floor createFloor(){
        Floor floor = new Floor();
        floors.add(floor);
        return floor;
    }

    /**
     * @return Number Area
     */
    public Number getArea() {
        return area;
    }

    /**
     * @return Number Floor
     */
    public Number getNumberOfFloor(){
        return new Number(floors.size());
    }

    /**
     * @return Number Rooms
     */
    public Number getNumberOfRooms() {
        Number count = new Number();
        for(Floor floor : floors){
            count.increase(floor.getNumberOfRooms());
        }
        return count;
    }

    /**
     * @param type Type
     * @return Number Rooms Type
     */
    public Number getNumberOfRooms(int type){
        Number count = new Number();
        for(Floor floor: floors){
            count.increase(floor.getNumberOfRooms(type));
        }
        return count;
    }

    /**
     * @return Number Price
     */
    public Number getPrice() {
        return price;
    }

    /**
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * @return is Availability
     */
    public boolean isAvailability() {
        return availability;
    }

    /**
     * Set Availability
     * @param b Boolean
     */
    public void setAvailability(boolean b) {
        this.availability = b;
    }

    /**
     * Set Area
     * @param area Area
     */
    public void setArea(double area){
        this.area.setValue(area);
        this.length.setValue(-1);
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
        this.length.setValue(lenght);
    }

    /**
     * Get Width
     * @return Width
     */
    public double getWidth(){
        return width.getValue();
    }

    /**
     * Get Lenght
     * @return Lenght
     */
    public double getLength(){
        return length.getValue();
    }

    /**
     * @return if it has square dimensions
     */
    public boolean isMeasured(){
        return !(length.isNull() && width.isNull());
    }

    /**
     * Set Price
     * @param price Number Price
     */
    public void setPrice(Number price) {
        this.price = price;
    }

    /**
     * Set Name
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the area is in the area boundary
     */
    public boolean isInArea(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(area);
    }
    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the price is in the price boundary
     */
    public boolean isInPrice(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(price);
    }
    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the rooms is in the rooms boundary
     */
    public boolean isInNumberRooms(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(getNumberOfRooms());
    }
    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @param type Type
     * @return if the roomsType is in the roomsType boundary
     */
    public boolean isInNumberRooms(DeltaNumber deltaNumber, int type){
        return deltaNumber.isInDeltaNumber(getNumberOfRooms(type));
    }
    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the floors is in the floors boundary
     */
    public boolean isInNumberFloor(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(getNumberOfFloor());
    }
    public String toString(Property property){
        String out = property.getName();
        Number area = property.getArea();
        Number price = property.getPrice();
        out += String.format("\nFloors:%d" +
                "\nArea:%.2f", property.getNumberOfFloor().getIntValue(), area.getValue());
        if(property.isMeasured()){
            out += String.format("\nWidth:%.2f\nLength:%.2f", property.getWidth(),property.getLength());
        }
        out += String.format("\nRooms:");
        for(int i = 0; i < Room.TYPE_NAME.length; i++){
            out += String.format("\n    %s - %d",Room.TYPE_NAME[i],property.getNumberOfRooms(i).getIntValue());
        }
        out += String.format("\nPrice:$%.2f\n",price.getValue());
        return out;
    }
}