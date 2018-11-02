package imo.property;

public class Feature {
    public static final int ROOF = 0;
    public static final int ELEVATOR = 1;
    public static final int STAIRWAY = 2;
    public static final int DOOR = 3;
    public static final int WINDOW = 4;

    public static final String[] TYPE_NAME = {"Roof","Elevator","Stairway","Door","Window"};

    private int type;

    public Feature(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public boolean isType(int type){
        return this.type == type;
    }
}
