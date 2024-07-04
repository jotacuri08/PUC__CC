#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>



#define MAX_TAM 5

int comparacoes = 0;

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

typedef struct No
{
    Jogador jogador;
    struct No *esq;
    struct No *dir;
    int nivel;
} No;

typedef struct
{
    No *raiz;
} Avl;

No *startNo(Jogador jogador)
{
    No *no = (No*)malloc(sizeof(No));
    no->jogador = jogador;
    no->dir = NULL;
    no->esq = NULL;
    no->nivel = 1;
    return no;
}

int getNivel(No *i) {
    if(i == NULL) {
        return 0;
    }
    else {
        return i->nivel;
    }
}

void setNivel(No *no) {
    int noEsq = getNivel(no->esq);
    int noDir = getNivel(no->dir);

    if(noEsq > noDir) {
        no->nivel = 1 + noEsq;
    }
    else {
        no->nivel = 1 + noDir;
    }
}

Avl *startAvl()
{
    Avl *avl = (Avl*)malloc(sizeof(Avl));
    avl->raiz = NULL;
    return avl;
}

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

No *rotacionarDir(No *i) {
    No *noEsq = i->esq;
    No *noEsqDir = noEsq->dir;
    
    noEsq->dir = i;
    i->esq = noEsqDir;
    setNivel(i);
    setNivel(noEsq);

    return noEsq;
}

No *rotacionarEsq(No *i) {
    No *noDir = i->dir;
    No *noDirEsq = noDir->esq;

    noDir->esq = i;
    i->dir = noDirEsq;
    setNivel(i);
    setNivel(noDir);

    return noDir;
}

No *rotacionarDirEsq(No *i) {
    i->dir = rotacionarDir(i->dir);
    return rotacionarEsq(i);
}

No *rotacionarEsqDir(No *i) {
    i->esq = rotacionarEsq(i->esq);
    return rotacionarDir(i);
}

No *balancear(No *i) {
    if(i != NULL) {
        int fator = getNivel(i->dir) - getNivel(i->esq);

        if(fator == 1 || fator == 0 || fator == -1) {
            setNivel(i);
        }
        else if(fator == 2) {
            int fatorFilhoDir = getNivel(i->dir->dir) - getNivel(i->dir->esq);
            if(fatorFilhoDir == -1) {
                i = rotacionarDirEsq(i);
            }
            else {
                i = rotacionarEsq(i);
            }
        }
        else if(fator == -2) {
            int fatorFilhoEsq = getNivel(i->esq->dir) - getNivel(i->esq->esq);

            if(fatorFilhoEsq == 1) {
                i = rotacionarEsqDir(i);
            }
            else {
                i = rotacionarDir(i);
            }
        }
        else {
            fprintf(stderr, "Fator de balanceamento invalido");
            exit(1);
        }

        return i;
    }
}

No *inserirRec(Jogador jogador, No *i) {
    if(i == NULL) {
        i = startNo(jogador);
    }
    else if(strcmp(jogador.nome,i->jogador.nome) < 0) {
        i->esq = inserirRec(jogador, i->esq);
    }
    else if(strcmp(jogador.nome, i->jogador.nome) > 0) {
        i->dir = inserirRec(jogador, i->dir);
    }
    else {
        fprintf(stderr, "Jogador repetido");
        exit(1);
    }

    return balancear(i);
}

void inserir(Avl *arvore, Jogador jogador) {
    arvore->raiz = inserirRec(jogador, arvore->raiz);
}


bool pesquisarRec(No *i, char *nome) {
    bool resp;
    if(i == NULL) {
        resp = false;
    }
    else {
        comparacoes++;
        if(strcmp(nome, i->jogador.nome) == 0) {
            resp = true;
        }
        else {
            comparacoes++;
            if(strcmp(nome, i->jogador.nome) < 0) {
                printf("esq ");
                resp = pesquisarRec(i->esq, nome);
            }
        
            else {
                printf("dir ");
                resp = pesquisarRec(i->dir, nome);
            }
        }
    }

    return resp;
}

bool pesquisar(Avl *arvore, char* nome) {
    if(arvore->raiz != NULL) {
        printf("raiz ");
    }
    return pesquisarRec(arvore->raiz, nome);
}


// Main
int main()
{
    struct timeval startTime, endTime;
    Jogador jogadores[3923];
    Avl *arvore = startAvl();
    int n = 0;
    int numJogadores = 0;
    int contadorRemocoes = 0;
    FILE* arquivo;
    arquivo = fopen("/tmp/players.csv", "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return 1;
    }
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

    for(int i = 0; i < numJogadoresDesejados; i++){
        Jogador jogador = jogadoresDesejados[i];
        inserir(arvore, jogador);
    }

    char *entrada2 = (char *)malloc(250 * sizeof(char));
    gettimeofday(&startTime, NULL);
    while (strcmp(entrada2, "FIM") != 0)
    {
        scanf(" %[^\n]", entrada2);
        if (strcmp(entrada2, "FIM") != 0)
        {
            printf("%s ", entrada2);
            bool resp = pesquisar(arvore, entrada2);
            if(resp) {
                printf("SIM\n");
            }
            else {
                printf("NAO\n");
            }
        }
    }
    gettimeofday(&endTime, NULL);

    double totalTime = (endTime.tv_sec - startTime.tv_sec) + ((endTime.tv_usec - startTime.tv_usec) / 1000000.0);
    FILE *logFile = fopen("729577_avl.txt", "w");
    if (logFile != NULL) {
        fprintf(logFile, "729577\t%d\t%lf", comparacoes, totalTime);
        fclose(logFile);
    }
    

    return 0;
}
