package AED_EX_REV;

public class u00h_7 {
    public static boolean palindromo(char[] palavra, int tamanho){
        if(tamanho != 0){
            if(palavra[tamanho -1] == palavra[0]){
                return palindromo(palavra, tamanho-1);
            }
        }else{
            return false;
        }
        return true;
    }
}
