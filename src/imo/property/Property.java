package imo.property;

import java.io.Serializable;
import java.util.ArrayList;

import elfoAPI.calendar.schedule.Schedule;
import elfoAPI.calendar.schedule.ScheduleRepository;
import elfoAPI.data.IIdentificable;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.number.DeltaNumber;
import elfoAPI.sale.ISellable;
import imo.exception.ParameterOutOfTypeException;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Property implements Serializable, ISellable, IIdentificable {

    private final String identity;
    private double area;
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

    /**
     * Pega Schedule da propriedade
     * @return Schedule of Property
     */
    public Schedule getSchedule() throws DataCannotBeAccessedException {
        return ScheduleRepository.getInstance().get(identity);
    }

    /**
     * Pega identidade
     * @return Identity
     */
    @Override
    public String getIdentity(){
        return this.identity;
    }

    /**
     * Pega area de todos os rooms juntos
     * @return Rooms Area
     */
    public double getArea() {
        double area = 0;
        for(Room room : rooms){
            area += room.getArea();
        }
        return area;
    }

    /**
     * Pega area do terreno
     * @return Terrain Area
     */
    public double getTerrainArea(){
        return area;
    }

    /**
     * Pega numero de rooms
     * @return ElfoNumber Rooms
     */
    public int getNumberOfRooms() {
        return rooms.size();
    }

    /**
     * Pega numero de rooms de um tipo
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
     * Pega preço de compra
     * @return buy price
     */
    public double getBuyPrice() {
        return buyPrice;
    }

    /**
     * Pega preço de aluguel
     * @return Rent Price
     */
    public double getRentPrice() {
        return rentPrice;
    }

    /**
     * Pega endereço
     * @return Andress
     */
    public String getAndress() {
        return andress;
    }

    /**
     * Se esta disponivel
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
     * Set Terrain Area
     * @param area Area
     */
    public void setTerrainArea(double area) throws ParameterOutOfTypeException {
        if(propertyType.isInArea(area)){
            this.area = area;
        }else{
            throw new ParameterOutOfTypeException("Area",propertyType.getArea());
        }
    }

    /**
     * Seta rooms
     * @param rooms
     */
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
     * Set Rent Price
     * @param price Price
     */
    public void setRentPrice(double price) throws ParameterOutOfTypeException {
        if(propertyType.isInRentPrice(price)){
            this.rentPrice = price;
        }else{
            throw new ParameterOutOfTypeException("Rent Price",propertyType.getRentPrice());
        }
    }

    /**
     * Set Buy Price
     * @param price Price
     */
    public void setBuyPrice(double price) throws ParameterOutOfTypeException {
        if(propertyType.isInBuyPrice(price)){
            this.buyPrice = price;
        }else{
            throw new ParameterOutOfTypeException("Buy Price",propertyType.getBuyPrice());
        }
    }

    /**
     * Set Andress
     * @param andress Andress
     */
    public void setAndress(String andress) {
        this.andress = andress;
    }

    /**
     * Seta numero de andares
     * @param floors Floors
     */
    public void setFloors(int floors) throws ParameterOutOfTypeException {
        this.floors = floors;
        if(propertyType.isInFloors(floors)){
            this.floors = floors;
        }else{
            throw new ParameterOutOfTypeException("Floors",propertyType.getFloor());
        }
    }

    /**
     * Se a area esta dentro do intervalo de deltaNumber
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the area is in the area boundary
     */
    public boolean isInArea(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(area);
    }

    /**
     * Se a Buy Price esta dentro do intervalo de deltaNumber
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the price is in the price boundary
     */
    public boolean isInBuyPrice(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(buyPrice);
    }

    /**
     * Se a Rent Price esta dentro do intervalo de deltaNumber
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the price is in the price boundary
     */
    public boolean isInRentPrice(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(rentPrice);
    }

    /**
     * Se a Floors esta dentro do intervalo de deltaNumber
     * @param deltaNumber Limit(DeltaNumber)
     * @return if the price is in the price boundary
     */
    public boolean isInFloors(DeltaNumber deltaNumber){
        return deltaNumber.isInDeltaNumber(floors);
    }

    /**
     * Se a Rooms esta dentro do intervalo de deltaNumber
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

    /**
     * Seta PropertyType
     * @param propertyType
     */
    public void setPropertyType(PropertyType propertyType){
        this.propertyType = propertyType;
    }

    /**
     * Pega rooms
     * @return rooms
     */
    public ArrayList<Room> getRooms(){
        return (ArrayList<Room>) rooms.clone();
    }

    /**
     * Pega nome do tipo de PropertyType
     * @return PropertyType Name
     */
    public String getPropertyTypeName(){
        return propertyType.getName();
    }

    /**
     * Pega PropertyType
     * @return
     */
    public PropertyType getPropertyType(){
        return propertyType;
    }

    /**
     * Pega numero de andares
     * @return Floors
     */
    public int getFloors(){
        return floors;
    }

    @Override
    public String toString(){
        String out = "Andress: " + andress +
        String.format("\nFloors:%d" +
                "\nTerrain Area:%.2f" +
                "\nRooms:", floors, area);
        for(String type : Room.TYPE_NAME){
            out += String.format("\n    %s - %d",type,this.getNumberOfRooms(type));
        }
        out += String.format("\n    Total area: %.2f", getArea()) +
                String.format("\nRent Price:$%.2f\nBuy Price::$%.2f\n",rentPrice,buyPrice) +
                "\n" + propertyType.getName() +
                "\n" + propertyType.getDescription();
        return out;
    }
}