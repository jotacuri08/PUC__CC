package TADL;

public class Pilha {
    int[] array;
    int n;

    Pilha(){
        this(6);
    }

    Pilha(int tamanho){
        array = new int[tamanho];
        n=0;
    }
}
