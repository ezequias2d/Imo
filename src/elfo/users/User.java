package elfo.users;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class User {
    private String name;
    private String lastName;
    private String fullName;
    private int typeUser;
    private int[] cpf;
    private String password;

    public User(String name, String lastName, String fullName, int[] cpf, String password){
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.cpf = cpf;
        this.password = password;
        this.typeUser = -1;
    }
    public boolean isCpf(int[] cpf){
        if(cpf.length != this.cpf.length){
            return false;
        }
        boolean out = true;
        for(int i = 0; i < cpf.length; i++){
            if(cpf[i] != this.cpf[i]){
                out = false;
                break;
            }
        }
        return out;
    }
    void setName(String fullName){
        this.name = UserTools.getFirstName(fullName);
        this.lastName = UserTools.getLastName(fullName);
        this.fullName = fullName;
    }
    void setFormalName(String name, String lastName){
        this.name = name;
        this.lastName = name;
    }
    void setPassword(String password){
        this.password = password;
    }
    void setCpf(int[] cpf){
        this.cpf = cpf;
    }
    boolean isPassword(String password){
        return this.password.equals(password);
    }
    public int getTypeUser(){
        return typeUser;
    }
    public int[] getCpf(){
        return cpf;
    }
    public String getFullName(){
        return fullName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getFormalName(){
        return getName() + " " + getLastName();
    }
    void setTypeUser(int type){
        this.typeUser = type;
    }
    public String getName(){
        return name;
    }
}
