import java.util.Scanner;

class Matriz {
    private int[][] matriz;
    private int linhas;
    private int colunas;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        matriz = new int[linhas][colunas];
    }

    public void setElemento(int linha, int coluna, int valor) {
        if (linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas) {
            matriz[linha][coluna] = valor;
        }
    }
    

    public void preencherMatriz(int[][] dados) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = dados[i][j];
            }
        }
    }

    public Matriz soma(Matriz outra) {
        // Verificar se as dimensões são compatíveis
        if (this.linhas != outra.linhas || this.colunas != outra.colunas) {
            System.out.println("Matrizes de dimensões diferentes!");
            return null;
        }

        Matriz resultante = new Matriz(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultante.matriz[i][j] = this.matriz[i][j] + outra.matriz[i][j];
            }
        }
        return resultante;
    }

    public Matriz multiplicacao(Matriz outra) {
        // Verificar se as dimensões são compatíveis
        if (this.colunas != outra.linhas) {
            System.out.println("Matrizes de dimensões incompatíveis para multiplicação!");
            return null;
        }

        Matriz resultante = new Matriz(this.linhas, outra.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < outra.colunas; j++) {
                for (int k = 0; k < this.colunas; k++) {
                    resultante.matriz[i][j] += this.matriz[i][k] * outra.matriz[k][j];
                }
            }
        }
        return resultante;
    }

    public void mostrarDiagonalPrincipal() {
        for (int i = 0; i < linhas; i++) {
            System.out.print(matriz[i][i] + " ");
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        for (int i = 0; i < linhas; i++) {
            System.out.print(matriz[i][colunas - i - 1] + " ");
        }
        System.out.println();
    }

    public void mostrar() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }



        public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCasos = scanner.nextInt();

        for (int i = 0; i < numCasos; i++) {
            // Leitura da primeira matriz
            int linhas1 = scanner.nextInt();
            int colunas1 = scanner.nextInt();
            Matriz matriz1 = new Matriz(linhas1, colunas1);
            for (int j = 0; j < linhas1; j++) {
                for (int k = 0; k < colunas1; k++) {
                    matriz1.setElemento(j, k, scanner.nextInt());
                }
            }

            // Leitura da segunda matriz
            int linhas2 = scanner.nextInt();
            int colunas2 = scanner.nextInt();
            Matriz matriz2 = new Matriz(linhas2, colunas2);
            for (int j = 0; j < linhas2; j++) {
                for (int k = 0; k < colunas2; k++) {
                    matriz2.setElemento(j, k, scanner.nextInt());
                }
            }

            // Processamento e saída
            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            Matriz soma = matriz1.soma(matriz2);
            if (soma != null) {
                soma.mostrar();
            }

            Matriz multiplicacao = matriz1.multiplicacao(matriz2);
            if (multiplicacao != null) {
                multiplicacao.mostrar();
            }
        }

        scanner.close();
    }
}