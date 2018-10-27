package imo.property;

import java.util.ArrayList;
import elfo.number.Number;

public class Floor {
    private ArrayList<Room> rooms;
    int numberFloor;

    public Floor(int numberFloor){
        this.numberFloor = numberFloor;
        rooms = new ArrayList<Room>();
    }
    public int getNumberFloor(){
        return numberFloor;
    }
    public void changeNumberFloor(Floor floor){
        int antNumber = floor.numberFloor;
        floor.numberFloor = this.numberFloor;
        this.numberFloor  = antNumber;
    }
    public boolean addRoom(Room room){
        return rooms.add(room);
    }
    public ArrayList<Room> getRooms(){
        return rooms;
    }
    public Number getNumberOfRooms(){
        return new Number(rooms.size());
    }
    public Room getRoom(int index){
        return rooms.get(index);
    }
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
