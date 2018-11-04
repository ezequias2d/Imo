package elfo.users;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserControl {
    public static final int LEVEL_ADM1 = 0;
    public static final int LEVEL_ADM2 = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_NOT_LOGGED = 3;

    private static UserControl userControl;

    private ArrayList<User> users;
    private User loadedAccount;
    private User notLoggedAccount;

    private UserControl(){
        this.users = new ArrayList<User>();

        //create default user
        User admin = new User("Admin","Admin","Admin",UserTools.getCpfNull(),"admin");
        admin.setTypeUser(LEVEL_ADM1);
        this.users.add(admin);

        notLoggedAccount = new User("","","",UserTools.getCpfNull(),"");
        notLoggedAccount.setTypeUser(LEVEL_NOT_LOGGED);
        loadedAccount = notLoggedAccount;
    }

    /**
     * @return Instance of UserControl
     */
    static public UserControl getInstance() {
        if (userControl == null) {
            userControl = new UserControl();
        }
        return userControl;
    }

    boolean add(User user){
        return users.add(user);
    }

    /**
     * @return if loaded user is ADM1
     */
    public boolean isADM1(){
        return loadedAccount.getTypeUser() == LEVEL_ADM1;
    }
    /**
     * @return if loaded user is ADM2
     */
    public boolean isADM2(){
        return loadedAccount.getTypeUser() == LEVEL_ADM2;
    }
    /**
     * @return if loaded user is NORMAL
     */
    public boolean isNORMAL(){
        return loadedAccount.getTypeUser() == LEVEL_NORMAL;
    }

    /**
     * @param level Level
     * @return if loaded user is level
     */
    public boolean isLevel(int level){
        return loadedAccount.getTypeUser() == level;
    }

    /**
     * @param password Old Password
     * @param newPassword New Password
     * @return if it was achieved
     */
    public boolean changeYourPassword(String password, String newPassword){
        if(this.loadedAccount.isPassword(password) && UserTools.authenticatePassword(newPassword)){
            this.loadedAccount.setPassword(newPassword);
            return true;
        }
        return false;
    }

    /**
     * @param password User Password
     * @param newFullName New Full Name
     * @return if it was achieved
     */
    public boolean changeYourName(String password, String newFullName){
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setName(newFullName);
            return true;
        }
        return false;
    }

    /**
     * @param password User Password
     * @param name New First Name
     * @param lastName New Last Name
     * @return if it was achieved
     */
    public boolean changeYourFormalName(String password, String name, String lastName){
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setFormalName(name,lastName);
            return true;
        }
        return false;
    }

    /**
     * @return Type of loaded user
     */
    public int getTypeOfUser(){
        return loadedAccount.getTypeUser();
    }

    /**
     *
     * @param ADM1Password ADM1
     * @param user User
     * @param newType New Type
     * @return if will change
     */
    public boolean changeTypeOfUser(String ADM1Password,User user, int newType){
        if(this.loadedAccount.isPassword(ADM1Password) && isADM1()){
            user.setTypeUser(newType);
            return true;
        }
        return false;
    }

    /**
     * @param user User to change
     * @param ADM1Password ADM1 Password
     * @param newPassword New Password
     * @return if will change
     */
    public boolean changePassword(User user, String ADM1Password, String newPassword){
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }

    /**
     * @param user User to change
     * @param ADM1Password ADM1 Password
     * @param newCpf New CPF
     * @return if changed
     */
    public boolean changeCpf(User user, String ADM1Password, int[] newCpf){
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setCpf(newCpf);
            return true;
        }
        return false;
    }

    /**
     * @return Current CPF
     */
    public int[] getCpfCurrent(){
        return loadedAccount.getCpf();
    }

    /**
     * @return Formal Name Current
     */
    public String getFormalNameCurrent(){
        return loadedAccount.getFormalName();
    }

    /**
     * @return Full Name Current
     */
    public String getFullNameCurrent(){
        return loadedAccount.getFullName();
    }
    /**
     * @param type Type
     * @return All user of Type
     */
    public ArrayList<User> getUsers(int type){
        ArrayList<User> usersType = new ArrayList<User>();
        for(User u: users){
            if(u.getTypeUser() == type){
                usersType.add(u);
            }
        }
        return usersType;
    }
    public ArrayList<User> getUsers(){
        ArrayList<User> out = new ArrayList<User>();
        out.addAll(users);
        return out;
    }

    /**
     * @param cpf CPF
     * @param type Type
     * @return User of CPF and Type
     */
    public User getUser(int[] cpf, int type){
        for(User u : getUsers(type)){
            if(u.isCpf(cpf)){
                return u;
            }
        }
        return null;
    }

    /**
     * @param cpf CPF
     * @return User of CPF
     */
    public User getUser(int[] cpf){
        for(User u : users){
            if(u.isCpf(cpf)){
                return u;
            }
        }
        return null;
    }

    /**
     * @param password Password
     * @return Permission
     */
    public boolean getPermission(String password){
        if(this.loadedAccount != this.notLoggedAccount){
            return loadedAccount.isPassword(password);
        }
        return false;
    }

    /**
     * @param cpf CPF to login
     * @param password Password of CPF to Login
     * @return If it can log in
     */
    public boolean login(int[] cpf, String password){
        for(User user : users){
            if(user.isCpf(cpf) && user.isPassword(password)){
                loadedAccount = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Logout
     * @return True
     */
    public boolean logout(){
        loadedAccount = notLoggedAccount;
        return true;
    }
}
