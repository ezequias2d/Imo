package imo.property;

import java.util.ArrayList;
import elfo.number.Number;

public class Floor {
    private ArrayList<Room> rooms;
    private ArrayList<Feature> features;
    int numberFloor;

    public Floor(int numberFloor){
        this.numberFloor = numberFloor;
        rooms = new ArrayList<Room>();
        features = new ArrayList<Feature>();
    }

    public double getArea(){
        double out = 0;
        for(Room r : rooms){
            out += r.getArea();
        }
        return out;
    }

    public int getNumberFloor(){
        return numberFloor;
    }
    public void changeNumberFloor(Floor floor){
        int antNumber = floor.numberFloor;
        floor.numberFloor = this.numberFloor;
        this.numberFloor  = antNumber;
    }
    public boolean add(Room room){
        return rooms.add(room);
    }
    public boolean add(Feature feature){
        return features.add(feature);
    }
    public ArrayList<Room> getRooms(){
        return rooms;
    }
    public ArrayList<Feature> getFeatures(){
        return features;
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
