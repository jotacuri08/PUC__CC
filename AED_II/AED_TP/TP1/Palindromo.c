#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

bool palindromo(char palavra[]){
    int tamanho;
    tamanho = strlen(palavra);
    for(int i=0, j = tamanho - 1; i < tamanho/2; i++, j--){
        if(palavra[i] != palavra[j]){
            return false;
        }
    }
    return true;
}

int main(){
    char* palavra = (char*)malloc(250 * sizeof(char));

    scanf(" %[^\r\n]%*c", palavra);
    while(strcmp(palavra, "FIM")){
        if(palindromo(palavra)){
            printf("SIM\n");
        }else{
            printf("NAO\n");
        }
        scanf(" %[^\r\n]%*c", palavra);
    }
}