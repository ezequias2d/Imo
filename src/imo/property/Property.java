package imo.property;

import java.util.ArrayList;
import elfo.number.Number;
import elfo.number.DeltaNumber;

public class Property {
    private Number area;
    private Number width;
    private Number length;
    private ArrayList<Room> rooms;
    private ArrayList<Floor> floors;
    private Number price;
    private String name;
    private boolean availability;
    
    public Property(double area, Number price) {
        this.area = new Number(area);
        this.floors = new ArrayList<Floor>();
        this.width = new Number(-1);
        this.length = new Number(-1);
        this.availability = true;
        setPrice(price);
    }

    public Number getArea() {
        return area;
    }
    public Number getNumberOfFloor(){
        return new Number(floors.size());
    }
    public Number getNumberOfRooms() {
        Number count = new Number();
        for(Floor floor : floors){
            count.increase(floor.getNumberOfRooms());
        }
        return count;
    }
    public Number getNumberOfRooms(int type){
        Number count = new Number();
        for(Floor floor: floors){
            count.increase(floor.getNumberOfRooms(type));
        }
        return count;
    }
    public Number getPrice() {
        return price;
    }
    public String getName() {
        return name;
    }
    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean b) {
        this.availability = b;
    }

    public void setArea(double area){
        this.area.setValue(area);
        this.length.setValue(-1);
        this.width.setValue(-1);
    }
    public void setArea(double width, double lenght){
        this.area.setValue(width*lenght);
        this.width.setValue(width);
        this.length.setValue(lenght);
    }
    public double getWidth(){
        return width.getValue();
    }
    public double getLength(){
        return length.getValue();
    }
    public boolean isMeasured(){
        return !(length.isNull() && width.isNull());
    }

    public Floor createFloor(){
        Floor floor = new Floor(floors.size() + 1);
        floors.add(floor);
        return floor;
    }
    public void setPrice(Number price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isInArea(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(area);
    }
    public boolean isInPrice(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(price);
    }
    public boolean isInNumberRooms(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(getNumberOfRooms());
    }
    public boolean isInNumberRooms(DeltaNumber deltaNumber, int type){
        return deltaNumber.isInDeltaNumber(getNumberOfRooms(type));
    }
    public boolean isInNumberFloor(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(getNumberOfFloor());
    }
}