package elfo.users;

import elfo.data.IRepositorio;
import elfo.data.Serializer;
import elfo.exception.data.DataCannotBeAccessedException;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;

import java.io.IOException;
import java.util.ArrayList;

public class UserRepository implements IRepositorio<User> {
    private static final String URI_DEPOT = "src/resources/depot/users.dat";
    private ArrayList<User> users;
    private Serializer serializer;

    /**
     * @throws UserInvalidException
     * @throws UserIsRegistredException
     * @throws ClassNotFoundException
     * @throws DataCannotBeAccessedException
     */
    public UserRepository() throws ClassNotFoundException, DataCannotBeAccessedException {
        try {
            User notLoggedAccount = new User();

            notLoggedAccount.setTypeUser(User.LEVEL_NOT_LOGGED);
            //System.out.print("\nCarregamdo elementos..\n");
            serializer = Serializer.getInstance();

            try {
                this.users = (ArrayList<User>)serializer.open(URI_DEPOT);
            } catch (IOException e) {
                //System.out.print("\nSem banco de usuarios persistente!\nCriando novo...\n");
                User admin = new User("Admin","Admin","Admin",UserTools.getCpfNull(),"admin");
                admin.setTypeUser(User.LEVEL_ADM1);
                users = new ArrayList<User>();
                users.add(admin);
                serializer.save(URI_DEPOT,users);
            }


        } catch (UserInvalidException | UserIsRegistredException e) {
            //Sem sentido continuar e impossivel de acontecer.
            e.printStackTrace();
            System.exit(0);
        }
    }


    /**
     * Adiciona objeto
     * @param object Objeto
     * @return Se adicionado
     * @throws DataCannotBeAccessedException Data cannot be acessed
     */
    @Override
    public boolean add(User object) throws DataCannotBeAccessedException {
        boolean out = users.add(object);
        if(out){
               serializer.save(URI_DEPOT,users);
        }
        return out;
    }

    /**
     * Remove objeto
     * @param object Objeto
     * @return Se removido
     * @throws DataCannotBeAccessedException Data cannot be acessed
     */
    @Override
    public boolean remove(User object) throws DataCannotBeAccessedException {
        boolean out = users.remove(object);
        if(out){
            serializer.save(URI_DEPOT,users);
        }
        return out;
    }

    /**
     * Pega objeto por index
     * @param index
     * @return
     */
    @Override
    public User get(int index) {
        return users.get(index);
    }

    /**
     * Pega objeto por identificador
     * @param indent
     * @return
     */
    @Override
    public User get(String indent) {
        for(User user : users){
            String cpfUser = user.getIdentity();
            if(indent.equals(cpfUser)){
                return user;
            }
        }
        return null;
    }

    /**
     * Pega index do objeto
     * @param object
     * @return
     */
    @Override
    public int get(User object) {
        return users.indexOf(object);
    }

    /**
     * Cria um array com objetos do repostorio
     * @return array dos objetos
     */
    @Override
    public User[] toArray(){
        User[] usersArray = new User[users.size()];
        for(int i = 0; i < users.size(); i ++){
            usersArray[i] = users.get(i);
        }
        return usersArray;
    }

    /**
     * Atualiza dados persistentes
     * @throws DataCannotBeAccessedException
     */
    @Override
    public void update() throws DataCannotBeAccessedException {
        serializer.save(URI_DEPOT, users);
    }
}
