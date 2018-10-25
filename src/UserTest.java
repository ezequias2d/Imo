import elfo.users.User;
import elfo.users.UserTools;

public class UserTest {
    static public void main(String[] argv){
        int[] cpf = {6,0,3,4,6,7,7,6,0,2,3};
        System.out.println(UserTools.authenticateCpf(cpf));
    }
}
