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

typedef struct Fila{
    Jogador array[6];
    int primeiro;
    int ultimo;
}Fila;

void start(Fila* fila){
    fila -> primeiro = fila -> ultimo = 0; 
}

void inserir(Jogador jogador, Fila* fila) {

   //validar insercao
   if (((fila -> ultimo + 1) % 6) == fila -> primeiro) {
      fila->primeiro = (fila->primeiro + 1) % 6;
   }

   fila -> array[fila -> ultimo] = jogador;
   fila -> ultimo = (fila -> ultimo + 1) % 6;
}


Jogador remover(Fila *fila) {

   //validar remocao
   if (fila -> primeiro == fila -> ultimo) {
      exit(1);
   }

   Jogador removido = fila -> array[fila -> primeiro];
   printf("(R) %s\n", removido.nome);
   fila -> primeiro = (fila -> primeiro + 1) % 6;
   return removido;
}


void imprimir(Jogador* jogador) {
    printf("[%d ## %s ## %d ## %d ## %d ## %s ## %s ## %s ##\n", jogador->id , jogador->nome, jogador->altura, jogador->peso, jogador->anoNascimento, jogador->universidade, jogador->cidadeNascimento, jogador->estadoNascimento);
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

    // Ler os IDs da entrada padrão e inserir na lista
    char entrada[3923];  // ID ou "FIM"
    while (scanf("%s", entrada) == 1 && strcmp(entrada, "FIM") != 0) {
    int idDesejado = atoi(entrada);  // Converte string para int

    // Procura o jogador com o ID desejado e insere na lista
    for (int i = 0; i < totalJogadores; i++) {
        if (jogadores[i].id == idDesejado) {
            inserir(jogadores[i], &fila);

            double alturaTotal = 0;
            int cont = 0;

            // Calcula a altura média
            for (int j = fila.primeiro; j != fila.ultimo; j = (j + 1) % 6) {
                alturaTotal += fila.array[j].altura;
                cont++;
            }

            if (cont > 0) {  // Evita divisão por zero
                double mediaAltura = round(alturaTotal / cont);
                printf("%d\n", (int)mediaAltura);
            }

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
            int numero;
                sscanf(entrada3, "I %d", &numero);
                for (int i = 0; i < totalJogadores; i++) {
                    if (jogadores[i].id == numero) {
                        inserir(jogadores[i], &fila);
                        double alturaTotal = 0;
                        int cont = 0;

                        // Calcula a altura média
                        for (int j = fila.primeiro; j != fila.ultimo; j = (j + 1) % 6) {
                            alturaTotal += fila.array[j].altura;
                            cont++;
                        }

                        if (cont > 0) {  // Evita divisão por zero
                            double mediaAltura = round(alturaTotal / cont);
                            printf("%d\n", (int)mediaAltura);
                        }
                    }
                }
        }else if(entrada3[0] == 'R'){
            remover(&fila);
            
        }
    }    


    for (int i = 0; i < 6; i++) {
        if(i == 2 || i == 3)
        imprimir(&fila.array[i]);
    }
    
    return 0;
}