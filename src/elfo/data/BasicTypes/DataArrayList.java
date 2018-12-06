package elfo.data.BasicTypes;

import elfo.data.DataBasic;
import elfo.users.DataUser;

import java.util.ArrayList;

public class DataArrayList<S, T extends DataBasic<S>> extends DataBasic<ArrayList<T>> {

    private T supportInstance;

    public DataArrayList(String name,T supportInstance) {
        super(name, new ArrayList<T>());
        this.supportInstance = supportInstance;
        this.increaseLevel(1);
    }

    public DataArrayList(String name,ArrayList<T> meta,T supportInstance) {
        super(name, meta);
        this.supportInstance = supportInstance;
        this.increaseLevel(1);
        for(T t : meta){
            this.addSubData(t);
            //t.increaseLevel(getLevel());
        }
    }

    public ArrayList<T> getMeta() {
        ArrayList<T> clone = new ArrayList<T>();
        clone.addAll(this.meta);
        return clone;
    }

    public boolean add(T element){
        this.addSubData(element);
        return this.meta.add(element);
    }

    public boolean remove(T element){
        this.removeSubData(element);
        return this.meta.remove(element);
    }

    public T get(String name){
        for(T db : meta){
            if(db.getName().equals(name)){
                return db;
            }
        }
        return null;
    }

    public ArrayList<S> getElements(){
        ArrayList<S> arrayListS = new ArrayList<S>();
        for(T t : meta){
            arrayListS.add(t.getMeta());
        }
        return arrayListS;
    }

    @SuppressWarnings("unchecked")
    public DataBasic<T>[] toArrayDataBasic(){
        DataBasic[] dataBasics;
        dataBasics = (DataBasic<T>[]) meta.toArray();
        return dataBasics;
    }

    @Override
    public void increaseLevel(int n){
        super.increaseLevel(n);
        for(T t : meta){
            t.increaseLevel(n);
        }
    }

    @Override
    public DataBasic<ArrayList<T>> newInstace(String name, ArrayList<T> meta) {
        return new DataArrayList<S,T>(name,supportInstance);
    }

    @Override
    public ArrayList<T> newSubInstance() {
        return new ArrayList<T>();
    }

    public boolean loadData(ArrayList<T> meta) {
        this.meta.removeAll(this.meta);
        return this.meta.addAll(meta);
    }

    @Override
    public boolean loadData(String str, boolean ignoreName) {
        //String[] splitStr = str.split(regex);
        String[] ungummed = ungum(str);
        if (ungummed[0].equals(getTypeName()) &&
                (ungummed[1].equals(getName()) || ignoreName)) {
            ArrayList<T> loaded = new ArrayList<T>();
            for (int i = 2; i < ungummed.length; i++) {
                loaded.add((T) supportInstance.newInstace(ungummed[i]));
            }
            loadData(loaded);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        regexGum(getTypeName());
        regexGum(getName());
        for (T elem : meta) {
            regexGum(elem.toString());
        }
        return regexGumAssimilate();
    }

}
