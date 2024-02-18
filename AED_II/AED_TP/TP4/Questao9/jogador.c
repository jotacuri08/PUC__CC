#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <sys/time.h>


#define MAX_TAM 5
#define TAM 25

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

typedef struct Celula {
    struct Celula *prox;
    Jogador jogador;
}Celula;

typedef struct Lista {
    Celula *primeiro;
    Celula *ultimo;
} Lista;

typedef struct Hash {
    int tamanho;
    Lista *tabela[TAM];

} Hash;

Celula *startCelula(Jogador jogador) {
    Celula *celula = (Celula*)malloc(sizeof(Celula));
    celula->jogador = jogador;
    celula->prox = NULL;
    return celula;
}

Lista *startLista() {
    Jogador jogador;
    jogador.id = -1;
    Lista *lista = (Lista*)malloc(sizeof(Lista));
    lista->primeiro = startCelula(jogador);
    lista->ultimo = lista->primeiro;
    return lista;
}

Hash startHash() {
    Hash hash;
    hash.tamanho = TAM;
    for(int i = 0; i < hash.tamanho; i++) {
        hash.tabela[i] = startLista();
    }
    return hash;
}


/*
    Função para ler jogadores de um arquivo CSV e preencher uma lista de jogadores.
    O arquivo deve ter um cabeçalho e os campos separados por vírgulas.
*/
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

bool pesquisarLista(Jogador jogador, Lista *lista) {
    bool resp = false;
    for(Celula* i = lista->primeiro->prox; i != NULL; i = i->prox) {
        comparacoes++;
        if(jogador.id == i->jogador.id) {
            resp = true;
            break;
        }
    }
    return resp;
}

void inserirLista(Jogador jogador, Lista *lista) {
    Celula *tmp = startCelula(jogador);
    lista->ultimo->prox = tmp;
    lista->ultimo = lista->ultimo->prox;
    tmp = NULL;
}


int h(Jogador jogador, Hash hash) {
    return (jogador.altura % hash.tamanho);
}

void inserir(Jogador jogador, Hash *hash) {
    int pos = h(jogador, *hash);
    inserirLista(jogador, hash->tabela[pos]);
}

bool pesquisar(Jogador jogador, Hash *hash) {
    int pos = h(jogador, *hash);
    bool resp = pesquisarLista(jogador, hash->tabela[pos]);
    return resp;
}



Jogador pesquisarJogadorNome(Jogador **jogadores, char *nome, int *numJogadores)
{
    for (int i = 0; i < *numJogadores; i++)
    {
        if (strcmp((*jogadores)[i].nome, nome) == 0)
        {
            return (*jogadores)[i];
        }
    }
    Jogador naoEncontrado;
    naoEncontrado.id = -1;
    return naoEncontrado;
}

Jogador encontrarNome(char *nome, Jogador *jogadores, int numJogadores) {
    for (int i = 0; i < numJogadores; i++) {
        if (strcmp((jogadores)[i].nome, nome) == 0) {
            return (jogadores)[i];
        }
    }

    Jogador naoEncontrado;
    naoEncontrado.id = -1;
    strcpy(naoEncontrado.nome, "Nao Encontrado");
    return naoEncontrado;
}

char* readStr(char* input){
    input[(int)strcspn(input, "\n\r")] = '\0';
    return input;
}



// Main
int main()
{
    struct timeval startTime, endTime;
    Jogador jogadores[3923];
    Hash hash = startHash();
    int n = 0;
    int numJogadores = 0;
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
        readStr(entrada);
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
        inserir(jogador, &hash);
    }

    
    char *entrada2 = (char *)malloc(250 * sizeof(char));
    gettimeofday(&startTime, NULL);
    while (strcmp(entrada2, "FIM") != 0)
    {
        scanf(" %[^\n]", entrada2);
        readStr(entrada2);
        if (strcmp(entrada2, "FIM") != 0)
        {
            printf("%s ", entrada2);
            Jogador jogadorNome = encontrarNome(entrada2, &jogadoresDesejados, numJogadoresDesejados);
            bool resp = pesquisar(jogadorNome, &hash);
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
    FILE *logFile = fopen("729577_hashIndireta.txt", "w");
    if (logFile != NULL) {
        fprintf(logFile, "729577\t%d\t%lf", comparacoes, totalTime);
        fclose(logFile);
    }
    
    return 0;
}
