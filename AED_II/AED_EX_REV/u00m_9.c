#include <stdio.h>

int main() {
    FILE *arquivo = fopen("exemplo.txt", "w+"); // Abre o arquivo para leitura e escrita

    if (arquivo == NULL) {
        printf("Não foi possível abrir o arquivo.\n");
        return 1;
    }

    // Escreve algumas informações no arquivo
    fprintf(arquivo, "Hello, World!\n");
    fprintf(arquivo, "Esta é uma linha de exemplo.\n");

    // Indica a posição atual de escrita no arquivo
    long posicaoEscrita = ftell(arquivo);
    printf("Posição atual de escrita: %ld\n", posicaoEscrita);

    // Lê algumas informações do arquivo
    char buffer[100];
    fgets(buffer, sizeof(buffer), arquivo);

    // Indica a posição atual de leitura no arquivo
    long posicaoLeitura = ftell(arquivo);
    printf("Posição atual de leitura: %ld\n", posicaoLeitura);

    fclose(arquivo); // Fecha o arquivo

    return 0;
}
