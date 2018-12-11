package imo.property;

import elfo.data.IIdentifiable;
import elfo.number.DeltaNumber;

import java.io.Serializable;


/**
 * Classe destinada a definir, restringir ou limitar uma propriedade(Construçao)
 */
public class PropertyType implements Serializable, IIdentifiable {
    /*
    public static String HOUSE = "House";
    public static String ROW_HOUSE = "Row House";
    public static String APARTAMENT = "Apartment";
    public static String EMPTY_GROUND = "Empty Ground";
    public static String BUILDING = "Building";
    */
    private String name;
    private String description;
    private DeltaNumber floor;
    private DeltaNumber[] room;
    private DeltaNumber area;
    private DeltaNumber buyPrice;
    private DeltaNumber rentPrice;


    PropertyType(String name, String description){
        this.name = name;
        this.description = description;
        this.floor = new DeltaNumber();
        this.room = new DeltaNumber[Room.TYPE_NAME.length];
        for(int i = 0; i < room.length; i ++){
            this.room[i] = new DeltaNumber();
        }
        this.area = new DeltaNumber();
        this.buyPrice = new DeltaNumber();
        this.rentPrice = new DeltaNumber();
    }

    public void setLimitFloor(int min, int max){
        floor.setDelta(min,max);
    }

    public void setLimitRoom(String type,int min, int max){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                room[i].setDelta(min,max);
                break;
            }
        }
    }

    public void setLimitArea(double min, double max){
        area.setDelta(min,max);
    }

    public void setLimitBuyPrice(double min, double max){
        buyPrice.setDelta(min,max);
    }

    public void setLimitRentPrice(double min, double max){
        rentPrice.setDelta(min,max);
    }

    public boolean isInArea(double area){
        return this.area.isInDeltaNumber(area);
    }
    public boolean isInBuyPrice(double price){
        return this.buyPrice.isInDeltaNumber(price);
    }
    public boolean isInRentPrice(double price){
        return this.rentPrice.isInDeltaNumber(price);
    }
    public boolean isInRoom(String type,int num){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                return room[i].isInDeltaNumber(num);
            }
        }
        return false;
    }
    public boolean isInFloors(double floors){
        return this.floor.isInDeltaNumber(floors);
    }
    public DeltaNumber getArea(){
        return area.getClone();
    }
    public DeltaNumber getBuyPrice(){
        return buyPrice.getClone();
    }
    public DeltaNumber getRentPrice(){
        return rentPrice.getClone();
    }
    public DeltaNumber getFloor(){
        return floor.getClone();
    }
    public DeltaNumber getRoom(String type){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                return room[i].getClone();
            }
        }
        return null;
    }
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String toString(){
        return name;
    }

    @Override
    public String getIdentity() {
        return name;
    }
}
