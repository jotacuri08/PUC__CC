public class Ciframento_cesar {
    public static void main (String[] args){
        char[] array_char = new char[1000];
        String palavra = new String();
        boolean notFim = true;
        int tamanho;
        do{
            palavra = MyIO.readLine();
            notFim = not_Fim(palavra); 
            converter_string(palavra, array_char); 
            tamanho = palavra.length();
            ciframento(array_char, tamanho);

        }while(notFim);
    }

    //funcao para verificar se a palavra digitada foi FIM
    public static boolean not_Fim(String palavra){
        boolean notFim = false;
        if(palavra.charAt(0) != 'F' || palavra.charAt(1) != 'I' || palavra.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }

    //funcao para converter a string em array de char
    public static void converter_string(String palavra, char[] array_char){
        for(int i = 0; i < palavra.length(); i++){
            array_char[i] = palavra.charAt(i);
        }
    }

    //funcao para efetuar o ciframento
    public static void ciframento(char[] array_char, int tamanho){
        
        for(int i = 0; i < tamanho; i++){
            array_char[i] = (char) (array_char[i] + 3);
            MyIO.print(array_char[i]);
        }
        MyIO.println("");
        return;
    }
}
