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

typedef struct Lista{
    Jogador array[1000];
    int n;
}Lista;

void start(Lista* lista){
    lista -> n = 0; 
}

void inserirFim(Jogador jogador, Lista* lista){
    if(lista -> n >= 1000){
        exit(1);
    }

    lista -> array[lista -> n] = jogador;
    lista -> n++;
}

void inserirInicio(Jogador jogador, Lista* lista){
    if(lista -> n >= 1000){
        exit(1);
    }

    for (int i = lista -> n; i > 0; i--){
        lista -> array[i] = lista -> array[i-1];
    }

    lista -> array[0] = jogador;
    lista -> n++;
}

void inserir(Jogador jogador, Lista* lista, int pos){
    if(lista -> n >= 1000 || pos < 0 || pos > lista -> n){
        exit(1);
    }

    for(int i = lista -> n; i > pos; i--){
        lista -> array[i] = lista -> array[i-1];
    }

    lista -> array[pos] = jogador;
    lista -> n++;
}

Jogador removerFim(Lista* lista){
    if(lista -> n <= 0){
        exit(1);
    }

    Jogador removido = lista -> array[lista -> n];
    printf("(R) %s\n", removido.nome);
    lista -> n--;
    return removido;
}

Jogador removerInicio(Lista* lista){
    if(lista -> n <= 0){
        exit(1);
    }

    Jogador removido = lista -> array[0];
    
    printf("(R) %s\n", removido.nome);

    for(int i = 0; i < lista -> n; i++){
        lista -> array[i] = lista -> array[i+1];
    }

    lista -> n--;
    return removido;
}

Jogador remover(Lista* lista, int pos){
    if(lista -> n <= 0 || pos < 0 || pos > lista -> n){
        exit(1);
    }

    Jogador removido = lista -> array[pos];

    printf("(R) %s\n", removido.nome);

    for(int i = pos; i < lista -> n; i++){
        lista -> array[i] = lista -> array[i+1];
    }

    lista -> n--;
    return removido;
}

void imprimir(Jogador* jogador, int index) {
    printf("[%d] ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n", index , jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
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
    Lista lista;
    start(&lista);
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

    // Ler os IDs da entrada padrão e inserir na lista
    char entrada[3923];  // ID ou "FIM"
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
        int idDesejado = atoi(entrada);  // Converte string para int

        // Procura o jogador com o ID desejado e insere na lista
        for (int i = 0; i < totalJogadores; i++) {
            if (jogadores[i].id == idDesejado) {
                inserirFim(jogadores[i], &lista);
            }
        }
    }

    
    char entrada2[4];
    scanf("%s", entrada2);

    int numRegistros = atoi(entrada2);
    char entrada3[250];

    for (int i = 0; i < numRegistros; i++){
        scanf(" %[^\n]", entrada3);
        if(entrada3[0] == 'I'){
            if(entrada3[1] == 'I'){
                int numero;
                sscanf(entrada3, "II %d", &numero);
                for (int i = 0; i < totalJogadores; i++) {
                    if (jogadores[i].id == numero) {
                        inserirInicio(jogadores[i], &lista);
                    }
                }
            }else if(entrada3[1] == 'F'){
                int numero;
                sscanf(entrada3, "IF %d", &numero);
                for (int i = 0; i < totalJogadores; i++) {
                    if (jogadores[i].id == numero) {
                        inserirFim(jogadores[i], &lista);
                    }
                }
            }else if(entrada3[1] == '*'){
                int numero, pos;
                sscanf(entrada3, "I* %d %d", &pos, &numero);
                for (int i = 0; i < totalJogadores; i++) {
                    if (jogadores[i].id == numero) {
                        inserir(jogadores[i], &lista, pos);
                    }
                }
            }
        }else if(entrada3[0] == 'R'){
            if(entrada3[1] == 'I'){
                removerInicio(&lista);
            }else if(entrada3[1] == 'F'){
                removerFim(&lista);
            }else if(entrada3[1] == '*'){
                int pos;
                sscanf(entrada3, "R* %d", &pos);
                remover(&lista, pos);
            }
        }
    }
    

    for (int i = 0; i < lista.n; i++) {
        imprimir(&lista.array[i], i);
    }

    return 0;
}