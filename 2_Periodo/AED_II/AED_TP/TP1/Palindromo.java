
public class Palindromo {
    public static boolean Palindromo(String str){
        for(int i = 0, j = str.length() - 1; i < str.length()/2; i++, j--){
            if(str.charAt(i) != str.charAt(j)){
                MyIO.println("NAO");
                return false;
            }
        }
        MyIO.println("SIM");
        return true;
    }

    //funcao para verificar se a palavra digitada foi FIM
    public static boolean not_Fim(String str){
        boolean notFim = false;
        if(str.charAt(0) != 'F' || str.charAt(1) != 'I' || str.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }

    public static void main(String[] args){
        String str;
        boolean notFim = false;
        do {
            str = MyIO.readLine();
            notFim = not_Fim(str);
            if (!notFim) {
                break;
            }
            Palindromo(str);
        } while (notFim);
    }
}