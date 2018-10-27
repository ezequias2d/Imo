package imo.property;

import imo.cashier.Money;

import java.util.ArrayList;
import elfo.number.Number;
import elfo.number.DeltaNumber;

public class Property {
    private Number area;
    private double width;
    private double length;
    private ArrayList<Room> rooms;
    private ArrayList<Floor> floors;
    private Money price;
    private String name;
    private boolean availability;
    
    public Property(double area, Money price) {
        this.area = new Number(area);
        this.floors = new ArrayList<Floor>();
    }

    public double getArea() {
        return area.getValue();
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
    public Money getPrice() {
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

    public void setArea(double area) {
        this.area.setValue(area);
    }
    public int addFloor(Floor floor){
        floors.add(floor);
        return floors.indexOf(floor);
    }
    public int addRoom(int index, Room room){
        floors.get(index).addRoom(room);
        return floors.get(index).getRooms().indexOf(room);
    }
    public void setPrice(double price) {
        this.price = new Money(price);
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