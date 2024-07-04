#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#define tamanho 3

bool int_array(int vet[], int num){
    for(int i = 0; i < tamanho; i++){
        if(vet[i] == num){
            printf("true");
            return true;
        }
    }
    printf("false");
    return false;
}

int main(){
    int vet[3] = {10,20,30}, num = 40;
    int_array(vet,num);
}