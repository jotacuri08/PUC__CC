package TADL;

public class Fila {
    int[] array;
    int primeiro, ultimo;

    Fila(){
        this(5);
    }

    Fila(int tamanho){
    array = new int[tamanho + 1];
    primeiro = ultimo = 0;
    }

    void inserir(int x) throws Exception{
        if(((ultimo + 1) % array.length) == primeiro)
            throw new Exception("erro");
        
        array[ultimo] = x;
        ultimo = (ultimo + 1) % array.length;
    }

    int remover() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }

        int removido = array[primeiro];
        primeiro = (primeiro + 1) % array.length;
        return removido;
    }

    void mostrar(){
        int i = primeiro;

        while((i % array.length) != ultimo){
            System.out.println(array[i]);
            i = (i + 1) % array.length;
        }
    }
}
