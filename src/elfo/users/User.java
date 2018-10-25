package elfo.users;

public class User {
    private String name;
    private String lastName;
    private String fullName;
    private int typeUser;
    private int[] cpf;
    private String password;
    private int cpfTam;

    public User(String name, String lastName, String fullName, int[] cpf, String password){
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.cpf = cpf;
        this.password = password;
        this.typeUser = -1;
        this.cpfTam = 11;
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
    void setPassword(String password){
        this.password = password;
    }
    boolean isPassword(String password){
        return this.password.equals(password);
    }
    public int getTypeUser(){
        return typeUser;
    }
    void setTypeUser(int type){
        this.typeUser = type;
    }
}
