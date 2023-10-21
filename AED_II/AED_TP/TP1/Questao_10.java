

public class Questao_10 {
    
    public static boolean isPalindromoRec(String palavra_digitada, int inicio, int tamanho){
    if (inicio >= tamanho) {
        return true;
    }
    if(palavra_digitada.charAt(inicio) != palavra_digitada.charAt(tamanho)){
        return false;
    }else{
        return isPalindromoRec(palavra_digitada, inicio + 1, tamanho - 1);
    }
}


    public static boolean not_Fim(String palavra_digitada){
        boolean notFim = false;
        if(palavra_digitada.charAt(0) != 'F' || palavra_digitada.charAt(1) != 'I' || palavra_digitada.charAt(2) != 'M'){
            notFim = true;
        }
        return notFim;
   }
    public static void main (String[] args){
        String palavra_digitada;
        boolean notFim = true;
        boolean isPalindromo;
        do{
            palavra_digitada = MyIO.readLine();
            notFim = not_Fim(palavra_digitada);
            if(!notFim){
                break;
            }
            isPalindromo = isPalindromoRec(palavra_digitada, 0, palavra_digitada.length() - 1);
            if(isPalindromo){
                MyIO.println("SIM");
            }else{
                MyIO.println("NAO");
            }
        }while(notFim);
    }
}
