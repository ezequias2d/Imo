package elfo.users;

import elfo.data.DataBasic;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class User {
    public static final int LEVEL_ADM1 = 0;
    public static final int LEVEL_ADM2 = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_NOT_LOGGED = 3;

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
    public User(String name, String lastName, String fullName, int[] cpf, String password) throws UserInvalidException, UserIsRegistredException {
        boolean flag = false;
        int[] nullCPF = UserTools.getCpfNull();
        for(int i = 0; i < nullCPF.length; i++){
            if(nullCPF[i] != cpf[i]){
                flag = true;
                break;
            }
        }
        if(flag) {
            if (UserController.getInstance().getUser(cpf) == null) {
                throw new UserIsRegistredException(cpf);
            }
            if (UserTools.authenticateCpf(cpf) || UserTools.authenticatePassword(password)) {
                throw new UserInvalidException();
            }
        }
        this.name = name;
        this.lastName = lastName;
        this.fullName = fullName;
        this.cpf = cpf;
        this.password = password;
        this.typeUser = -1;
    }

    public User() throws UserInvalidException, UserIsRegistredException, UserIsRegistredException{
        this("","","",UserTools.getCpfNull(),"");
    }

    User(String[] detail){
        //String[] splitDetail = detail.split(getRegex());
        this.name = detail[0];
        this.lastName = detail[1];
        this.fullName = detail[2];
        this.typeUser = Integer.valueOf(detail[3]);
        this.cpf = UserTools.stringToCpf(detail[4]);
        this.password = detail[5];
    }

    public String getCpfString(){
        return UserTools.convertCpfToString(cpf);
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
        this.lastName = lastName;
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

    String getDetail(){
        String regex = "▮▮";
        return regex +
                name + regex +
                lastName + regex +
                fullName + regex +
                typeUser + regex +
                UserTools.convertCpfToString(cpf) + regex +
                password;
    }
}
