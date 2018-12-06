package elfo.users;

import elfo.data.DataBasic;
import elfo.exception.user.UserInvalidException;
import elfo.exception.user.UserIsRegistredException;

public class DataUser extends DataBasic<User> {

    public DataUser(User meta){
        super(meta.getFullName(),meta);
    }
    public boolean loadData(String str,boolean ignoreName){
        loadData(new User(ungum(str)));
        System.out.printf("\n%s\n",meta.getDetail());
        return true;
    }

    @Override
    public User newSubInstance() {
        try {
            return new User();
        } catch (UserIsRegistredException | UserInvalidException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        return meta.getDetail();
    }

    @Override
    public DataBasic<User> newInstace(String name, User meta) {
        return new DataUser(meta);
    }
}
