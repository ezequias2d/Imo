package elfo.users;


/**
 * Generic command class that does not depend on an object
 * Funçoes utilitarias do sistema 'users'
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserTools {
    static private final int cpfTam = 11;

    /**
     * @param name Name
     * @return Last Name of Name
     */
    static public String getLastName(String name){
        String[] nameSp = name.split(" ");
        return nameSp[nameSp.length - 1];
    }

    /**
     * @param name Name
     * @return First Name of Name
     */
    static public String getFirstName(String name){
        String[] nameSp = name.split(" ");
        return nameSp[0];
    }


    /**
     * @return Array of integers filled with 0
     */
    static public int[] getCpfNull(){
        return new int[cpfTam];
    }

    /**
     * @param s String of CPF
     * @return Array of integers filled with CPF
     */
    static public int[] stringToCpf(String s){
        int[] cpf = new int[cpfTam];
        char[] cpfChar = s.toCharArray();
        for(int i = 0; i < cpfTam && cpfChar.length >= cpfTam; i++){
            cpf[i] = Character.getNumericValue(cpfChar[i]);
        }
        if(cpfChar.length < cpfTam) return new int[0];
        return cpf;
    }

    /**
        Calculates remainder according to the Brazilian cpf validation rules
     @param rest Valor para calcular resto
     */
    static public int processRest(int rest){
        if(rest < 2){
            rest = 0;
        }else{
            rest = 11 - rest;
        }
        return rest;
    }

    /**
     * |1|5|6|7|2|4|6|8|1|  |X|Y| = S(Array of Numbers)
     * if ignore == 2: X and Y is ignored
     * |10|9|8|7|6|5|4|3|2| = vector starting with two, arranged in reverse order to multiply
     * | 1*10 | 5*9 | 6*8 | 7*7 | 2*6 | 4*5 | 6*4 | 8*3 | 1*2 |
     *  1* 10 + 5*9 + 6*8 + 7*7 + 2*6 + 4*5 + 6*4 + 8*3 + 1*2 = Return
     * @param ignore ElfoNumber of numbers at the end ignore
     * @param s Array of Numbers
     * @return Internal product of the vector not ignored by another that ends with 2 and is decreasing
     */
    static public int sumOfMultiplicationFrom2Invert(int ignore,int... s){
        int out = 0;
        int tam = s.length;
        for(int i = 2, j = 1; j != 0; i++){
            j = tam - i - ignore + 1;
            out += i * (s[j]);
        }
        return out;
    }

    /**
     * @param cpf Array of CPF
     * @return String Form of CPF
     */
    static public String convertCpfToString(int... cpf){
        String cpfString = "";
        for(int n : cpf){
            cpfString += String.valueOf(n);
        }
        return cpfString;
    }
}
