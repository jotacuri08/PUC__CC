import mypackage.MyIO;
public class Algebra_booleana {
    public static void main(String[] args) {
        boolean continuarExecucao;
        int quantidadeValores;
        String entradaExpressao;
        char[] expressaoCharArray = new char[1000];

        do {
            quantidadeValores = MyIO.readInt();
            continuarExecucao = verificarContinuacao(quantidadeValores);
            if (continuarExecucao) {
                String[] valoresBinarios = lerValoresBinarios(quantidadeValores);
                entradaExpressao = MyIO.readLine();
                int tamanhoExpressao = entradaExpressao.length();
                tamanhoExpressao = removerEspacosEVirgulas(entradaExpressao, expressaoCharArray, tamanhoExpressao);
                substituirValoresBinarios(expressaoCharArray, valoresBinarios, tamanhoExpressao);
                char resultadoExpressao = resolverExpressaoBooleana(expressaoCharArray, tamanhoExpressao);
                MyIO.println(resultadoExpressao);
            }
        } while (continuarExecucao);
    }

    public static void converterStringParaCharArray(String entradaExpressao, char[] expressaoCharArray) {
        for (int i = 0; i < entradaExpressao.length(); i++) {
            expressaoCharArray[i] = entradaExpressao.charAt(i);
        }
    }

    public static int removerEspacosEVirgulas(String entradaExpressao, char[] expressaoCharArray, int tamanho) {
        int novoTamanho = 0;
        for (int i = 0; i < tamanho; i++) {
            char caractere = entradaExpressao.charAt(i);
            if (caractere != ' ' && caractere != ',') {
                expressaoCharArray[novoTamanho] = caractere;
                novoTamanho++;
            }
        }
        return novoTamanho;
    }

    public static void substituirValoresBinarios(char[] expressaoCharArray, String[] valoresBinarios, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            char caractere = expressaoCharArray[i];
            if (caractere >= 'A' && caractere <= 'Z') {
                int indice = caractere - 'A';
                if (indice < valoresBinarios.length) {
                    expressaoCharArray[i] = valoresBinarios[indice].charAt(0);
                }
            }
        }
    }

    public static String[] lerValoresBinarios(int quantidadeValores) {
        String[] valoresBinarios = new String[quantidadeValores];
        for (int i = 0; i < quantidadeValores; i++) {
            valoresBinarios[i] = MyIO.readString();
        }
        return valoresBinarios;
    }

    public static char operacaoNOT(char[] valores, int quantidadeValores) {
        char resultado = '1';
        for (int i = 0; i < quantidadeValores; i++) {
            if (valores[i] == '1') {
                resultado = '0';
                break;
            }
        }
        return resultado;
    }

    public static char operacaoAND(char[] valores, int quantidadeValores) {
        char resultado = '1';
        for (int i = 0; i < quantidadeValores; i++) {
            if (valores[i] == '0') {
                resultado = '0';
                break;
            }
        }
        return resultado;
    }

    public static char operacaoOR(char[] valores, int quantidadeValores) {
        char resultado = '0';
        for (int i = 0; i < quantidadeValores; i++) {
            if (valores[i] == '1') {
                resultado = '1';
                break;
            }
        }
        return resultado;
    }

    public static char resolverExpressaoBooleana(char[] expressaoCharArray, int tamanho) {
        for (int i = 0; i < tamanho; i++) {
            if (expressaoCharArray[i] == ')') {
                int j = i;
                while (j >= 0) {
                    if (expressaoCharArray[j] == '(') {
                        char operador = expressaoCharArray[j - 1];
                        int inicio = j + 1;
                        int fim = i - 1;
                        char[] valores = new char[fim - inicio + 1];
                        int indice = 0;
                        for (int k = inicio; k <= fim; k++) {
                            valores[indice] = expressaoCharArray[k];
                            indice++;
                        }
                        char resultado;
                        if (operador == 'N') {
                            resultado = operacaoNOT(valores, indice);
                        } else if (operador == 'A') {
                            resultado = operacaoAND(valores, indice);
                        } else if (operador == 'O') {
                            resultado = operacaoOR(valores, indice);
                        } else {
                            resultado = ' ';
                        }
                        expressaoCharArray[j - 1] = resultado;
                        for (int k = 0; k < indice + 3; k++) {
                            for (int w = j - 2; w < tamanho - 1; w++) {
                                expressaoCharArray[w] = expressaoCharArray[w + 1];
                            }
                            tamanho--;
                        }
                        i = 0;
                        break;
                    }
                    j--;
                }
            }
        }
        return expressaoCharArray[0];
    }

    public static boolean verificarContinuacao(int quantidadeValores) {
        return quantidadeValores != 0;
    }
}
