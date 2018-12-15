package imo.property;

import elfoAPI.data.IIdentificable;
import elfoAPI.number.DeltaNumber;

import java.io.Serializable;


/**
 * Classe destinada a definir, restringir e/ou limitar uma propriedade(Construçao)
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.2
 */
public class PropertyType implements Serializable, IIdentificable {
    private String name;
    private String description;
    private DeltaNumber floor;
    private DeltaNumber[] room;
    private DeltaNumber area;
    private DeltaNumber buyPrice;
    private DeltaNumber rentPrice;
    private DeltaNumber rooms;


    public PropertyType(String name, String description){
        this.name = name;
        this.description = description;
        this.floor = new DeltaNumber();
        this.rooms = new DeltaNumber();
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

    public void setLimitRooms(double min, double max){
        rooms.setDelta(min,max);
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
    public boolean isInRooms(int num){
        return rooms.isInDeltaNumber(num);
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
    public DeltaNumber getRooms(){
        return rooms.getClone();
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

    public void setName(String name){
        this.name = name;
    }
    public void setDescription(String description){
        this.description = description;
    }
    @Override
    public String getIdentity() {
        return name;
    }
    @Override
    public boolean equals(Object object){
        if(object instanceof PropertyType){
            PropertyType propertyType = (PropertyType) object;
            return propertyType.getIdentity().equals(name);
        }
        return false;
    }
}
