package imo.menu.actions;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.6
 */
public class AccountScreen {
    private Menu menu;
    private int menuListIndex;
    private MenuList menuList;
    private Scanner sc;
    private UserControl userControl;
    public AccountScreen(){
        menu = Menu.getMenu();
        menuListIndex = menu.creatMenu();
        menuList = menu.getMenuList(menuListIndex);
        sc = UserTools.getScanner();
        userControl = UserControl.getUserControl();
    }
    public int getMenuListIndex(){
        return menuListIndex;
    }
    public void genericChangePasswordScreen(){
        menuList.addOption("Show Account",this::showAccount);
        menuList.addOption("Change Your Password",this::changePassword);
        menuList.addOption("Change Your Full Name", this::changeFullName);
        menuList.addOption("Change Your Formal Name", this::changeFormalName);
    }
    public void ADM1ChangePasswordScreen(){
        genericChangePasswordScreen();
        menuList.addOption("Change Password",this::changePasswordADM1);
        menuList.addOption("Change Cpf", this::changeCpfADM1);
    }
    private void showAccount(Menu menu){
        System.out.printf("Full Name:%s\nFormal Name:\nCpf:%s\n",
                userControl.getFormalNameCurrent(),
                userControl.getFormalNameCurrent(),
                UserTools.convertCpfToString(userControl.getCpfCurrent()));
    }
    private void changePassword(Menu menu){
        sc.nextLine();
        System.out.printf("Your password>");
        String pass = sc.nextLine();
        System.out.printf("New password>");
        String newPass = sc.nextLine();
        if(userControl.changeYourPassword(pass,newPass)){
            System.out.printf("\nchanged successfully!\n");
        }else{
            System.out.printf("\nno change\n");
        }
    }
    private void changeFullName(Menu menu){
        sc.nextLine();
        System.out.printf("Your password>");
        String pass = sc.nextLine();
        System.out.printf("New FullName>");
        String fullname = sc.nextLine();
        if(userControl.changeYourName(pass,fullname)){
            System.out.printf("\nchanged successfully!\n");
        }else{
            System.out.printf("\nno change\n");
        }
    }
    private void changeFormalName(Menu menu){
        sc.nextLine();
        System.out.printf("Your password>");
        String pass = sc.nextLine();
        System.out.printf("New Name>");
        String name = sc.nextLine();
        System.out.printf("New Last Name>");
        String last = sc.nextLine();
        if(userControl.changeYourFormalName(pass,name,last)){
            System.out.printf("\nchanged successfully!\n");
        }else{
            System.out.printf("\nno change\n");
        }
    }
    private void changePasswordADM1(Menu menu){
        sc.next();
        System.out.printf("Your password>");
        String pass = sc.next();
        System.out.printf("CPF Account>");
        int[] cpf = UserTools.stringToCpf(sc.next());
        System.out.printf("New password>");
        String newPass = sc.next();
        if(userControl.changePassword(userControl.getUser(cpf),pass,newPass)){
            System.out.printf("\nchanged successfully!\n");
        }else{
            System.out.printf("\nno change\n");
        }
    }
    private void changeCpfADM1(Menu menu){
        System.out.printf("Your password>");
        String pass = sc.next();
        System.out.printf("CPF Account>");
        int[] cpf = UserTools.stringToCpf(sc.next());
        System.out.printf("New CPF>");
        int[] newCpf = UserTools.stringToCpf(sc.next());
        if(userControl.changeCpf(userControl.getUser(cpf),pass,newCpf)){
            System.out.printf("\nchanged successfully!\n");
        }else{
            System.out.printf("\nno change\n");
        }
    }
}
