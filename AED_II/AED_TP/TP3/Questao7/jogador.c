#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

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

typedef struct Fila {
    Celula *primeiro;
    Celula *ultimo;
    int tamanho;
} Fila;

void start(Fila *fila);
void inserir(Jogador jogador, Fila *fila);
Jogador remover(Fila *fila);  
void imprimir(Jogador *jogador);
int lerJogador(FILE* arquivo, Jogador* jogador);

void start(Fila *fila) {
    fila->primeiro = fila->ultimo = NULL;
    fila->tamanho = 0;
}

void inserir(Jogador jogador, Fila *fila) {
    if (fila->tamanho == 5) {
        remover(fila); // Remove o elemento mais antigo se a fila estiver cheia
    }

    Celula *temp = (Celula*) malloc(sizeof(Celula));
    temp->jogador = jogador;
    temp->prox = NULL;

    if (fila->ultimo != NULL) {
        fila->ultimo->prox = temp;
    } else {
        fila->primeiro = temp;
    }

    fila->ultimo = temp;
    fila->tamanho++;
}

Jogador remover(Fila *fila) {
    if (fila->primeiro == NULL) {
        exit(1); // Fila vazia
    }

    Celula *temp = fila->primeiro;
    Jogador removido = temp->jogador;

    fila->primeiro = fila->primeiro->prox;
    if (fila->primeiro == NULL) {
        fila->ultimo = NULL;
    }

    free(temp);
    fila->tamanho--;
    return removido;
}

void imprimir(Jogador *jogador) {
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##]\n", jogador->id, jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
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

int main() {
    Jogador jogadores[3923];
    Fila fila;
    start(&fila);
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

    // Ler os IDs da entrada padrão e inserir na fila
    char entrada[3923];
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
        int idDesejado = atoi(entrada);

        for (int i = 0; i < totalJogadores; i++) {
            if (jogadores[i].id == idDesejado) {
                inserir(jogadores[i], &fila);

                double alturaTotal = 0;
                int cont = 0;
                Celula *temp = fila.primeiro;

                while (temp != NULL) {
                    alturaTotal += temp->jogador.altura;
                    cont++;
                    temp = temp->prox;
                }

                if (cont > 0) {
                    double mediaAltura = round(alturaTotal / cont);
                    printf("%d\n", (int)mediaAltura);
                }

                break;
            }
        }
    }

    char entrada3[250];
    int numRegistros;
    scanf("%d", &numRegistros);

    for (int i = 0; i < numRegistros; i++){
        scanf(" %[^\n]", entrada3);
        if(entrada3[0] == 'I'){
            int numero;
            sscanf(entrada3, "I %d", &numero);
            for (int i = 0; i < totalJogadores; i++) {
                if (jogadores[i].id == numero) {
                    inserir(jogadores[i], &fila);

                    double alturaTotal = 0;
                    int cont = 0;
                    Celula *temp = fila.primeiro;

                    while (temp != NULL) {
                        alturaTotal += temp->jogador.altura;
                        cont++;
                        temp = temp->prox;
                    }

                    if (cont > 0) {
                        double mediaAltura = round(alturaTotal / cont);
                        printf("%d\n", (int)mediaAltura);
                    }

                    break;
                }
            }
        } else if(entrada3[0] == 'R') {
            Jogador removido = remover(&fila);
            printf("(R) %s\n", removido.nome); 
        }
    }

    Celula *atual = fila.primeiro;
    while (atual != NULL) {
        imprimir(&atual->jogador); // Imprime todas as informações do jogador
        atual = atual->prox; // Move para o próximo elemento da fila
    }
   

    return 0;
}