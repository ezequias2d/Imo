package imo.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import elfo.calendar.schedule.Schedule;
import elfo.number.DeltaNumber;
import imo.exception.ParameterOutOfTypeException;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Property implements Serializable {

    private double area;
    private double width;
    private double length;
    private ArrayList<Room> rooms;
    private int floors;
    private double buyPrice;
    private double rentPrice;
    private String name;
    private boolean availability;
    private Schedule schedule;


    private PropertyType propertyType;

    /**
     * Constructor
     * @param area Area
     */
    public Property(double area, double buyPrice, double rentPrice, PropertyType propertyType) {
        this.propertyType = propertyType;
        this.area = area;
        this.rooms = new ArrayList<Room>();
        this.width = -1;
        this.length = -1;
        this.availability = true;
        this.floors = 0;
        this.buyPrice = buyPrice;
        this.rentPrice = rentPrice;
        this.schedule = new Schedule();
    }

    public Schedule getSchedule(){
        schedule.upgradeToCurrentDay();
        return schedule;
    }

    /**
     * @return ElfoNumber Area
     */
    public double getArea() {
        return area;
    }

    /**
     * @return ElfoNumber Rooms
     */
    public double getNumberOfRooms() {
        return rooms.size();
    }

    /**
     * @param type Type
     * @return ElfoNumber Rooms Type
     */
    public double getNumberOfRooms(String type){
        double count = 0;
        for(Room r : rooms){
            if(r.isType(type)){
                count++;
            }
        }
        return count;
    }

    /**
     * @return ElfoNumber Price
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    public double getRentPrice() {
        return rentPrice;
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
    public void setArea(double area) throws ParameterOutOfTypeException {
        if(propertyType.isInArea(area)){
            this.area = area;
            this.length = -1;
            this.width = -1;
        }else{
            throw new ParameterOutOfTypeException("Area",propertyType.getArea());
        }
    }

    /**
     * Get Width
     * @return Width
     */
    public double getWidth(){
        return width;
    }

    /**
     * Get Lenght
     * @return Lenght
     */
    public double getLength(){
        return length;
    }

    /**
     * @return if it has square dimensions
     */
    public boolean isMeasured(){
        return !(length == -1 && width == -1);
    }

    /**
     * Set Price
     * @param price ElfoNumber Price
     */
    public void setRentPrice(double price) throws ParameterOutOfTypeException {
        if(propertyType.isInRentPrice(price)){
            this.rentPrice = price;
        }else{
            throw new ParameterOutOfTypeException("Rent Price",propertyType.getRentPrice());
        }
    }

    public void setBuyPrice(double price) throws ParameterOutOfTypeException {
        if(propertyType.isInBuyPrice(price)){
            this.buyPrice = price;
        }else{
            throw new ParameterOutOfTypeException("Buy Price",propertyType.getBuyPrice());
        }
    }

    /**
     * Set Name
     * @param name Name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void setFloors(int floors) throws ParameterOutOfTypeException {
        this.floors = floors;
        if(propertyType.isInFloors(floors)){
            this.floors = floors;
        }else{
            throw new ParameterOutOfTypeException("Floors",propertyType.getFloor());
        }
    }

    public void addRoom(Room r){
       rooms.add(r);
    }

    public void addRooms(Collection<Room> rooms){
        rooms.addAll(rooms);
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
        return deltaNumber.isInDeltaNumber(buyPrice);
    }
    /**
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the price is in the price boundary
     */
    public boolean isInFloors(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(floors);
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
    public boolean isInNumberRooms(DeltaNumber deltaNumber, String type){
        return deltaNumber.isInDeltaNumber(getNumberOfRooms(type));
    }

    public String getPropertyTypeName(){
        return propertyType.getName();
    }
    public String getPropertyTypeDescription(){
        return propertyType.getDescription();
    }
    public DeltaNumber getPropertyTypeArea(){
        return propertyType.getArea().getClone();
    }
    public DeltaNumber getPropertyTypeBuyPrice(){
        return propertyType.getBuyPrice().getClone();
    }
    public DeltaNumber getPropertyTypeFloor(){
        return propertyType.getFloor().getClone();
    }
    public DeltaNumber getPropertyTypeRentPrice(){
        return propertyType.getRentPrice().getClone();
    }
    public DeltaNumber getPropertyRoom(String roomType){
        return propertyType.getRoom(roomType).getClone();
    }

    public int getFloors(){
        return floors;
    }
    public String toString(){
        String out = this.getName();
        out += String.format("\nFloors:%d" +
                "\nArea:%.2f", floors, area);
        if(this.isMeasured()){
            out += String.format("\nWidth:%.2f\nLength:%.2f", this.getWidth(),this.getLength());
        }
        out += String.format("\nRooms:");
        for(Room r : Room.values()){
            out += String.format("\n    %s - %.0f",r.getType(),this.getNumberOfRooms(r.getType()));
        }
        out += String.format("\nRent Price:$%.2f\nBuy Price::$.2f\n",rentPrice,buyPrice);
        return out;
    }
}