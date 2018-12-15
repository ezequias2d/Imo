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

    /**
     * Contrutor de tipo de propriedade
     * @param name Nome do tipo
     * @param description Descriçao do tipo
     */
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

    /**
     * Seta limite de andares
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitFloor(int min, int max){
        floor.setDelta(min,max);
    }

    /**
     * Seta limite de quartos
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitRoom(String type,int min, int max){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                room[i].setDelta(min,max);
                break;
            }
        }
    }

    /**
     * Seta limite de area de terreno
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitArea(double min, double max){
        area.setDelta(min,max);
    }

    /**
     * Seta limite de preço de compra
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitBuyPrice(double min, double max){
        buyPrice.setDelta(min,max);
    }

    /**
     * Seta limite de preço do aluguel
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitRentPrice(double min, double max){
        rentPrice.setDelta(min,max);
    }

    /**
     * Seta limite de quartos
     * Indefinido = -1
     * @param min Minimo
     * @param max Maximo
     */
    public void setLimitRooms(double min, double max){
        rooms.setDelta(min,max);
    }

    /**
     * Se esta dentro dos limites de Area
     * return True se sim
     */
    public boolean isInArea(double area){
        return this.area.isInDeltaNumber(area);
    }

    /**
     * Se esta dentro dos limites de preço de venda
     * return True se sim
     */
    public boolean isInBuyPrice(double price){
        return this.buyPrice.isInDeltaNumber(price);
    }

    /**
     * Se esta dentro dos limites de preço do aluguel
     * return True se sim
     */
    public boolean isInRentPrice(double price){
        return this.rentPrice.isInDeltaNumber(price);
    }

    /**
     * Se esta dentro dos limites de numero de quartos por tipo
     * return True se sim
     */
    public boolean isInRoom(String type,int num){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                return room[i].isInDeltaNumber(num);
            }
        }
        return false;
    }

    /**
     * Se esta dentro dos limites de numero de quartos
     * return True se sim
     */
    public boolean isInRooms(int num){
        return rooms.isInDeltaNumber(num);
    }

    /**
     * Se esta dentro dos limites de andares
     * return True se sim
     */
    public boolean isInFloors(double floors){
        return this.floor.isInDeltaNumber(floors);
    }

    /**
     * Pega DeltaNumber de area
     * @return DeltaNumber
     */
    public DeltaNumber getArea(){
        return area.getClone();
    }

    /**
     * Pega DeltaNumber de BuyPrice
     * @return DeltaNumber
     */
    public DeltaNumber getBuyPrice(){
        return buyPrice.getClone();
    }

    /**
     * Pega DeltaNumber de RentPrice
     * @return DeltaNumber
     */
    public DeltaNumber getRentPrice(){
        return rentPrice.getClone();
    }

    /**
     * Pega DeltaNumber de Floor
     * @return DeltaNumber
     */
    public DeltaNumber getFloor(){
        return floor.getClone();
    }

    /**
     * Pega DeltaNumber de um tipo de Room
     * @param type Tipo do quarto
     * @return DeltaNumber
     */
    public DeltaNumber getRoom(String type){
        for(int i = 0; i < room.length; i ++){
            if(Room.TYPE_NAME[i].equals(type)){
                return room[i].getClone();
            }
        }
        return null;
    }

    /**
     * Pega DeltaNumber de Rooms
     * @return DeltaNumber
     */
    public DeltaNumber getRooms(){
        return rooms.getClone();
    }

    /**
     * Pega nome do tipo
     * @return Name
     */
    public String getName(){
        return name;
    }

    /**
     * Pega descriçao do tipo
     * @return Description
     */
    public String getDescription(){
        return description;
    }

    @Override
    public String toString(){
        return name;
    }

    /**
     * Seta nome
     * @param name Name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Seta descriçao
     * @param description Description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Pega identificador
     * @return Identity
     */
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
