package elfo.users;

import elfo.data.BasicTypes.DataArrayList;
import elfo.data.DataBasic;
import elfo.data.FileReg;
import elfo.data.IRepositorio;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;

import java.io.IOException;
import java.util.ArrayList;

public class UserRepository implements IRepositorio<User> {
    public static final String URI_DEPOT = "src/resources/depot/users.edd";

    private ArrayList<User> users;
    private User notLoggedAccount;

    private DataArrayList<User,DataUser> dataUserDataArrayList;
    private FileReg<DataArrayList> fileReg;

    public UserRepository() throws UserInvalidException, UserIsRegistredException {
        notLoggedAccount = new User();
        notLoggedAccount.setTypeUser(User.LEVEL_NOT_LOGGED);

        dataUserDataArrayList = new DataArrayList<User,DataUser>("users",new DataUser(notLoggedAccount));
        fileReg = new FileReg<DataArrayList>(dataUserDataArrayList);
        if(!fileReg.loadFile(URI_DEPOT)){
            System.out.printf("\nSem banco de usuarios persistente!\nCriando novo...\n");
            User admin = new User("Admin","Admin","Admin",UserTools.getCpfNull(),"admin");
            admin.setTypeUser(User.LEVEL_ADM1);
            dataUserDataArrayList.add(new DataUser(admin));
            fileReg.loadMeta(dataUserDataArrayList);
            fileReg.writeMeta(URI_DEPOT);
        }
        System.out.printf("\nCarregamdo elementos..\n");
        this.users = dataUserDataArrayList.getElements();
    }


    @Override
    public boolean add(User object) {
        dataUserDataArrayList.add(new DataUser(object));
        fileReg.writeMeta(URI_DEPOT);
        return users.add(object);
    }

    @Override
    public boolean remove(User object) {
        for(DataBasic dataBasic : dataUserDataArrayList.toArrayDataBasic()){
            if(dataBasic.getMeta() == object){
                dataUserDataArrayList.remove((DataUser) dataBasic);
            }
        }
        fileReg.writeMeta(URI_DEPOT);
        return users.remove(object);
    }

    @Override
    public User get(int index) {
        return users.get(index);
    }

    @Override
    public User get(String indent) {
        for(User user : users){
            String cpfUser = user.getCpfString();
            if(indent.equals(cpfUser)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int get(User object) {
        return users.indexOf(object);
    }

    @Override
    public User[] toArray(){
        User[] usersArray = new User[users.size()];
        for(int i = 0; i < users.size(); i ++){
            usersArray[i] = users.get(i);
        }
        return usersArray;
    }

    @Override
    public void update() throws IOException {
        fileReg.writeMeta(URI_DEPOT);
    }
}
