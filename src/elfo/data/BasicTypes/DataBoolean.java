package elfo.data.BasicTypes;

import elfo.data.DataBasic;

public class DataBoolean extends DataBasic<Boolean> {

    public DataBoolean(String name,Boolean meta){
        super(name,meta);
    }

    @Override
    public boolean loadData(String str, boolean ignoreName) {
        String[] ungummed = ungum(str);
        if (ungummed[0].equals(getTypeName()) &&
                (ungummed[1].equals(getName()) || ignoreName)) {
            loadData(Boolean.valueOf(ungummed[2]));
        } else {
            return false;
        }
        return true;
    }

    @Override
    public DataBasic<Boolean> newInstace(String name, Boolean meta) {
        return new DataBoolean(name,meta);
    }

    @Override
    public Boolean newSubInstance() {
        return false;
    }
}
