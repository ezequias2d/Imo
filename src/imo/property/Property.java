package imo.property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import elfoAPI.calendar.schedule.Schedule;
import elfoAPI.calendar.schedule.ScheduleRepository;
import elfoAPI.data.IIdentifiable;
import elfoAPI.number.DeltaNumber;
import elfoAPI.sale.ISellable;
import imo.exception.ParameterOutOfTypeException;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Property implements Serializable, ISellable, IIdentifiable {

    private final String identity;
    private double area;
    private double width;
    private double length;
    private ArrayList<Room> rooms;
    private int floors;
    private double buyPrice;
    private double rentPrice;
    private String andress;
    private boolean avaliable;


    private PropertyType propertyType;

    /**
     * Constructor
     * @param area Area
     */
    public Property(ArrayList<Room> rooms, int floors, double area, double buyPrice, double rentPrice, PropertyType propertyType, String andress,String identity) throws ParameterOutOfTypeException {
        this.propertyType = propertyType;
        this.identity = identity;
        setTerrainArea(area);
        setBuyPrice(buyPrice);
        setRentPrice(rentPrice);
        setFloors(floors);
        setRooms(rooms);
        this.andress = andress;
        this.avaliable = true;
        this.floors = 0;
        this.buyPrice = buyPrice;
        this.rentPrice = rentPrice;
        this.andress = "";
    }

    public Schedule getSchedule(){
        return ScheduleRepository.getInstance().get(identity);
    }

    @Override
    public String getIdentity(){
        return this.identity;
    }

    /**
     * @return ElfoNumber Area
     */
    public double getArea() {
        double area = 0;
        for(Room room : rooms){
            area += room.getArea();
        }
        return area;
    }
    public double getTerrainArea(){
        if(area != -1){
            return area;
        }else{
            return width * length;
        }
    }

    /**
     * @return ElfoNumber Rooms
     */
    public int getNumberOfRooms() {
        return rooms.size();
    }

    /**
     * @param type Type
     * @return ElfoNumber Rooms Type
     */
    public int getNumberOfRooms(String type){
        int count = 0;
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
    public String getAndress() {
        return andress;
    }

    /**
     * @return is Availability
     */
    @Override
    public boolean isAvaliable() {
        return avaliable;
    }

    /**
     * Set Availability
     * @param b Boolean
     */
    public void setAvaliable(boolean b) {
        this.avaliable = b;
    }

    /**
     * Set Area
     * @param area Area
     */
    public void setTerrainArea(double area) throws ParameterOutOfTypeException {
        if(propertyType.isInArea(area)){
            this.area = area;
            this.length = -1;
            this.width = -1;
        }else{
            throw new ParameterOutOfTypeException("Area",propertyType.getArea());
        }
    }

    public void setRooms(ArrayList<Room> rooms) throws ParameterOutOfTypeException{
        ArrayList<Room> saveRooms = this.rooms;
        this.rooms = rooms;
        if(!propertyType.isInRooms(rooms.size())){
            throw new ParameterOutOfTypeException("All Rooms", propertyType.getRooms());
        }
        for(String roomType : Room.TYPE_NAME){
            int numberOfType = getNumberOfRooms(roomType);
            if(!propertyType.isInRoom(roomType,numberOfType)){
                this.rooms = saveRooms;
                throw  new ParameterOutOfTypeException(roomType,propertyType.getRoom(roomType));
            }
        }
        this.rooms = new ArrayList<Room>();
        this.rooms.addAll(rooms);
    }

    /**
     * Set Area
     */
    public void setTerrainArea(double lenght, double width) throws ParameterOutOfTypeException {
        if(propertyType.isInArea(area)){
            this.area = -1;
            this.length = lenght;
            this.width = width;
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
     * @param andress Name
     */
    public void setAndress(String andress) {
        this.andress = andress;
    }

    public void setFloors(int floors) throws ParameterOutOfTypeException {
        this.floors = floors;
        if(propertyType.isInFloors(floors)){
            this.floors = floors;
        }else{
            throw new ParameterOutOfTypeException("Floors",propertyType.getFloor());
        }
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

    public void replacingRooms(ArrayList<Room> rooms){
        this.rooms.clear();
        this.rooms.addAll(rooms);
    }
    public void setPropertyType(PropertyType propertyType){
        this.propertyType = propertyType;
    }

    public ArrayList<Room> getRooms(){
        return (ArrayList<Room>) rooms.clone();
    }

    public String getPropertyTypeName(){
        return propertyType.getName();
    }
    public String getPropertyTypeDescription(){
        return propertyType.getDescription();
    }
    public PropertyType getPropertyType(){
        return propertyType;
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
        String out = this.getAndress();
        out += String.format("\nFloors:%d" +
                "\nArea:%.2f", floors, area);
        if(this.isMeasured()){
            out += String.format("\nWidth:%.2f\nLength:%.2f", this.getWidth(),this.getLength());
        }
        out += String.format("\nRooms:");
        for(String type : Room.TYPE_NAME){
            out += String.format("\n    %s - %d",type,this.getNumberOfRooms(type));
        }
        out += String.format("\nRent Price:$%.2f\nBuy Price::$%.2f\n",rentPrice,buyPrice);
        return out;
    }
}