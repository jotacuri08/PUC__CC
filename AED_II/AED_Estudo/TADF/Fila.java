package TADF;

public class Fila {
    private Celula primeiro, ultimo;
    
    public Fila() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserir(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    //sem no cabeça
    public int remover() throws Exception{
        if(ultimo == primeiro){
            throw new Exception("erro");
        }

        int removido = primeiro.prox.elemento;
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        tmp.prox = null;
        tmp = null;
        return removido;
    } 

    //com no cabeça
    public int remover2() throws Exception{
        if(ultimo == primeiro){
            throw new Exception("erro");
        }

        Celula tmp = primeiro.prox;
        primeiro.prox = primeiro.prox.prox;
        int removido = tmp.elemento;
        tmp.prox = null;
        tmp = null;
        return removido;
    }

    public int maior(){
        Celula tmp = primeiro.prox;
        int maior = tmp.elemento;
        while(tmp != null){
            if(tmp.elemento > maior){
                maior = tmp.elemento;
            }
            tmp = tmp.prox;
        }

        return maior;
    }

    public void mostrar(){
        if (primeiro.prox == null) { // Verifica se a lista está vazia
            throw new RuntimeException("A lista está vazia."); // Ou retorne um valor apropriado
        }
        Celula tmp = primeiro.prox;
        while(tmp!=null){
            System.out.println(tmp.elemento);
            tmp = tmp.prox;
        }
    }
}
