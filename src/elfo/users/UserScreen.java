package elfo.users;

import elfo.console.Menu;
import elfo.console.MenuList;

import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserScreen extends MenuList {
    static UserScreen userScreen;
    private int menuListIndex;
    private UserControl userControl;
    private Scanner sc;
    private int[] nextScreen;
    private UserScreen(){
        super(Menu.getInstance());
        sc = UserTools.getScanner();
        userControl = UserControl.getInstance();
        menuListIndex = menuHome.addMenu(this);
        nextScreen = new int[4];
        this.addOption("Login",this::login);
        this.addOption("Create Account",this::create);
        this.addOption("Exit", this::exit);
    }

    /**
     * @return Instance of UserScreen
     */
    public static UserScreen getInstace(){
        if(userScreen == null){
            userScreen = new UserScreen();
        }
        return userScreen;
    }

    /**
     * Create to your account(command of menu)
     * @param m Menu
     */
    public void create(Menu m){
        boolean loop = true;
        int st = 0;
        String fullName =  "";
        int[] cpf = UserTools.getCpfNull();
        int countCpfErros = 0;
        String pass;
        sc.nextLine();
        while (loop){
            if(st == 0){
                System.out.printf("Full Name:");
                fullName = sc.nextLine();
                st = 1;
            }else if(st == 1){
                System.out.printf("Cpf:");
                String cpfString = sc.nextLine();
                cpf = UserTools.stringToCpf(cpfString);
                if(UserTools.authenticateCpf(cpf)){
                    st = 2;
                }else{
                    if(countCpfErros >= 3){
                        st = 0;
                    }
                    countCpfErros++;
                }
            }else if(st == 2){
                System.out.printf("Password:");
                pass = sc.nextLine();
                System.out.printf("Confirm password:");
                String confirmPass = sc.nextLine();
                if(pass.equals(confirmPass)){
                    registerNewUser(fullName,cpf,pass,true);
                    menuHome.setMenuIndex(nextScreen[userControl.getTypeOfUser()]);
                    break;
                }
            }
        }

    }

    /**
     * @return Index of MenuList in Menu
     */
    public int getIndexMenu(){
        return menuListIndex;
    }

    /**
     * set next screen according to user type
     * @param screen Next screen index
     * @param type Associated user type
     */
    public void setNextScreen(int screen,int type){
        nextScreen[type] = screen;
    }

    /**
     * Login account(Menu Command)
     * @param m Menu
     */
    public void login(Menu m){
        boolean loop = true;
        int st = 0;
        int[] cpf = UserTools.getCpfNull();
        int countPassErrors = 0;
        int countCpfErrors = 0;
        sc.nextLine();
        while (loop){
            if(st == 0){
                System.out.printf("CPF:");
                String cpfString = sc.nextLine();
                cpf = UserTools.stringToCpf(cpfString);
                if(UserTools.authenticateCpf(cpf)){
                    st = 1;
                }else{
                    if(countCpfErrors >= 2){
                        break;
                    }
                    countCpfErrors++;
                }
            }else{
                System.out.printf("Password:");
                String pass = sc.nextLine();
                if(userControl.login(cpf,pass)){
                    menuHome.setMenuIndex(nextScreen[userControl.getTypeOfUser()]);
                    break;
                }else{
                    if(countPassErrors >= 2){
                        st = 0;
                        countPassErrors = 0;
                    }
                    countPassErrors++;
                }
            }
        }
    }
    /**
     * shows the screen to select the type of user, if the user who is creating the account is adm1
     * @return Type of User
     */
    private int insertType(){
        if(userControl.isADM1()){
            System.out.printf("0-ADM1 Account\n1-ADM2 Account\n2-Normal account\n->");
            return UserTools.getScanner().nextInt();
        }
        return UserControl.LEVEL_NORMAL;
    }

    /**
     * Register a new User
     * @param fullName Full Name
     * @param cpf CPF
     * @param password Password
     * @param ignoreConfirm Ignore Confirm
     * @return if will create
     */
    public boolean registerNewUser(String fullName, int[] cpf, String password,boolean ignoreConfirm){
        if(UserTools.authenticateCpf(cpf) && UserTools.authenticatePassword(password)){
            for (User user: userControl.getUsers()) {
                if(user.isCpf(cpf)){
                    //the cpf is registred
                    System.out.printf("\nThe user is registred!\n");
                    return false;
                }
            }
            String name = UserTools.getFirstName(fullName);
            if(ignoreConfirm) name = confirmName("is your first name?",name);
            String lastName = UserTools.getLastName(fullName);
            if(ignoreConfirm) lastName = confirmName("is your last name?",lastName);
            User user = new User(name,lastName,fullName,cpf,password);
            if(userControl.isLevel(UserControl.LEVEL_NOT_LOGGED)){
                user.setTypeUser(UserControl.LEVEL_NORMAL);
            }else if(userControl.isADM2()){
                System.out.printf("You should not be here!");
                return false;
            }else if(userControl.isADM1()){
                user.setTypeUser(insertType());
            }
            userControl.add(user);
            System.out.println("User registred");
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param msg Message
     * @param name Possible name
     * @return Name
     */
    private String confirmName(String msg,String name){
        boolean loop = true;
        while (loop) {
            System.out.printf("'%s' %s(y/n)",name,msg);
            Scanner sc = UserTools.getScanner();
            char c = sc.next().charAt(0);
            sc.nextLine();
            if (c == 'y') {
                loop = false;
                return name;
            } else if (c == 'n') {
                loop = false;
                System.out.printf(">");
                name = sc.nextLine();
                return name;
            }
        }
        return "";
    }

    /**
     * Menu Command
     * Logout
     * @param m MenuHome
     */
    public void logout(Menu m){
        userControl.logout();
        System.out.printf("\nLogout!\n");
    }

    /**
     * Menu Command
     * Exit
     * @param m MenuHome
     */
    public void exit(Menu m){
        System.exit(0);
    }
}
