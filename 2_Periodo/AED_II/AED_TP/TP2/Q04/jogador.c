#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

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


typedef struct Metricas {
    long tempoExecucao;
    long comparacoes;
    long movimentacoes;
} Metricas;


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

void registroDeLog(char* matricula, Metricas* metricas) {
    char nomeArquivo[30];  // Buffer para o nome do arquivo
    sprintf(nomeArquivo, "%s_binaria.txt", matricula);  // Formar o nome do arquivo

    FILE *f = fopen(nomeArquivo, "w");
    if (f == NULL) {
        printf("Erro ao abrir arquivo de log.\n");
        return;
    }
    fprintf(f, "%s\t%ld\t%ld\t%ld\n", matricula, metricas->tempoExecucao, metricas->comparacoes, metricas->movimentacoes);
    fclose(f);
}

int main() {
    Metricas metricas = {0, 0, 0}; // Inicializar a estrutura de métricas com zeros
    clock_t tempoInicial, tempoFinal; // Variáveis para calcular o tempo de execução
    tempoInicial = clock(); // Registrar o tempo de início
    Jogador jogadores[3923];
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

    // Ler os IDs da entrada padrão
    char entrada[1000];  // ID ou "FIM"
    int a = 0;
    int idDesejado[3923];
    while (scanf(" %[^\n]", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
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

    
    // Ordenar os jogadores por nome (ordem alfabética)
     for (int i = 0; i < numJogadoresDesejados - 1; i++) {
        for (int j = i + 1; j < numJogadoresDesejados; j++) {
            metricas.comparacoes++; // Incrementar as comparações
            if (strcmp(jogadoresDesejados[i].nome, jogadoresDesejados[j].nome) > 0) {
                metricas.movimentacoes += 3; // Trocar três atributos (id, nome, altura)
                // Troque os jogadores nas posições 'i' e 'j'
                Jogador temp = jogadoresDesejados[i];
                jogadoresDesejados[i] = jogadoresDesejados[j];
                jogadoresDesejados[j] = temp;
            }
        }
    }


    

    while (scanf(" %[^\n]", entrada) == 1 && strcmp(entrada, "FIM") != 0){
        bool resp = false;
        int dir = numJogadoresDesejados - 1, esq = 0, meio;  
        while (esq <= dir) {
            meio = (esq + dir) / 2;
            int comparacao = strcmp(jogadoresDesejados[meio].nome, entrada);
            if (comparacao == 0){
                resp = true;
                esq = numJogadoresDesejados; // Esta linha não é necessária para encerrar o loop.
                printf("SIM\n");
                break; // Usar "break" é uma maneira mais clara de sair do loop
            } else if (comparacao < 0){
                esq = meio + 1;
            } else {
                dir = meio - 1;
            }

        }
        if(!resp){
           printf("NAO\n");
        }
    }
    
    tempoFinal = clock();
    metricas.tempoExecucao = (tempoFinal - tempoInicial) * 1000 / CLOCKS_PER_SEC; // Converter para milissegundos

    registroDeLog("729577", &metricas); // Grava as métricas no arquivo de log

    return 0;
}