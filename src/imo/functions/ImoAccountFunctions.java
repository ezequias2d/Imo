package imo.functions;

import elfo.exception.user.UserInvalidPermissionException;
import elfo.users.IUserInput;
import elfo.users.User;
import elfo.users.UserController;
import elfo.users.UserTools;

import java.io.IOException;

public class ImoAccountFunctions {
    private UserController userController;
    private IUserInput userInput;
    /**
     * Constructor
     */
    public ImoAccountFunctions(IUserInput userInput){
        userController = UserController.getInstance();
        this.userInput = userInput;
    }
    /**
     *
     */
    public String getAccountInfo(){
        return String.format("Full Name:%s\nFormal Name:%s\nCpf:%s\n",
                userController.getFullNameCurrent(),
                userController.getFormalNameCurrent(),
                UserTools.convertCpfToString(userController.getCpfCurrent()));
    }

    /**
     * Menu Command
     */
    public void changePassword() throws IOException {
        String oldPassword = userInput.getPassword("Old Password");
        String newPassword = userInput.getPassword("New Password");
        userController.changeYourPassword(oldPassword,newPassword);
    }

    /**
     * Menu command
     */
    public void changeFullName() throws IOException {
        String password = userInput.getPassword("Password");
        String fullname = userInput.getText("Full Name");
        userController.changeYourName(password,fullname);
    }

    /**
     * Menu Command
     */
    public void changeFormalName() throws IOException {
        String password = userInput.getPassword("Password");
        String firstName = userInput.getText("First Name");
        String lastName = userInput.getText("Last Name");
        userController.changeYourFormalName(password,firstName,lastName);
    }
    /**
     * Menu Command
     */

    public void changePasswordADM1() throws UserInvalidPermissionException, IOException {
        String password = userInput.getPassword("ADM1 Password");
        int[] cpf = userInput.getCPF("CPF");
        String newPassword = userInput.getPassword("New Password");
        userController.changePassword(userController.getUser(cpf),password,newPassword);
    }
    /**
     * Menu Command
     */
    public void changeCpfADM1() throws UserInvalidPermissionException, IOException {
        String password = userInput.getPassword("AM1 Password");
        int[] cpf = userInput.getCPF("CPF");
        int[] newCpf = userInput.getCPF("New CPF");
        userController.changeCpf(userController.getUser(cpf),password,newCpf);
    }

    /**
     */
    public void deleteAccount() throws UserInvalidPermissionException {
        String password = userInput.getPassword("ADM Password");
        int[] cpf = userInput.getCPF("CPF");
        userController.deleteAccount(password,cpf);
    }
}
