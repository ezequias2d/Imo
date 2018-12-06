package elfo.data.BasicTypes;

import elfo.data.DataBasic;

public class DataString extends DataBasic<String> {

    public DataString(String name, String meta){
        super(name,meta);
    }

    @Override
    public boolean loadData(String str, boolean ignoreName) {
        String[] splitStr = str.split(regex);
        if (splitStr[0].equals(getTypeName()) &&
                (splitStr[1].equals(getName()) || ignoreName)) {
            loadData(splitStr[2]);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public DataBasic<String> newInstace(String name, String meta) {
        return null;
    }

    @Override
    public String newSubInstance() {
        return null;
    }
}
