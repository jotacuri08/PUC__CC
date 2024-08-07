package TADL;

public class Lista {
    int[] array;
    int n;
                                                                                                                                                                                                
    Lista(){
        this(6);
    }

    Lista(int tamanho){
        array = new int[tamanho];
        n = 0;
    }

    void inserirInicio(int x) throws Exception{
        if(n >= array.length){
            throw new Exception("erro");
        }
        for(int i = n; i > 0; i--){
           array[i] = array[i-1];
        }
        array[0] = x;
        n++;
    }

    void inserirFim(int x) throws Exception{
        if(n == array.length){
            throw new Exception ("erro");
        }

        array[n] = x;
        n++;
    }

    void inserir(int x, int pos) throws Exception{
        if(n == array.length){
            throw new Exception("erro");
        }

        for(int i = n; i > pos; i--){
            array[i] = array[i-1];
        }

        array[pos] = x;
        n++;
    }

    int removerInicio() throws Exception{
        if(n == 0){
            throw new Exception("erro");
        }

        int removido = array[0];
        n--;

        for(int i = 0; i < n; i++){
            array[i] = array[i+1];
        }
        
        return removido;
    }

    int removerFim() throws Exception{
        if (n == 0){
            throw new Exception("erro");
        }
        
        n--;
        int removido = array[n];
        return removido;
    }

    int remover(int pos) throws Exception{
        if(n == 0){
            throw new Exception("erro");
        }

        int removido = array[pos];
        n--;
        
        for(int i = pos; i < n; i++){
            array[i] = array[i+1];
        }
        
      
        return removido;
    }

    void mostrar(){
        for(int i = 0; i < n; i++){
            System.out.println(array[i]);
        }
    }

}
