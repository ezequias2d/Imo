package elfo.users;

import java.util.ArrayList;
import java.util.Scanner;

public class UserControl {
    static final int ADM_L1_TYPE = 0;
    static final int ADM_L2_TYPE = 1;
    static final int NORMAL_TYPE = 2;

    static private UserControl userControl;

    private ArrayList<User> users;
    private User loadedAccount;
    private UserControl(){
        this.users = new ArrayList<User>();
        User admin = new User("Admin","Admin","Admin",new int[]{0,0,0,0,0,0,0,0,0,0,0},"admin");
        admin.setTypeUser(ADM_L1_TYPE);
        this.users.add(admin);
    }
    static public UserControl getUserControl() {
        if (userControl == null) {
            userControl = new UserControl();
        }
        return userControl;
    }

    public boolean isADM1(){
        return loadedAccount.getTypeUser() == ADM_L1_TYPE;
    }
    public boolean isADM2(){
        return loadedAccount.getTypeUser() == ADM_L2_TYPE;
    }
    public boolean isNORMAL(){
        return loadedAccount.getTypeUser() == NORMAL_TYPE;
    }

    public boolean changeYourPassword(String password, String newPassword){
        if(this.loadedAccount.isPassword(password) && UserTools.authenticatePassword(password)){
            this.loadedAccount.setPassword(newPassword);
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
            users.add(user);
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
}
