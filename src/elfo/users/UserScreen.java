package elfo.users;

import elfo.console.Menu;
import elfo.console.MenuList;

import java.util.Scanner;

public class UserScreen {
    private Menu menu;
    private int menuListIndex;
    private UserControl userControl;
    private MenuList menuList;
    private Scanner sc;
    private int nextScreen;
    public UserScreen(){
        sc = UserTools.getScanner();
        menu = Menu.getMenu();
        userControl = UserControl.getUserControl();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);
        menuList.addOption("Login",this::login);
        menuList.addOption("Create Account",this::creat);
    }
    public void creat(Menu m){
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
                    userControl.registerNewUser(fullName,cpf,pass,true);
                    userControl.login(cpf,pass);
                    menu.setMenuIndex(nextScreen);
                    break;
                }
            }
        }

    }
    public int getIndexMenu(){
        return menuListIndex;
    }
    public void setNextScreen(int screen){
        nextScreen = screen;
    }
    public void login(Menu m){
        boolean loop = true;
        int st = 0;
        int[] cpf = UserTools.getCpfNull();
        int countPassErrors = 0;
        sc.nextLine();
        while (loop){
            if(st == 0){
                System.out.printf("CPF:");
                String cpfString = sc.nextLine();
                cpf = UserTools.stringToCpf(cpfString);
                if(UserTools.authenticateCpf(cpf)){
                    st = 1;
                }
            }else if(st == 1){
                System.out.printf("Password:");
                String pass = sc.nextLine();
                if(userControl.login(cpf,pass)){
                    menu.setMenuIndex(nextScreen);
                    break;
                }else{
                    if(countPassErrors >= 3){
                        st = 0;
                        countPassErrors = 0;
                    }
                    countPassErrors++;
                }
            }
        }
    }
    public void runScreen(){

    }
}
