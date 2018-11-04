package imo.property;

import java.util.ArrayList;
import elfo.number.Number;

/**
 * @author Jose Romulo Pereira
 * @version 0.0.4
 */
public class Floor {
    private ArrayList<Room> rooms;

    /**
     * Constructor
     */
    public Floor(){
        rooms = new ArrayList<Room>();
    }

    /**
     * @return Floor Area
     */
    public double getArea(){
        double out = 0;
        for(Room r : rooms){
            out += r.getArea();
        }
        return out;
    }

    /**
     * @param room Room
     * @return if added
     */
    public boolean add(Room room){
        return rooms.add(room);
    }

    /**
     * @return Rooms
     */
    public ArrayList<Room> getRooms(){
        return rooms;
    }

    /**
     * @return Number of Rooms
     */
    public Number getNumberOfRooms(){
        return new Number(rooms.size());
    }

    /**
     * @param index Index
     * @return room of index
     */
    public Room getRoom(int index){
        return rooms.get(index);
    }

    /**
     * @param type Type
     * @return Number of Rooms of type
     */
    public Number getNumberOfRooms(int type){
        Number count = new Number();
        for(int i = 0; i < rooms.size(); i++){
            if(rooms.get(i).getType() == type){
                count.increase();
            }
        }
        return count;
    }
}
