
public class Questao_12 {
    
    public static boolean not_Fim(String palavra_digitada){
        boolean notFim = false;
        if(palavra_digitada.charAt(0) != 'F' || palavra_digitada.charAt(1) != 'I' || palavra_digitada.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }

    public static void converter_string(String palavra_digitada, char[] palavra){
        for(int i = 0; i < palavra_digitada.length(); i++){
            palavra[i] = palavra_digitada.charAt(i);
        }
    }

    public static void ciframento(char[] palavra, int tamanho, int posicao_atual){
        if(tamanho == posicao_atual){
            return;
        }
        palavra[posicao_atual] = (char) (palavra[posicao_atual] + 3);
        ciframento(palavra, tamanho, posicao_atual + 1);
    }

    public static void main(String[] args){
        String palavra_digitada;
        char[] palavra = new char[1000];
        boolean notFim = true;
        int tamanho;
        do{
            palavra_digitada = MyIO.readLine();
            notFim = not_Fim(palavra_digitada);
            if(!notFim){
                break;
            }
            tamanho = palavra_digitada.length();
            converter_string(palavra_digitada, palavra);
            ciframento(palavra, tamanho, 0);
            for(int i = 0; i < tamanho; i++){
                MyIO.print(palavra[i]);
            }
            MyIO.println("");
        }while(notFim);
        
    }
}
