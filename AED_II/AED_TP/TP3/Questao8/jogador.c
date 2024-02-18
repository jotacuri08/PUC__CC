#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>

int delimitadorFila = 0;

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

typedef struct CelulaDupla
{
    Jogador jogador;
    struct CelulaDupla *prox;
    struct CelulaDupla *ant;
} CelulaDupla;

typedef struct
{
    CelulaDupla *primeiro;
    CelulaDupla *ultimo;
} Lista;


CelulaDupla *startCelula(Jogador jogador)
{
    CelulaDupla *celula = (CelulaDupla *)malloc(sizeof(CelulaDupla));
    celula->jogador = jogador;
    celula->prox = NULL;
    celula->ant = NULL;
    return celula;
}

Lista *startLista()
{
    Jogador jogador;
    jogador.id = -1;
    Lista *lista = (Lista*)malloc(sizeof(Lista));
    lista->primeiro = startCelula(jogador);
    lista->ultimo = lista->primeiro;
    return lista;
}

// Função para imprimir um jogador
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

void inserirInicio(Lista *lista, Jogador jogador) {
    CelulaDupla *tmp = startCelula(jogador);
    tmp->ant = lista->primeiro;
    tmp->prox = lista->primeiro->prox;
    lista->primeiro->prox = tmp;
    if(lista->primeiro == lista->ultimo) {
        lista->ultimo = tmp;
    }
    else {
        tmp->prox->ant = tmp;
    }
    tmp = NULL;
}

void inserirFinal(Lista *lista, Jogador jogador) {
    lista->ultimo->prox = startCelula(jogador);
    lista->ultimo->prox->ant = lista->ultimo;
    lista->ultimo = lista->ultimo->prox;
}

int tamanho(Lista *lista) {
    int tamanho = 0;
    for(CelulaDupla *i = lista->primeiro; i != lista->ultimo;i = i->prox, tamanho++);
    return tamanho;
}

void inserir(Lista *lista, Jogador jogador, int pos){
    int n = tamanho(lista);
    if(pos < 0 || pos > n) {
        fprintf(stderr, "Erro ao inserir na posição desejada.");
        exit(1);
    }
    else if(pos == 0) {
        inserirInicio(lista, jogador);
    }
    else if(pos == n) {
        inserirFinal(lista, jogador);
    }
    else {
        CelulaDupla *i = lista->primeiro;
        for(int j = 0; j < pos; j++, i = i->prox);
        CelulaDupla *tmp = startCelula(jogador);
        tmp->ant = i;
        tmp->prox = i->prox;
        tmp->ant->prox = tmp;
        tmp->prox->ant = tmp;
        tmp = i = NULL;
    }

}

Jogador removerInicio(Lista *lista) {
    if(lista->primeiro == lista->ultimo) {
        fprintf(stderr, "Erro: Lista Vazia (removerInicio)");
        exit(1);
    }
    CelulaDupla *tmp = lista->primeiro;
    lista->primeiro = lista->primeiro->prox;
    Jogador jogadorRemovido = lista->primeiro->jogador;
    tmp->prox = lista->primeiro->ant = NULL;
    tmp = NULL;
    return jogadorRemovido;
}

Jogador removerFinal(Lista *lista) {
    if(lista->primeiro == lista->ultimo) {
        fprintf(stderr, "Erro: Lista vazia (removerFinal)");
        exit(1);
    }
    Jogador jogadorRemovido = lista->ultimo->jogador;
    lista->ultimo = lista->ultimo->ant;
    lista->ultimo->prox = lista->ultimo->prox->ant = NULL;
    return jogadorRemovido;
}

Jogador remover(Lista *lista, Jogador jogador, int pos) {
    int n = tamanho(lista);
    Jogador resp;
    if(lista->ultimo == lista->primeiro) {
        fprintf(stderr, "Erro: lista vazia(remover)");
        exit(1);
    }
    else if(pos < 0 || pos >= n) {
        fprintf(stderr, "Erro para remover da posicao desejada");
        exit(1);
    }
    else if(pos == 0) {
        resp = removerInicio(lista);
    }
    else if(pos == n - 1) {
        resp = removerFinal(lista);
    }
    else {
        CelulaDupla *i = lista->primeiro->prox;
        for(int j = 0; j < pos; j++, i = i->prox);
        i->ant->prox = i->prox;
        i->prox->ant = i->ant;
        resp = i->jogador;
        i->prox = i->ant = NULL;
        i = NULL;
    }
    return resp;
}

