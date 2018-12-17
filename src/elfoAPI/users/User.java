package elfoAPI.users;

import elfoAPI.data.IIdentificable;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.UserIsRegistredException;

import java.io.Serializable;

/**
 * Classe que representa um usuario do sistema.
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.4
 */
public class User implements Serializable, IIdentificable {
    public static final int LEVEL_ADM1 = 0;
    public static final int LEVEL_ADM2 = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_NOT_LOGGED = 3;
    public static final String[] STRINGS_USERS_TYPES = new String[]{"ADM1","ADM2","NORMAL","NOT_LOGGED"};

    private String name;
    private String lastName;
    private String fullName;
    private int typeUser;
    private int[] cpf;
    private String password;

    /**
     * Cria usuario
     * @param name Fist Name
     * @param lastName Last NAme
     * @param fullName Full Name
     * @param cpf CPF
     * @param password Password
     * @throws UserInvalidException CPF ou Senha invalido
     * @throws UserIsRegistredException Usuario registrado
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
            if (UserController.getInstance().getUser(cpf) != null) {
                throw new UserIsRegistredException(cpf);
            }
            if (!(authenticateCpf(cpf) || authenticatePassword(password))) {
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

    /**
     * Usuario nulo
     */
    public User() throws UserInvalidException, UserIsRegistredException{
        this("","","",UserTools.getCpfNull(),"");
    }

    /**
     * Autentica usuario
     * @param cpf Array of CPF
     * @return if it is a valid CPF
     */
    private boolean authenticateCpf(int... cpf){
        int cpfTam = 11;
        if(cpf.length != cpfTam){
            return false;
        }
        int[] posCpf = new int[11];
        for(int i = 0; i < cpf.length - 2; i++){
            posCpf[i] = cpf[i];
        }
        //ps# = processing status #
        int ps1 = UserTools.sumOfMultiplicationFrom2Invert(2,cpf) % 11;
        posCpf[cpfTam - 2] = UserTools.processRest(ps1);
        int ps2 = UserTools.sumOfMultiplicationFrom2Invert(1,posCpf) % 11;
        posCpf[cpfTam - 1] = UserTools.processRest(ps2);
        boolean out = true;
        for(int i = 0; i < cpfTam; i++){
            if(cpf[i] != posCpf[i]){
                out = false;
                break;
            }
        }
        return out;
    }
    /**
     * Autentica senha
     * @param password Password
     * @return if entered password meets minimum requirements
     */
    private boolean authenticatePassword(String password){
        char[] pass = password.toCharArray();
        boolean[] points = new boolean[6];
        for(char c : pass){
            if(Character.isAlphabetic(c)){
                points[0] = true;
            }else if(Character.isDigit(c)){
                points[1] = true;
            }else if(Character.isLetter(c)){
                points[2] = true;
            }else if(Character.isLowerCase(c)){
                points[3] = true;
            }else if(Character.isUpperCase(c)){
                points[4] = true;
            }else if(!(Character.isLetter(c) && Character.isDigit(c)) && Character.isDefined(c)){
                points[5] = true;
            }
        }
        int totalPoints = 0;
        for(boolean b : points){
            if(b){
                totalPoints += 1;
            }
        }
        if(totalPoints >= 2){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Pega identifdicador
     * @return Identity(CPF in String)
     */
    @Override
    public String getIdentity(){
        return UserTools.convertCpfToString(cpf);
    }

    /**
     * Verifica se CPF fornecido e o do usuario
     * @param cpf CPF
     * @return if it's the account's CPF
     */
    boolean isCpf(int[] cpf){
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
     * Set FullName(seta nome completo)
     * @param fullName Full Name
     */
    void setName(String fullName){
        this.name = UserTools.getFirstName(fullName);
        this.lastName = UserTools.getLastName(fullName);
        this.fullName = fullName;
    }

    /**
     * Set First and Last Name(Seta nome e sobre nome completo)
     * @param name First Name
     * @param lastName Last Name
     */
    void setFormalName(String name, String lastName){
        this.name = name;
        this.lastName = lastName;
    }

    /**
     * Set Password(Seta senha)
     * @param password Password
     */
    void setPassword(String password){
        this.password = password;
    }

    /**
     * Set CPF(Seta CPF)
     * @param cpf CPF
     */
    void setCpf(int[] cpf){
        this.cpf = cpf;
    }

    /**
     * Verifica se senha fornecida e a do usuario
     * @param password Password
     * @return if this is the account password
     */
    boolean isPassword(String password){
        return this.password.equals(password);
    }

    /**
     * Pega tipo do usuario
     * @return Type of User
     */
    public int getTypeUser(){
        return typeUser;
    }

    /**
     * Pega CPF em array de inteiros
     * @return CPF
     */
    public int[] getCpf(){
        return cpf;
    }

    /**
     * Pega nome completo
     * @return Full Name
     */
    public String getFullName(){
        return fullName;
    }

    /**
     * Pega ultimo nome
     * @return Last Name
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * Pega nome formal(primeiro + ultimo nome)
     * @return Formal Name(First Name + Last Name)
     */
    public String getFormalName(){
        return getName() + " " + getLastName();
    }

    /**
     * Set Type(Seta um tipo)
     * @param type Type
     */
    void setTypeUser(int type){
        this.typeUser = type;
    }

    /**
     * Pega primeiro nome
     * @return First Name
     */
    public String getName(){
        return name;
    }

}
