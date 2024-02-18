#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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

typedef struct Celula {
    Jogador jogador;
    struct Celula *prox;
} Celula;

typedef struct Pilha {
    Celula *topo;
} Pilha;

void start(Pilha *pilha) {
    pilha->topo = NULL;
}

void inserir(Jogador jogador, Pilha *pilha) {
    Celula *temp = (Celula*) malloc(sizeof(Celula));
    temp->jogador = jogador;
    temp->prox = pilha->topo;
    pilha->topo = temp;
}

Jogador remover(Pilha *pilha) {
    if (pilha->topo == NULL) {
        printf("Pilha vazia.\n");
        exit(1);
    }
    
    Celula *temp = pilha->topo;
    Jogador removido = temp->jogador;
    pilha->topo = pilha->topo->prox;
    free(temp);
    return removido;
}

void imprimir(Jogador *jogador) {
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n", jogador->id, jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
}

void imprimirInvertido(Pilha *pilha) {
    Pilha aux;
    start(&aux);
    int posicao = 0;

    // Desempilhar elementos da pilha original e empilhar na auxiliar
    while (pilha->topo != NULL) {
        Jogador removido = remover(pilha);
        inserir(removido, &aux);
    }

    // Imprimir e reempilhar na pilha original
    while (aux.topo != NULL) {
        Jogador jogador = remover(&aux);
        printf("[%d] ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n", posicao, jogador.nome, jogador.altura, jogador.peso, jogador.anoNascimento, jogador.universidade, jogador.cidadeNascimento, jogador.estadoNascimento);
        inserir(jogador, pilha);
        posicao++;
    }
}


void verificaVazio(char* campo) {
    if (strcmp(campo, " ") == 0 || strcmp(campo, "") == 0) {
        strcpy(campo, "nao informado");
    }
}

char *scan(char **pp, char c) {
    char *s = *pp, *p;
    p = strchr(*pp, c);
    if (p) *p++ = '\0';
    *pp = p;
    return s;
}

int lerJogador(FILE* arquivo, Jogador* jogador) {
    char linha[1024];
    char *v[8], *pointLin;
    int i;
    if (fgets(linha, sizeof(linha), arquivo) == NULL) {
        return EOF; // Fim do arquivo
    }

    pointLin = linha;
    for (i = 0; i < 8; i++) {
        v[i] = scan(&pointLin, ',');
    }

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

int main() {
    Jogador jogadores[3923];
    Pilha pilha;
    start(&pilha);
    FILE* arquivo;

    arquivo = fopen("/tmp/players.csv", "r");
    if (arquivo == NULL) {
        return 1;
    }

    char linha[1024];
    fgets(linha, sizeof(linha), arquivo);

    int totalJogadores = 0;
    while (lerJogador(arquivo, &jogadores[totalJogadores]) != EOF && totalJogadores < 3923) {
        totalJogadores++;
    }
    fclose(arquivo);

    char entrada[3923];
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
        int idDesejado = atoi(entrada);
        for (int i = 0; i < totalJogadores; i++) {
            if (jogadores[i].id == idDesejado) {
                inserir(jogadores[i], &pilha);
                break;
            }
        }
    }

    char entrada3[250];
    int numRegistros;
    scanf("%d", &numRegistros);

    for (int i = 0; i < numRegistros; i++) {
        scanf(" %[^\n]", entrada3);
        if (entrada3[0] == 'I') {
            int numero;
            sscanf(entrada3, "I %d", &numero);
            for (int j = 0; j < totalJogadores; j++) {
                if (jogadores[j].id == numero) {
                    inserir(jogadores[j], &pilha);
                    break;
                }
            }
        } else if (entrada3[0] == 'R') {
            Jogador removido = remover(&pilha);
            printf("(R) %s\n", removido.nome);
        }
    }

    imprimirInvertido(&pilha);

    return 0;
}