void mostrar(Lista *lista) {
    for(CelulaDupla *i = lista->primeiro->prox; i != NULL; i = i->prox) {
        printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s]\n", i->jogador.id, i->jogador.nome, i->jogador.altura, i->jogador.peso, i->jogador.anoNascimento, 
        i->jogador.universidade, i->jogador.cidadeNascimento, i->jogador.estadoNascimento);
    }
}

CelulaDupla *encontrarPivo(Lista *lista, CelulaDupla *esq, CelulaDupla *dir) {
    CelulaDupla *ptrEsq = esq, *ptrDir = dir;
    while(ptrEsq != ptrDir && ptrEsq->prox != ptrDir) {
        ptrEsq = ptrEsq->prox;
        ptrDir = ptrDir->ant;
    }
    CelulaDupla *pivo = ptrEsq;

    return pivo;
}

void quicksort(Lista*lista, CelulaDupla *esq, CelulaDupla *dir, int *comparacoes, int *movimentacoes) {
    CelulaDupla *i = esq, *j = dir;
    CelulaDupla *Celulapivo = encontrarPivo(lista, i, j);
    Jogador pivo = Celulapivo->jogador;
    while(i != j->prox && j->prox != i->ant) {
        while(((*comparacoes)++, strcmp(i->jogador.estadoNascimento, pivo.estadoNascimento) < 0) ||
        ((*comparacoes)++, strcmp(i->jogador.estadoNascimento, pivo.estadoNascimento) == 0 && strcmp(i->jogador.nome, pivo.nome) < 0)) {
            i = i->prox;
        }
        while(((*comparacoes)++, strcmp(j->jogador.estadoNascimento, pivo.estadoNascimento) > 0) ||
        ((*comparacoes)++, strcmp(j->jogador.estadoNascimento, pivo.estadoNascimento) == 0 && strcmp(j->jogador.nome, pivo.nome) > 0)) {
            j = j->ant;
        }
        if(i != j->prox) {
            (*movimentacoes) += 2;
            Jogador tmp = i->jogador;
            i->jogador = j->jogador;
            j->jogador = tmp;
            i = i->prox;
            j = j->ant;
        }
    }
    if(esq != j->prox && esq != j) {
        quicksort(lista, esq, j, comparacoes, movimentacoes);
    }
    if(i != dir->prox && i != dir) {
        quicksort(lista, i, dir, comparacoes, movimentacoes);
    }
    
}


int main() {
    
    Jogador jogadores[3923];
    Lista *lista = startLista();
    struct timeval startTime, endTime;
    int totalJogadores = 0;
    int n = 0;
    int numJogadores = 0;
    int contadorRemocoes = 0;
    int comparacoes = 0;
    int movimentacoes = 0;
    FILE* arquivo;
    arquivo = fopen("/tmp/players.csv", "r");
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo.\n");
        return 1;
    }

    // Pular o cabeçalho e carregar todos os jogadores do arquivo
    char linha[1024];
    fgets(linha, sizeof(linha), arquivo);

    while (lerJogador(arquivo, &jogadores[totalJogadores]) != EOF && totalJogadores < 3923) {
        totalJogadores++;
    }
    fclose(arquivo);

    // Ler os IDs da entrada padrão e imprimir os jogadores correspondentes
    char *entrada = (char*) malloc(250 * sizeof(char));  // ID ou "FIM"
    int a = 0;
    int idDesejado[3923];
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
        idDesejado[a] = atoi(entrada);  // Converte string para int
        a++;
    }

    int numJogadoresDesejados = 0; // Número de jogadores desejados encontrados

    // Iterar sobre os IDs desejados
    for (int i = 0; i < a; i++) {
        int idProcurado = idDesejado[i];

    // Iterar sobre os jogadores originais
        for (int j = 0; j < totalJogadores; j++) {
            if (jogadores[j].id == idProcurado) {
                // O ID do jogador corresponde a um ID desejado, copie o jogador
                Jogador jogador = jogadores[j];
                inserirFinal(lista, jogador);
                numJogadoresDesejados++;
                break; // Você pode sair do loop interno assim que encontrar um jogador correspondente
            }
        }
    }

    gettimeofday(&startTime, NULL);
    
    quicksort(lista, lista->primeiro->prox, lista->ultimo, &comparacoes, &movimentacoes);
    
    gettimeofday(&endTime, NULL);

    mostrar(lista);

    double totalTime = (endTime.tv_sec - startTime.tv_sec) + ((endTime.tv_usec - startTime.tv_usec) / 1000000.0);
    FILE *logFile = fopen("729577_quicksort2.txt", "w");
    if (logFile != NULL) {
        fprintf(logFile, "729577\t%d\t%d\t%lf", comparacoes, movimentacoes, totalTime);
        fclose(logFile);
    }

    free(entrada);
    return 0;
}