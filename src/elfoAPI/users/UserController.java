package elfoAPI.users;

import elfoAPI.exception.ElfoException;
import elfoAPI.exception.data.DataCannotBeAccessedException;
import elfoAPI.exception.user.UserDoesNotExistForThisTypeException;
import elfoAPI.exception.user.UserInvalidException;
import elfoAPI.exception.user.permission.UserInvalidPermissionException;
import elfoAPI.exception.user.UserIsRegistredException;

import java.util.ArrayList;

/**
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserController {
    private static UserController userController;

    private UserRepository userRepository;

    //Guarda o usuario atual logado
    private User loadedAccount;
    //Guarda uma instancia de usuario do tipo User.LEVEL_NOT_LOGGED, que representa o usuario nao logado
    private User notLoggedAccount;

    private UserController() throws UserInvalidException, UserIsRegistredException, DataCannotBeAccessedException, ClassNotFoundException {
        userRepository = new UserRepository();
        notLoggedAccount = new User();
        notLoggedAccount.setTypeUser(User.LEVEL_NOT_LOGGED);

        /*
            O motivo dos dois serem iguais e que o sistema inicia
            deslogado.
            Quando usuario logar, eles seram diferentes
            Para deslogar o sistema simplesmente faz loadedAccount ser igual a notLoggedAccount
        */
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
            } catch (ElfoException | ClassNotFoundException e) {
                /*
                    Sistema simplesmente fecha aqui por ser sem sentindo o programa continuar se nao consegue acessar
                    seus dados de usuarios no disco ou cria-los

                    Teoricamente nunca deve chegar a este ponto, mas caso chegue e invevitavel o sistema nao funcionar
                    pelo simples motivo de tudo precisar de um usuario e/ou permisao de usuario.
                 */
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        return userController;
    }

    /**
     * Pega usuario atual
     * @return
     */
    public User getLoadedAccount(){
        return loadedAccount;
    }

    /**
     * Verifica se o sistema esta logado(Se o loadedAccount e diferente de notLoggedAccount)
     * @return
     */
    public boolean isLogged(){
        return loadedAccount != notLoggedAccount;
    }

    /**
     * Registra novo usuario
     *
     * @param fullName Full name
     * @param cpf CPF
     * @param password Password
     * @param firstName First Name
     * @param lastName Last name
     * @param userType User Type (User.LEVEL_*)
     * @throws UserIsRegistredException if user is registred
     * @throws UserInvalidException if user cpf or password is invalid
     * @throws DataCannotBeAccessedException if data cannot be acessed
     */
    public void registerNewUser(String fullName, int[] cpf, String password, String firstName, String lastName, int userType) throws UserIsRegistredException, UserInvalidException, DataCannotBeAccessedException {
        for (User user: userController.getUsers()) {
            if(user.isCpf(cpf)){
                //the cpf is registred
                throw new UserInvalidException();
            }
        }
        if(!isLogged() && userType == User.LEVEL_NORMAL || isLogged() && isADM1()){
            //user not logged
            //Se usuario nao logado, apenas tem permiçao de registrar usuario do tipo LEVEL_NORMAL
            //Se usuario logado e e do tipo ADM1, tem permiçao de registrar qualquer tipo
            User user = new User(firstName,lastName,fullName,cpf,password);
            user.setTypeUser(userType);
            if(userRepository.add(user)){
                return;
            }
        }
        //algo nao valido
        throw new UserInvalidException();
    }

    /**
     * Retorna true se usuario e do tipo ADM1
     * caso contrario falso
     * @return if loaded user is ADM1
     */
    public boolean isADM1(){
        return loadedAccount.getTypeUser() == User.LEVEL_ADM1;
    }
    /**
     * Retorna true se usuario e do tipo ADM2
     * caso contrario falso
     * @return if loaded user is ADM2
     */
    public boolean isADM2(){
        return loadedAccount.getTypeUser() == User.LEVEL_ADM2;
    }
    /**
     *
     * Retorna true se usuario e do tipo NORMAL
     * caso contrario falso
     * @return if loaded user is NORMAL
     */
    public boolean isNORMAL(){
        return loadedAccount.getTypeUser() == User.LEVEL_NORMAL;
    }

    /**
     * Retorna true se usuario e do tipo 'level'(fornecido na chamada da funçao)
     * caso contrario falso
     * @param level Level
     * @return if loaded user is level
     */
    public boolean isLevel(int level){
        return loadedAccount.getTypeUser() == level;
    }

    /**
     * Muda senha do usuario logado, se a senha forncida for a 'antiga' senha do usuario
     * @param password Old Password
     * @param newPassword New Password
     * @return if it was achieved
     * @throws DataCannotBeAccessedException Se nao conseguir acessar dados
     * @throws UserInvalidPermissionException Se permiçao invalida
     */
    public boolean changeYourPassword(String password, String newPassword) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        if(getPermission(password,loadedAccount.getTypeUser())){
            this.loadedAccount.setPassword(newPassword);
            userRepository.update();
            return true;
        }
        return false;
    }
    /**
     *
     *         Funçao de permiçao ADM1
     *
     *  Deleta uma conta pelo CPF fornecido se o usuario tem permissao para tal e inseriu senha correta
     *
     * @param password ADM1 Password
     * @param cpf CPF Account
     * @return if it was achieved
     * @throws DataCannotBeAccessedException Se nao conseguir acessar dados
     * @throws UserInvalidPermissionException Se permiçao invalida
     */
    public boolean deleteAccount(String password, int[] cpf) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        if(getPermission(password, User.LEVEL_ADM1)){//this.loadedAccount.isPassword(password) && isADM1() && (!loadedAccount.isCpf(cpf))){
            User userDelet = getUser(cpf);
            return userRepository.remove(userDelet);//users.remove(userDelet);
        }
        throw new UserInvalidPermissionException("ADM1");
    }


    /**
     * Muda nome do usuario logado se a sneha fornecida for a senha do usuario.
     *
     * @param password User Password
     * @param newFullName New Full Name
     * @return if it was achieved
     * @throws DataCannotBeAccessedException Se nao conseguir acessar dados
     * @throws UserInvalidPermissionException Se permiçao invalida
     */
    public boolean changeYourName(String password, String newFullName) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        if(getPermission(password,loadedAccount.getTypeUser())){//this.loadedAccount.isPassword(password)){
            this.loadedAccount.setName(newFullName);
            userRepository.update();
            return true;
        }
        return false;
    }

    /**
     * Muda nome formal do usuario se a senha for a do usuario
     *
     * @param password User Password
     * @param name New First Name
     * @param lastName New Last Name
     * @return if it was achieved
     * @throws DataCannotBeAccessedException Se nao conseguir acessar dados
     * @throws UserInvalidPermissionException Se permiçao invalida
     */
    public boolean changeYourFormalName(String password, String name, String lastName) throws DataCannotBeAccessedException, UserInvalidPermissionException {
        if(getPermission(password,loadedAccount.getTypeUser())){//this.loadedAccount.isPassword(password)){
            this.loadedAccount.setFormalName(name,lastName);
            userRepository.update();
            return true;
        }
        return false;
    }

    /**
     * >>ADM1<<
     * Pega tipo do usuario
     * @return Type of loaded user
     */
    public int getTypeOfUser(){
        return loadedAccount.getTypeUser();
    }

    /**
     * Muda tipo do usuario se a senha fornecida for a do usuario logado
     * e o usuario logado for do tipo ADM1
     *
     * @param password ADM1
     * @param user User
     * @param newType New Type
     * @return if will change
     */
    public void changeTypeOfUser(String password,User user, int newType) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        if(getPermission(password,User.LEVEL_ADM1)){//this.loadedAccount.isPassword(ADM1Password) && isADM1()){
            user.setTypeUser(newType);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
    }

    /**
     * >>ADM1<<
     * Muda senha de um usuario qualquer se o usuario logado for ADM1
     * e a senha dele estiver correta
     *
     * @param user User to change
     * @param password ADM1 Password
     * @param newPassword New Password
     * @return if will change
     */
    public void changePassword(User user, String password, String newPassword) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        if(getPermission(password,User.LEVEL_ADM1)){//this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setPassword(newPassword);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
    }

    /**
     * >>ADM1<<
     * Muda cpf de um usuario qualquer se o usuario logado for ADM1
     * e a senha dele estiver correta
     *
     * @param user User to change
     * @param password ADM1 Password
     * @param newCpf New CPF
     * @return if changed
     */
    public void changeCpf(User user, String password, int[] newCpf) throws UserInvalidPermissionException, DataCannotBeAccessedException {
        if(getPermission(password, User.LEVEL_ADM1)){//this.isADM1() && this.loadedAccount.isPassword(ADM1Password)){
            user.setCpf(newCpf);
            userRepository.update();
            return;
        }
        throw new UserInvalidPermissionException("ADM1");
    }

    /**
     * Pega cpf atual em vetor
     * @return Current CPF
     */
    public int[] getCpfCurrent(){
        return loadedAccount.getCpf();
    }

    /**
     * Pega CPF atual em string
     * @return CPF/Identity of user
     */
    public String getCurrentIdentity(){
        return loadedAccount.getIdentity();
    }

    /**
     * Pega nome formal
     * @return Formal Name Current
     */
    public String getFormalNameCurrent(){
        return loadedAccount.getFormalName();
    }

    /**
     * Pega nome completo
     * @return Full Name Current
     */
    public String getFullNameCurrent(){
        return loadedAccount.getFullName();
    }

    /**
     * Pega array de usuarios de um tipo especifico(User.LEVE_*)
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

    /**
     * Pega um array de todos os usuarios
     * @return
     */
    public User[] getUsers(){
        return userRepository.toArray();
    }

    /**
     * Pega usuario de um cpf e tipo, caso nao exista lança exceçao
     * @param cpf CPF
     * @param type Type
     * @return User of CPF and Type
     */
    public User getUser(int[] cpf, int type) throws UserDoesNotExistForThisTypeException {
        for(User u : getUsers(type)){
            if(u.isCpf(cpf)){
                return u;
            }
        }
        throw new UserDoesNotExistForThisTypeException(type);
    }

    /**
     * Pega usuario por cpf, sem restriçao de tipo
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
     * Pega usuario por cpf, sem restriçao de tipo
     * @param identity Identity
     * @return User of CPF
     */
    public User getUser(String identity){
        return userRepository.get(identity);
    }

    /**
     * Pede permiçao ao usuario logado de um tipo e com uma senha para validar
     * @param password Password
     * @return Permission
     */
    public boolean getPermission(String password, int levelType) throws UserInvalidPermissionException{
        if(this.loadedAccount != this.notLoggedAccount && isLevel(levelType)){
            return loadedAccount.isPassword(password);
        }
        if(levelType > 2 || levelType < 0){
            throw new UserInvalidPermissionException("INVALID LEVEL");
        }
        throw new UserInvalidPermissionException(User.STRINGS_USERS_TYPES[levelType]);
    }

    /**
     * Loga um usuario no sistema
     *
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
     * Desloga o sistema
     * Logout
     * @return True
     */
    public boolean logout(){
        loadedAccount = notLoggedAccount;
        return true;
    }


}
