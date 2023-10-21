import java.util.Random;

public class Alteracao_aleatoria {
    public static void main(String[] args){
        String strEntrada;
        boolean notFim = false;
        char[] arraychar = new char[1000];
        int tamanho;
        Random gerador = new Random();
        gerador.setSeed(4);
        
        do{
        strEntrada = MyIO.readLine();
        tamanho = strEntrada.length();
        notFim = not_Fim(strEntrada);
        random(strEntrada, arraychar, gerador);
        printStr(arraychar, tamanho);
        }while(notFim);
    }

    //funcao para verificar se a palavra digitada foi FIM
    public static boolean not_Fim(String strEntrada){
        boolean notFim = false;
        if(strEntrada.charAt(0) != 'F' || strEntrada.charAt(1) != 'I' || strEntrada.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }
    
    //funcao para gerar as letrar aleatorias e substituir a segunda nas ocorrencias da primeira
    public static void random(String strEntrada, char[] arraychar, Random gerador){
        char letra1 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char)('a' + (Math.abs(gerador.nextInt()) % 26));
        for(int i = 0; i < strEntrada.length(); i++){
            arraychar[i] = strEntrada.charAt(i);
        }
        for(int i = 0; i < strEntrada.length(); i++){
            if(arraychar[i] == letra1){
                arraychar[i] = letra2;
            }
        }
        return;
    }

    //funcao para printar a nova string
    public static void printStr(char[] arraychar, int tamanho){
        for(int i = 0; i < tamanho; i++){
            MyIO.print(arraychar[i]);
        }
        MyIO.println("");
        return;
    }
}
