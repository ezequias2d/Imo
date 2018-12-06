package elfo.data;


import elfo.data.BasicTypes.DataArrayList;

import java.util.ArrayList;

public abstract class DataBasic<T> {
    private String typeName;
    protected T meta;
    protected String regex;
    private String name;
    private int level;
    private ArrayList<DataBasic> subData;
    private String regexGumSource;

    public DataBasic(String name,T meta){
        subData = new ArrayList<DataBasic>();
        regexGumSource = "";
        this.meta = meta;
        this.typeName = meta.getClass().getName();
        this.name = name;
        this.level = -1;
        this.regex = "▶▶";
    }
    public boolean addSubData(DataBasic element){
        element.increaseLevel(this.getLevel() + 1 - element.getLevel());
        return subData.add(element);
    }
    public boolean removeSubData(DataBasic element){
        element.increaseLevel(-(this.getLevel() + 1 - element.getLevel()));
        return subData.remove(element);
    }
    public int getLevel(){
        return level;
    }
    public void increaseLevel(int n){
        char[] charRegex = regex.toCharArray();
        for(char c: charRegex){
            c += n;
        }
        for(DataBasic db : subData){
            db.increaseLevel(n);
        }
        regex = String.valueOf(charRegex);
    }
    public String getTypeName(){
        return typeName;
    }
    public String getName(){
        return name;
    }
    public boolean isType(Object obj){
        return obj.getClass().getName().equals(typeName);
    }

    protected String regexGumAssimilate(){
        String out = regexGumSource;
        regexGumSource = "";
        return out;
    }
    protected void regexGum(String info){
        regexGumSource += regex + info;
    }
    protected void regexGum(int info){
        regexGumSource += regex + info;
    }
    protected void regexGum(double info){
        regexGumSource += regex + info;
    }

    protected String[] ungum(String gum){
        return gum.substring(2).split(gum.substring(0,2));
    }

    public String toString(){
        regexGum(getTypeName());
        regexGum(getName());
        regexGum(getMeta().toString());
        return regexGumAssimilate();
    }

    public boolean loadData(T meta){
        this.meta = meta;
        return true;
    }
    public T getMeta(){
        return meta;
    }

    public DataBasic<T> newInstace(String str){
        String[] splitStr = str.split(str.substring(0,1));
        System.out.printf("\nnewInstance:%s",str);
        DataBasic<T> instance = newInstace(splitStr[1], newSubInstance());
        instance.loadData(str,true);
        return instance;
    }

    public abstract DataBasic<T> newInstace(String name, T meta);
    public abstract boolean loadData(String str,boolean ignoreName);
    public abstract T newSubInstance();
}
