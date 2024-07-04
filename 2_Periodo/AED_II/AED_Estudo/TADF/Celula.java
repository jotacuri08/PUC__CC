package TADF;


class Celula{
    int elemento;
    Celula prox;
    Celula(){
        this(0);
    }
    Celula(int elemento){
        this.elemento = elemento;
        this.prox = null;
    }
}

class ListaSimples{
    Celula primeiro, ultimo;
    ListaSimples(){
        primeiro = new Celula();
        ultimo = primeiro;
    }
    void inserirFim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }
    boolean pesquisar(int x){
        boolean resp = false;
        if(primeiro == ultimo){
            erro;
        }
        Celula i;
        for(i = primeiro.prox; i != null; i = i.prox){
            if(i.elemento == x){
                resp = true;
                break;
            }
        }
        return resp;
    }

    int remover(int pos)throws Exception{
        if(primeiro == ultimo){
           throw new Exception("erro");
        }
        Celula i = primeiro;
        for(int j = 0; j < pos; j++){
            i = i.prox;
            if(i == null){
                throw new Exception("erro");
            }
        }
        int removido = i.prox.elemento;
        Celula tmp = i.prox;
        tmp.prox = i.prox.prox;
        i.prox = tmp.prox;
        tmp.prox = null;
        i = null;
        return removido;   
    }
}

class No{
    int elemento;
    No esq, dir;
    No(){
        this(0);
    }
    No(int elemento){
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }
}

class ArvoreBinaria{
    No raiz;
    ArvoreBinaria(){
        raiz = null;
    }
    void inserir(int x)throws Exception{
        raiz = inserir(x, raiz);
    }
    No inserir(int x, No i)throws Exception{
        if(i == null){
            i = new No(x);
        }
        else if(x < i.elemento){
            i.esq = inserir(x, i.esq);
        }
        else if(x > i.elemento){
            i.dir = inserir(x, i.dir);
        }
        return i;
    }

    boolean pesquisar(int x){
        boolean resp = pesquisar(x, raiz);
    }
    
    boolean pesquisar(int x, No i){
        boolean resp = false;
        if(i == null){
            return resp;
        }
        else if(i.elemento == x){
            resp = true;
            return resp;
        }
        else if(x < i.elemento){
            resp = pesquisar(x , i.esq);
        }
        else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    void caminharCentral(No i){
        if(i != null){
            caminharCentral(i.esq);
            System.out.println(i.elemento);
            caminharCentral(i.dir);
        }

    }

    void remover(int x){
        raiz = remover(x, raiz);
    }

    No remover(int x, No i) throws Exception{
        if(i == null){
            throw new Exception();
        }
        else if(x < i.elemento){
            i.esq = remover(x, i.esq);
        }

    }
}


class HashDiretaReserva{
    int[] tabela;
    int tamanho, tamanhoTotal, tamanhoReserva, reserva;
    HashDiretaReserva(int tamanho, int tamanhoReserva){
        this.tamanhoTotal = tamanho + tamanhoReserva;
        tabela = new int[tamanho];
        for(int i = 0; i < tamanho; i++){
            tabela[i] = -1;
        }
        this.tamanho = tamanho;
        this.tamanhoReserva = tamanhoReserva;
        reserva = 0;
    }
    int hash(int x){
        return x % tamanho;
    }
    boolean inserir(int x){
        boolean resp = false;
        int pos = hash(x);
        if(tabela[pos] == x){
            for(int i = tamanho; i < tamanhoTotal; i++){
                if(tabela[i] == -1){
                    resp = true;
                    tabela[i] = x;
                    return resp;
                }
            }
        }
        return resp;
    }
}