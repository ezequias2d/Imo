package imo.menu.screens;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.users.UserControl;
import elfo.users.UserTools;

import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class AccountScreen extends MenuList {
    private int menuListIndex;
    private Scanner sc;
    private UserControl userControl;

    /**
     * Constructor
     */
    public AccountScreen(){
        super(Menu.getInstance());
        menuListIndex = menuHome.addMenu(this);
        sc = UserTools.getScanner();
        userControl = UserControl.getInstance();
    }

    /**
     * @return Index of Menu
     */
    public int getMenuListIndex(){
        return menuListIndex;
    }

    /**
     * Sets the menu generically
     */
    public void genericChangePasswordScreen(){
        this.addOption("Show Account",this::showAccount);
        this.addOption("Change Your Password",this::changePassword);
        this.addOption("Change Your Full Name", this::changeFullName);
        this.addOption("Change Your Formal Name", this::changeFormalName);
    }

    /**
     * sets the menu for ADM1
     */
    public void ADM1ChangePasswordScreen(){
        genericChangePasswordScreen();
        this.addOption("Change Password",this::changePasswordADM1);
        this.addOption("Change Cpf", this::changeCpfADM1);
    }

    /**
     * Menu command
     * @param menu MenuHome
     */
    private void showAccount(Menu menu){
        System.out.printf("Full Name:%s\nFormal Name:%s\nCpf:%s\n",
                userControl.getFullNameCurrent(),
                userControl.getFormalNameCurrent(),
                UserTools.convertCpfToString(userControl.getCpfCurrent()));
    }

    /**
     * Menu Command
     * @param menu MenuHome
     */
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

    /**
     * Menu command
     * @param menu MenuHome
     */
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

    /**
     * Menu Command
     * @param menu MenuHome
     */
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
    /**
     * Menu Command
     * @param menu MenuHome
     */
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
    /**
     * Menu Command
     * @param menu MenuHome
     */
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
