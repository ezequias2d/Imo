package imo.menu.screens;

import elfo.console.Menu;
import elfo.console.MenuList;
import elfo.exception.user.UserInvalidPermissionException;
import elfo.users.UserController;
import elfo.users.UserTools;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.4
 */
public class AccountScreen extends MenuList {
    private int menuListIndex;
    private Scanner sc;
    private UserController userController;

    /**
     * Constructor
     */
    public AccountScreen(){
        super(Menu.getInstance());
        menuListIndex = menuHome.addMenu(this);
        sc = UserTools.getScanner();
        userController = UserController.getInstance();
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
    public void genericChangeInfoScreen(){
        this.addOption("Show Account",this::showAccount);
        this.addOption("Change Your Password",this::changePassword);
        this.addOption("Change Your Full Name", this::changeFullName);
        this.addOption("Change Your Formal Name", this::changeFormalName);
    }

    /**
     * sets the menu for ADM1
     */
    public void adm1ChangeInfoScreen(){
        this.addOption("Delete Account",this::deleteAccount);
        genericChangeInfoScreen();
        this.addOption("Change Password",this::changePasswordADM1);
        this.addOption("Change Cpf", this::changeCpfADM1);
    }

    /**
     * Menu command
     * @param menu MenuHome
     */
    private void showAccount(Menu menu){
        System.out.printf("Full Name:%s\nFormal Name:%s\nCpf:%s\n",
                userController.getFullNameCurrent(),
                userController.getFormalNameCurrent(),
                UserTools.convertCpfToString(userController.getCpfCurrent()));
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

        try {
            if(userController.changeYourPassword(pass,newPass)){
                System.out.printf("\nchanged successfully!\n");
            }else{
                System.out.printf("\nno change\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            if(userController.changeYourName(pass,fullname)){
                System.out.printf("\nchanged successfully!\n");
            }else{
                System.out.printf("\nno change\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        try {
            if(userController.changeYourFormalName(pass,name,last)){
                System.out.printf("\nchanged successfully!\n");
            }else{
                System.out.printf("\nno change\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        try{
            userController.changePassword(userController.getUser(cpf),pass,newPass);
            System.out.printf("\nchanged successfully!\n");
        } catch (UserInvalidPermissionException | IOException e) {
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
        try{
            userController.changeCpf(userController.getUser(cpf),pass,newCpf);
            System.out.printf("\nchanged successfully!\n");
        } catch (UserInvalidPermissionException e) {
            System.out.printf("\nno change\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param menu MenuHome
     */
    private void deleteAccount(Menu menu){
        System.out.printf("CPF to delete>");
        int[] cpf = UserTools.stringToCpf(sc.next());
        System.out.printf("ADM1 Password>");
        String pass = sc.next();
        try {
            if(userController.deleteAccount(pass,cpf)){
                System.out.printf("\nDeleted!\n");
            }
        } catch (UserInvalidPermissionException e) {
            e.printStackTrace();
        }
    }
}
