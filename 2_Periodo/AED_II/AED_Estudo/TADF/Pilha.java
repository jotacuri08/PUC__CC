package TADF;

public class Pilha {
    private Celula topo;
    
    public Pilha(){
        topo = null;
    }

    void inserir(int x){
        Celula tmp = new Celula(x);
        tmp.prox = topo;
        topo = tmp;
        tmp = null;
    }

    public int remover() throws Exception{
        if(topo == null){
            throw new Exception("erro");
        }
        int removido = topo.elemento;
        Celula tmp = topo;
        topo = tmp.prox;
        tmp.prox = null;
        tmp = null;
        return removido;
    }

    public void mostrar(){
        Celula temp = topo;
        while(temp != null){
            System.out.println(temp.elemento);
            temp = temp.prox;
        }
    }
}
