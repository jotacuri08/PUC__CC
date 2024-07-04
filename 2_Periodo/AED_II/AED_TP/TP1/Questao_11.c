#include <stdio.h>
#include <stdbool.h>
#include <string.h>
bool isPalindromo(char palavra_digitada[], int inicio, int tamanho){
    if(inicio >= tamanho){
        return true;
    }
    if(palavra_digitada[inicio] == palavra_digitada[tamanho - 1]){
        return isPalindromo(palavra_digitada, inicio + 1, tamanho - 1);
    }else{
        return false;
    }

}

int main(){
    char palavra_digitada[1000];
    scanf(" %[^\n]", &palavra_digitada);
    int tamanho, inicio;
    bool resp;
    while(strcmp(palavra_digitada, "FIM")){
        tamanho = strlen(palavra_digitada);
        resp = isPalindromo(palavra_digitada, 0, tamanho);
        if(resp){
            printf("SIM\n");
        }else{
            printf("NAO\n");
        }
        scanf(" %[^\n]", &palavra_digitada);
    }
}