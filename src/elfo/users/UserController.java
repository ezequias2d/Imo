package elfo.users;

import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserInvalidPermissionException;
import elfo.exception.user.UserIsRegistredException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserController {
    private static UserController userController;

    //private ArrayList<User> users;
    private UserRepository userRepository;
    private User loadedAccount;
    private User notLoggedAccount;
    private IUserInput userPermission;

    private UserController() throws UserInvalidException, UserIsRegistredException {
        //this.users = new ArrayList<User>();
        userRepository = new UserRepository();
        //create default user
        notLoggedAccount = new User();
        notLoggedAccount.setTypeUser(User.LEVEL_NOT_LOGGED);
        loadedAccount = notLoggedAccount;

        UserController.userController = this;

    }

    /**
     * @return Instance of UserController
     */
    static public UserController getInstance() {
        if (userController == null) {
            try {
                new UserController();
            } catch (UserIsRegistredException | UserInvalidException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return userController;
    }
    public User getLoadedAccount(){
        return loadedAccount;
    }

    public void setUserPermission(IUserInput userPermission){
        this.userPermission = userPermission;
    }

    public boolean isLogged(){
        return loadedAccount != notLoggedAccount;
    }

    public boolean registerNewUser(String fullName, int[] cpf, String password, String firstName, String lastName, int userType) throws UserIsRegistredException, UserInvalidException {
        if(UserTools.authenticateCpf(cpf) && UserTools.authenticatePassword(password)){
            for (User user: userController.getUsers()) {
                if(user.isCpf(cpf)){
                    //the cpf is registred
                    System.out.printf("\nThe user is registred!\n");
                    return false;
                }
            }

            if(!isLogged() && userType == User.LEVEL_NORMAL ||
                    isLogged() && isADM1()){
                //user not logged
                //Se usuario nao logado, apenas tem permiçao de registrar usuario do tipo LEVEL_NORMAL
                //Se usuario logado e e do tipo ADM1, tem permiçao de registrar qualquer tipo

                User user = new User(firstName,lastName,fullName,cpf,password);

                user.setTypeUser(userType);

                if(userRepository.add(user)){
                    System.out.println("User registred!");
                    return true;
                }

            }

        }
        //algo nao valido
        return false;
    }

    /**
     * @return if loaded user is ADM1
     */
    public boolean isADM1(){
        return loadedAccount.getTypeUser() == User.LEVEL_ADM1;
    }
    /**
     * @return if loaded user is ADM2
     */
    public boolean isADM2(){
        return loadedAccount.getTypeUser() == User.LEVEL_ADM2;
    }
    /**
     * @return if loaded user is NORMAL
     */
    public boolean isNORMAL(){
        return loadedAccount.getTypeUser() == User.LEVEL_NORMAL;
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
    public boolean changeYourPassword(String password, String newPassword) throws IOException {
        if(this.loadedAccount.isPassword(password) && UserTools.authenticatePassword(newPassword)){
            this.loadedAccount.setPassword(newPassword);
            userRepository.update();
            return true;
        }
        return false;
    }
    /**
     * @param password ADM1 Password
     * @param cpf CPF Account
     * @return if it was achieved
     */
    public boolean deleteAccount(String password, int[] cpf) throws UserInvalidPermissionException{
        if(this.loadedAccount.isPassword(password) && isADM1() && (!loadedAccount.isCpf(cpf))){
            User userDelet = getUser(cpf);
            return userRepository.remove(userDelet);//users.remove(userDelet);
        }
        throw new UserInvalidPermissionException("ADM1");
    }


    /**
     * @param password User Password
     * @param newFullName New Full Name
     * @return if it was achieved
     */
    public boolean changeYourName(String password, String newFullName) throws IOException {
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setName(newFullName);
            userRepository.update();
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
    public boolean changeYourFormalName(String password, String name, String lastName) throws IOException {
        if(this.loadedAccount.isPassword(password)){
            this.loadedAccount.setFormalName(name,lastName);
            userRepository.update();
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
    public void changeTypeOfUser(String ADM1Password,User user, int newType) throws UserInvalidPermissionException, IOException {
        if(this.loadedAccount.isPassword(ADM1Password) && isADM1()){
            user.setTypeUser(newType);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
    }

    /**
     * @param user User to change
     * @param ADM1Password ADM1 Password
     * @param newPassword New Password
     * @return if will change
     */
    public void changePassword(User user, String ADM1Password, String newPassword) throws UserInvalidPermissionException, IOException {
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setPassword(newPassword);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
    }

    /**
     * @param user User to change
     * @param ADM1Password ADM1 Password
     * @param newCpf New CPF
     * @return if changed
     */
    public void changeCpf(User user, String ADM1Password, int[] newCpf) throws UserInvalidPermissionException, IOException {
        if(this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setCpf(newCpf);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
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
    public User[] getUsers(int type){
        ArrayList<User> usersType = new ArrayList<User>();
        for(User u: userRepository.toArray()){
            if(u.getTypeUser() == type){
                usersType.add(u);
            }
        }
        User[] out = new User[usersType.size()];
        for(int i = 0; i < out.length; i++){
            out[i] = usersType.get(i);
        }
        return out;
    }
    public User[] getUsers(){
        return userRepository.toArray();
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
        for(User u : userRepository.toArray()){
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
        for(User user : userRepository.toArray()){
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
