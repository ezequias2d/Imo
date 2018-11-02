package imo.property;
import elfo.number.Number;

public class PropertyView {
    private PropertyControl propertyControl;
    static public PropertyView propertyView;
    private PropertyView(){
        propertyControl = PropertyControl.getPropertyControl();
    }
    public static PropertyView getInstance(){
        if(propertyView == null){
            propertyView = new PropertyView();
        }
        return propertyView;
    }
    public String getViewOfProperty(Property property){
        String out = property.getName();
        Number area = property.getArea();
        Number price = property.getPrice();
        out += String.format("\nFloors:%d" +
                "\nArea:%.2f", property.getNumberOfFloor().getIntValue(), area.getValue());
        if(property.isMeasured()){
            out += String.format("\nWidth:%.2f\nLength:%.2f", property.getWidth(),property.getLength());
        }
        out += String.format("\nRooms:");
        for(int i = 0; i < Room.TYPE_NAME.length; i++){
            out += String.format("\n    %s - %d",Room.TYPE_NAME[i],property.getNumberOfRooms(i).getIntValue());
        }
        out += String.format("\nPrice:$%.2f\n",price.getValue());
        return out;
    }
}
