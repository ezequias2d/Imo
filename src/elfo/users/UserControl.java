package elfo.users;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class UserControl {
    public static final int LEVEL_ADM1 = 0;
    public static final int LEVEL_ADM2 = 1;
    public static final int LEVEL_NORMAL = 2;
    public static final int LEVEL_NOT_LOGGED = 3;
    static private UserControl userControl;

    private ArrayList<User> users;
    private User loadedAccount;
    private User notLoggedAccount;
    private UserControl(){
        this.users = new ArrayList<User>();

        User admin = new User("Admin","Admin","Admin",UserTools.getCpfNull(),"admin");
        User admin2 = new User("Admin","Admin","Admin",UserTools.getCpfNull(),"admin2");
        admin.setTypeUser(LEVEL_ADM1);
        admin2.setTypeUser(LEVEL_ADM2);
        this.users.add(admin);
        this.users.add(admin2);

        notLoggedAccount = new User("","","",UserTools.getCpfNull(),"");
        notLoggedAccount.setTypeUser(LEVEL_NOT_LOGGED);
        loadedAccount = notLoggedAccount;
    }
    static public UserControl getUserControl() {
        if (userControl == null) {
            userControl = new UserControl();
        }
        return userControl;
    }

    public boolean isADM1(){
        return loadedAccount.getTypeUser() == LEVEL_ADM1;
    }
    public boolean isADM2(){
        return loadedAccount.getTypeUser() == LEVEL_ADM2;
    }
    public boolean isNORMAL(){
        return loadedAccount.getTypeUser() == LEVEL_NORMAL;
    }

    public boolean isLevel(int level){
        return loadedAccount.getTypeUser() == level;
    }

    public boolean changeYourPassword(String password, String newPassword){
        if(this.loadedAccount.isPassword(password) && UserTools.authenticatePassword(newPassword)){
            this.loadedAccount.setPassword(newPassword);
            return true;
        }
        return false;
    }
    public boolean changeYourName(String password, String newFullName){
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setName(newFullName);
            return true;
        }
        return false;
    }
    public boolean changeYourFormalName(String password, String name, String lastName){
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setFormalName(name,lastName);
            return true;
        }
        return false;
    }

    private String confirmName(String msg,String name){
        boolean loop = true;
        while (loop) {
            System.out.printf("'%s' %s(y/n)",name,msg);
            Scanner sc = UserTools.getScanner();
            char c = sc.next().charAt(0);
            sc.nextLine();
            if (c == 'y') {
                return name;
            } else if (c == 'n') {
                System.out.printf(">");
                name = sc.nextLine();
                return name;
            }
        }
        return "";
    }
    public int getTypeOfUser(){
        return loadedAccount.getTypeUser();
    }
    private int inserType(){
        if(isADM1()){
            System.out.printf("0-ADM1 Account\n1-ADM2 Account\n2-Normal account\n->");
            return UserTools.getScanner().nextInt();
        }
        return LEVEL_NORMAL;
    }
    public boolean registerNewUser(String fullName, int[] cpf, String password,boolean ignoreConfirm){
        if(UserTools.authenticateCpf(cpf) && UserTools.authenticatePassword(password)){
            for (User user: users) {
                if(user.isCpf(cpf)){
                    //the cpf is registred
                    return false;
                }
            }
            String name = UserTools.getFirstName(fullName);
            if(ignoreConfirm) name = confirmName("is your first name?",name);
            String lastName = UserTools.getLastName(fullName);
            if(ignoreConfirm) lastName = confirmName("is your last name?",lastName);
            User user = new User(name,lastName,fullName,cpf,password);
            if(isLevel(LEVEL_NOT_LOGGED)){
                user.setTypeUser(LEVEL_NORMAL);
            }else if(isLevel(LEVEL_ADM2)){
                System.out.printf("You should not be here!");
                return false;
            }else if(isLevel(LEVEL_ADM1)){
                user.setTypeUser(inserType());
            }
            users.add(user);
            System.out.println("User registred");
            return true;
        }else{
            return false;
        }
    }

    public boolean changeTypeOfUser(String ADM1Password,User user, int newType){
        if(this.loadedAccount.isPassword(ADM1Password)){
            user.setTypeUser(newType);
            return true;
        }
        return false;
    }

    public boolean changePassword(User user, String ADM1Password, String newPassword){
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setPassword(newPassword);
            return true;
        }
        return false;
    }
    public boolean changeCpf(User user, String ADM1Password, int[] newCpf){
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setCpf(newCpf);
            return true;
        }
        return false;
    }
    public int[] getCpfCurrent(){
        return loadedAccount.getCpf();
    }
    public String getFormalNameCurrent(){
        return loadedAccount.getFormalName();
    }
    public ArrayList<User> getUsers(int type){
        ArrayList<User> usersType = new ArrayList<User>();
        for(User u: users){
            if(u.getTypeUser() == type){
                usersType.add(u);
            }
        }
        return usersType;
    }
    public User getUserOfCpfAndType(int[] cpf, int type){
        for(User u : getUsers(type)){
            if(u.isCpf(cpf)){
                return u;
            }
        }
        return null;
    }
    public User getUser(int[] cpf){
        for(User u : users){
            if(u.isCpf(cpf)){
                return u;
            }
        }
        return null;
    }
    public boolean getPermission(String password){
        if(this.loadedAccount != this.notLoggedAccount){
            return loadedAccount.isPassword(password);
        }
        return false;
    }
    public boolean login(int[] cpf, String password){
        for(User user : users){
            if(user.isCpf(cpf) && user.isPassword(password)){
                loadedAccount = user;
                System.out.printf("\nEntered successfully\n");
                return true;
            }
        }
        return false;
    }
    public void logout(){
        loadedAccount = notLoggedAccount;
        System.out.printf("\nLogout successfully\n");
    }
}
