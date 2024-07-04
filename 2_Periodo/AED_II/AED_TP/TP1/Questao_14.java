import java.util.Random;

public class Questao_14 {
    public static boolean not_Fim(String strEntrada){
        boolean notFim = false;
        if(strEntrada.charAt(0) != 'F' || strEntrada.charAt(1) != 'I' || strEntrada.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }

    public static void random(Random gerador, char[] letras) {
        for (int i = 0; i < letras.length; i++) {
            letras[i] = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        }
    }

    public static void substituir(char[] arraychar, int posicao_atual, char letra1, char letra2) {
        if (posicao_atual < arraychar.length) {
            if (arraychar[posicao_atual] == letra1) {
                arraychar[posicao_atual] = letra2;
            }
            substituir(arraychar, posicao_atual + 1, letra1, letra2);
        }
    }

        public static void printStr(char[] arraychar, int tamanho) {
            for (int i = 0; i < tamanho; i++) {
                MyIO.print(arraychar[i]);
            }
            MyIO.println("");
        }

        public static void converter_string(String strEntrada, char[] arraychar) {
            for (int i = 0; i < strEntrada.length(); i++) {
                arraychar[i] = strEntrada.charAt(i);
            }
        }


    public static void main(String[] args) {
        String strEntrada;
        boolean notFim = false;
        char[] arraychar = new char[1000];
        char[] letras = new char[2]; // Array para armazenar as letras aleatórias
        int tamanho;
        Random gerador = new Random();
        gerador.setSeed(4);

        do {
            strEntrada = MyIO.readLine();
            converter_string(strEntrada, arraychar);
            tamanho = strEntrada.length();
            notFim = not_Fim(strEntrada);
            random(gerador, letras); // Gere as letras aleatórias
            substituir(arraychar, 0, letras[0], letras[1]); // Substitua as letras no arraychar
            printStr(arraychar, tamanho);
        } while (notFim);
    }
    }
