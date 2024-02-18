package TADF;

public class ListaDupla {
    private CelulaDupla primeiro, ultimo;
    public ListaDupla(){
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    public void inserirInicio(int x){
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.prox = primeiro.prox;
        tmp.ant = primeiro;
        primeiro.prox = tmp;
        if(primeiro == ultimo){
            ultimo = tmp;
        }else{
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    public void inserirFim(int x){
        ultimo.prox = new CelulaDupla(x);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public int removerInicio() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }

        int removido = primeiro.prox.elemento;
        CelulaDupla tmp = primeiro;
        primeiro = primeiro.prox;
        primeiro.ant = tmp.prox = null;
        tmp = null;
        return removido;
    }

    public int removerFim() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }

        int removido = ultimo.elemento;
        CelulaDupla tmp = ultimo;
        ultimo = ultimo.ant;
        ultimo.prox = tmp.ant = null;
        tmp = null;
        return removido;
    }

    public void inserir(int x, int pos) throws Exception{
        int tamanho = tamanho();
        if(pos < 0 || pos > tamanho){
            throw new Exception("erro");
        }
        else if(pos == 0){
            inserirInicio(x);
        }
        else if(pos == tamanho){
            inserirFim(x);
        }
        else{
        
        CelulaDupla i = primeiro;
        for(int j = 0; j < pos; i = i.prox, j++);
        CelulaDupla tmp = new CelulaDupla(x);
        tmp.prox = i.prox;
        tmp.ant = i;
        tmp.prox.ant = tmp.ant.prox = tmp;
        tmp = i = null;
        }
    }

    public int remover(int pos) throws Exception{
        int tamanho = tamanho();
        if(primeiro == ultimo || pos > tamanho - 1){
            throw new Exception("erro");
        }
        else if(pos == 0){
            removerInicio();
        }
        else if(pos == tamanho - 1){
            removerFim();
        }
        else{
            CelulaDupla i = primeiro.prox;
            for(int j = 0; j < pos; j++, i = i.prox);
            int elemento = i.elemento;
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            i.prox = i.ant = null;
        }
        return elemento;
    }

    public int tamanho(){
        int tamanho = 0;
        CelulaDupla i;
        for(i = primeiro; i.prox != null; i = i.prox){
            tamanho++;
        }
        return tamanho;
    }
}
