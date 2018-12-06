package elfo.users;

import java.util.Scanner;

/**
 * Generic command class that does not depend on an object
 * @author Ezequias Moises dos Santos Silva
 * @version 0.0.13
 */
public class UserTools {
    static private final int cpfTam = 11;
    static private Scanner scanner;

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
     * @return Scanner Instace
     */
    static public Scanner getScanner(){
        if(scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    /**
     * @param password Password
     * @return if entered password meets minimum requirements
     */
    static public boolean authenticatePassword(String password){
        char[] pass = password.toCharArray();
        boolean[] points = new boolean[6];
        for(char c : pass){
            if(Character.isAlphabetic(c)){
                points[0] = true;
            }else if(Character.isDigit(c)){
                points[1] = true;
            }else if(Character.isLetter(c)){
                points[2] = true;
            }else if(Character.isLowerCase(c)){
                points[3] = true;
            }else if(Character.isUpperCase(c)){
                points[4] = true;
            }else if(!(Character.isLetter(c) && Character.isDigit(c)) && Character.isDefined(c)){
                points[5] = true;
            }
        }
        int totalPoints = 0;
        for(boolean b : points){
            if(b){
                totalPoints += 1;
            }
        }
        if(totalPoints >= 2){
            return true;
        }else{
            return false;
        }
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
     * @param cpf Array of CPF
     * @return if it is a valid CPF
     */
    static public boolean authenticateCpf(int... cpf){
        if(cpf.length != cpfTam){
            return false;
        }
        int[] posCpf = new int[11];
        for(int i = 0; i < cpf.length - 2; i++){
            posCpf[i] = cpf[i];
        }
        //ps# = processing status #
        int ps1 = sumOfMultiplicationFrom2Invert(2,cpf) % 11;
        posCpf[cpfTam - 2] = processRest(ps1);
        int ps2 = sumOfMultiplicationFrom2Invert(1,posCpf) % 11;
        posCpf[cpfTam - 1] = processRest(ps2);
        boolean out = true;
        for(int i = 0; i < cpfTam; i++){
            if(cpf[i] != posCpf[i]){
                out = false;
                break;
            }
        }
        return out;
    }
    /*
        Calculates remainder according to the Brazilian cpf validation rules
     */
    static private int processRest(int rest){
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
    static private int sumOfMultiplicationFrom2Invert(int ignore,int... s){
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
