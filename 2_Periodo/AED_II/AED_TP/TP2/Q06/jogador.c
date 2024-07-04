#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

typedef struct Jogador {
    int id;
    char nome[250];
    int altura;
    int peso;
    char universidade[250];
    int anoNascimento;
    char cidadeNascimento[250];
    char estadoNascimento[250];
} Jogador;

void imprimir(Jogador* jogador) {
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", jogador->id, jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
}

void verificaVazio(char* campo) {
    if (strcmp(campo, " ") == 0 || strcmp(campo, "") == 0) {
        strcpy(campo, "nao informado");
    }
}

char *scan(char **pp, char c)
{
    char *s = *pp, *p;
    p = strchr(*pp, c);
    if (p) *p++ = '\0';
    *pp = p;
    return s;
}

int lerJogador(FILE* arquivo, Jogador* jogador) {
    char linha[1024];
    char *v[8],*pointLin;
    int i;
    if (fgets(linha, sizeof(linha), arquivo) == NULL) {
        return EOF; // Fim do arquivo
    }

    // Dividir a linha em campos
    pointLin = linha;
    for (i = 0; i < 8; i++) v[i] = scan(&pointLin, ',');
    
    jogador->id = atoi(v[0]);
    strcpy(jogador->nome, v[1]);
    jogador->altura = atoi(v[2]);
    jogador->peso = atoi(v[3]);
    strcpy(jogador->universidade, v[4]);
    jogador->anoNascimento = atoi(v[5]);
    strcpy(jogador->cidadeNascimento, v[6]);
    strcpy(jogador->estadoNascimento, v[7]);
    jogador->estadoNascimento[strlen(jogador->estadoNascimento)-1]='\0';

    verificaVazio(jogador->nome);
    verificaVazio(jogador->universidade);
    verificaVazio(jogador->cidadeNascimento);
    verificaVazio(jogador->estadoNascimento);

    return 8;
}

void swap(Jogador *a, Jogador *b) {
    Jogador temp = *a;
    *a = *b;
    *b = temp;
}

void selecaoRec(int numJogadoresDesejados, Jogador *jogadoresDesejados, int cont1) {
    if (cont1 < numJogadoresDesejados - 1) {
        int minIndex = cont1;
        // Encontra o índice do jogador com o menor nome entre cont1 e numJogadoresDesejados
        for (int j = (cont1 + 1); j < numJogadoresDesejados; j++) {
            char *nome1 = jogadoresDesejados[j].nome;
            char *nome2 = jogadoresDesejados[minIndex].nome;
            int comparacaoNome = strcmp(nome1, nome2);
            if (comparacaoNome < 0) {
                minIndex = j;
            }
        }
        // Troca os jogadores nas posições 'cont1' e 'minIndex'
        swap(&jogadoresDesejados[cont1], &jogadoresDesejados[minIndex]);
        // Chamada recursiva para ordenar o restante do vetor
        selecaoRec(numJogadoresDesejados, jogadoresDesejados, ++cont1);
    }
}



int main() {
    Jogador jogadores[3923];
    int cont1 = 0;

    FILE* arquivo;
    arquivo = fopen("/tmp/players.csv", "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return 1;
    }

    // Pular o cabeçalho e carregar todos os jogadores do arquivo
    char linha[1024];
    fgets(linha, sizeof(linha), arquivo);

    int totalJogadores = 0;
    while (lerJogador(arquivo, &jogadores[totalJogadores]) != EOF && totalJogadores < 3923) {
        totalJogadores++;
    }
    fclose(arquivo);

    
    // Ler os IDs da entrada padrão e imprimir os jogadores correspondentes
    char entrada[250];  // ID ou "FIM"
    int a = 0;
    int idDesejado[3923];
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
        idDesejado[a] = atoi(entrada);  // Converte string para int
        a++;
    }

    Jogador jogadoresDesejados[3923]; // Novo vetor para armazenar os jogadores desejados
    int numJogadoresDesejados = 0; // Número de jogadores desejados encontrados

    // Iterar sobre os IDs desejados
    for (int i = 0; i < a; i++) {
        int idProcurado = idDesejado[i];

    // Iterar sobre os jogadores originais
        for (int j = 0; j < totalJogadores; j++) {
            if (jogadores[j].id == idProcurado) {
                // O ID do jogador corresponde a um ID desejado, copie o jogador
                jogadoresDesejados[numJogadoresDesejados] = jogadores[j];
                numJogadoresDesejados++;
                break; // Você pode sair do loop interno assim que encontrar um jogador correspondente
            }
        }
    }

    
    selecaoRec(numJogadoresDesejados, jogadoresDesejados, cont1);

    for(int i = 0; i < numJogadoresDesejados; i++){
        imprimir(&jogadoresDesejados[i]);
    }
   
    return 0;
}