package elfo.users;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class User {
    private String name;
    private String lastName;
    private String fullName;
    private int typeUser;
    private int[] cpf;
    private String password;

    /**
     * @param name Fist Name
     * @param lastName Last NAme
     * @param fullName Full Name
     * @param cpf CPF
     * @param password Password
     */
    public User(String name, String lastName, String fullName, int[] cpf, String password){
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.cpf = cpf;
        this.password = password;
        this.typeUser = -1;
    }

    /**
     * @param cpf CPF
     * @return if it's the account's CPF
     */
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

    /**
     *  Set FullName
     * @param fullName Full Name
     */
    void setName(String fullName){
        this.name = UserTools.getFirstName(fullName);
        this.lastName = UserTools.getLastName(fullName);
        this.fullName = fullName;
    }

    /**
     *  Set First and Last Name
     * @param name First Name
     * @param lastName Last Name
     */
    void setFormalName(String name, String lastName){
        this.name = name;
        this.lastName = name;
    }

    /**
     * Set Password
     * @param password Password
     */
    void setPassword(String password){
        this.password = password;
    }

    /**
     * Set CPF
     * @param cpf CPF
     */
    void setCpf(int[] cpf){
        this.cpf = cpf;
    }

    /**
     * @param password Password
     * @return if this is the account password
     */
    boolean isPassword(String password){
        return this.password.equals(password);
    }

    /**
     * @return Type of User
     */
    public int getTypeUser(){
        return typeUser;
    }

    /**
     * @return CPF
     */
    public int[] getCpf(){
        return cpf;
    }

    /**
     * @return Full Name
     */
    public String getFullName(){
        return fullName;
    }

    /**
     * @return Last Name
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * @return Formal Name(First Name + Last Name)
     */
    public String getFormalName(){
        return getName() + " " + getLastName();
    }

    /**
     * Set Type
     * @param type Type
     */
    void setTypeUser(int type){
        this.typeUser = type;
    }

    /**
     * @return First Name
     */
    public String getName(){
        return name;
    }
}
