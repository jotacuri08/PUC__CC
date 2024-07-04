package TADF;

import java.io.EOFException;

public class ListaSimples {
    private Celula primeiro, ultimo;
    public ListaSimples(){
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirInicio(int x){
        Celula tmp = new Celula(x);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if(primeiro == ultimo){
            ultimo = tmp;
        }
        tmp = null;
    }

    public void inserirFim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    public int removerInicio() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }
        int removido = primeiro.prox.elemento;
        Celula tmp = new Celula();
        tmp.prox = primeiro.prox.prox;
        primeiro = tmp;
        tmp = null;
        return removido;
    }

    public int removerFim() throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }

        int removido = ultimo.elemento;
        Celula i;
        for(i = primeiro; i.prox != ultimo; i = i.prox);
        ultimo = i;
        i = null;
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
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = new Celula(x);
            tmp.prox = i.prox;
            i.prox = tmp;
            tmp = i = null;
        }
    }

    public int remover(int pos) throws Exception{
        int tamanho = tamanho();
        if(pos < 0 || pos > tamanho){
            throw new Exception("erro");
        }
        else if(pos == 0){
            removerInicio();
        }
        else if(pos == tamanho - 1){
            removerFim();
        }
        else{
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            int removido = i.prox.elemento;
            Celula tmp = i.prox;
            i.prox = tmp.prox;
            tmp.prox = null;
            i = tmp = null;
            return removido;
        }
    }


    public int tamanho(){
        int tamanho = 0;
        Celula i;
        for(i = primeiro; i.prox != null; i = i.prox){
            tamanho++;
        }
        return tamanho;
    }
}
