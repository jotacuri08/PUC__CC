#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool palindromo(char palavra[]){
    int tamanho;
    tamanho = strlen(palavra);
    for(int i=0, j = tamanho - 1; i < tamanho/2; i++, j--){
        if(palavra[i] != palavra[j]){
            printf("NAO\n");
            return false;
        }
    }
    printf("SIM\n");
    return true;
}

int main(){
    char palavra[250];
    char fim[4] = {'F','I', 'M'};
    
    while(1){
        scanf(" %[^\n]", palavra);
        int valor = strcmp(palavra , fim);
        if(valor == 0){
            return 0;
        }
        palindromo(palavra);
    }
    
}