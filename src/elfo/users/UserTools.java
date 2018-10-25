package elfo.users;

import java.util.Scanner;

public class UserTools {
    static private final int cpfTam = 11;
    static private Scanner scanner;

    static public String getLastName(String name){
        String[] nameSp = name.split(" ");
        return nameSp[nameSp.length - 1];
    }
    static public String getFirstName(String name){
        String[] nameSp = name.split(" ");
        return nameSp[0];
    }
    static public Scanner getScanner(){
        if(scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

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
        if(totalPoints >= 3){
            return true;
        }else{
            return false;
        }
    }
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
    static private int processRest(int rest){
        if(rest < 2){
            rest = 0;
        }else{
            rest = 11 - rest;
        }
        return rest;
    }
    static private int sumOfMultiplicationFrom2Invert(int ignore,int... s){
        int out = 0;
        int tam = s.length;
        for(int i = 2, j = 1; j != 0; i++){
            j = tam - i - ignore + 1;
            out += i * (s[j]);
        }
        return out;
    }
}
