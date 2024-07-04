#include <stdio.h>
#include <stdlib.h>

int main() {
    int quant_num;
    double num_digitado;
    scanf("%d", &quant_num);
    FILE *arquivo;
    arquivo = fopen("arq.txt", "wb"); // "wb" para abrir o arquivo em modo binário de escrita
    for (int i = 0; i < quant_num; i++) {
        scanf("%lf", &num_digitado);
        fwrite(&num_digitado, sizeof(double), 1, arquivo); // Escreve o número no arquivo em binário
    }
    fclose(arquivo);

    arquivo = fopen("arq.txt", "rb"); // "rb" para abrir o arquivo em modo binário de leitura
    fseek(arquivo, 0, SEEK_END); // Move o cursor para o final do arquivo
    long posicao_atual = ftell(arquivo); // Obtém a posição atual (tamanho do arquivo em bytes)
    for (long i = posicao_atual - sizeof(double); i >= 0; i -= sizeof(double)) {
        fseek(arquivo, i, SEEK_SET);
        fread(&num_digitado, sizeof(double), 1, arquivo); // Lê o número do arquivo em binário
        printf("%g\n", num_digitado);
    }
    fclose(arquivo);

    return 0;
}
