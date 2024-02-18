package TabelaHash;

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

class Lista{
    Celula primeiro, ultimo;
    Lista(){
        Celula primeiro = new Celula();
        ultimo = primeiro;
    }
    boolean pesquisar(int x){
        boolean resp = false;
        if(primeiro == ultimo){
            return resp;
        }
        else if(x == primeiro.prox.elemento){
            return true;
        }
        Celula i;
        for(i = primeiro.prox; i != null; i = i.prox){
            if(i.elemento == x){
                return true;
            }
        }
        return resp;
    }
    
    void inserirFim(int x){
        ultimo.prox = new Celula(x);
        ultimo = ultimo.prox;
    }

    int remover(int x) throws Exception{
        if(primeiro == ultimo){
            throw new Exception("erro");
        }
        Celula i;
        for(i = primeiro; i != null; i = i.prox){
            if(i.prox.elemento == x){
                break;
            }
        }
        int removido = i.prox.elemento;
        Celula tmp = i.prox;
        i.prox = tmp.prox;
        tmp.prox = null;
        tmp = null;
        return removido;
    }
}

class HashLista{
    Lista[] tabela;
    int tamanho;
    public HashLista(int tamanho){
        tabela = new Lista[tamanho];
        for(int i = 0; i < tamanho; i++){
            tabela[i] = new Lista();
        }
    }
    public int hash(int x){
        return x % tamanho;
    }

    boolean pesquisar(int x){
        int pos = hash(x);
        return tabela[pos].pesquisar(x);
    }

    void inserir(int x){
        int pos = hash(x);
        tabela[pos].inserirFim(x);
    }

    int remover(int x){
        int pos = hash(x);
        if(tabela[pos].pesquisar(x) == true){
            int removido;
            return removido = tabela[pos].remover(x);
        }else{
           return -1;
        }
    }
}

class HashDiretaReserva{
    int[] tabela;
    int tamanho, tamanhoReserva, tamanhoTotal, reserva;
    public HashDiretaReserva(int tamanho, int tamanhoReserva){
        this.tamanhoTotal = tamanho + tamanhoReserva;
        tabela = new int[tamanhoTotal];
        for(int i = 0; i < tamanhoTotal; i++){
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
        if(tabela[pos] == -1){
            tabela[pos] = x;
            resp = true;
       }else{
            for(int i = tamanho; i < tamanhoTotal; i++){
                if(tabela[i] == -1){
                    tabela[i] = x;
                    reserva++;
                    resp = true;
                }
            }
       }
       return resp;
    }

    public boolean pesquisar(int x){
        boolean resp = false;
        int pos = hash(x);
        if(tabela[pos] == x){
            resp = true;
        }else{
            for(int i = tamanho; i < tamanhoTotal; i++){
              if(tabela[i] == x){
                    resp = true;
                    break;
                }
            }
        }
        return resp;
    }
}

class HashRehash{
    int[] tabela;
    int tamanho;
    public HashRehash(int tamanho){
        tabela = new int[tamanho];
        this.tamanho = tamanho;
        for(int i = 0; i < tamanho; i++){
            tabela[i] = -1;
        }
    }
    int hash(int x){
        return x % tamanho;
    }
    int reHash(int x){
        return x++ % tamanho;
    }
    boolean inserir(int x){
        boolean resp = false;
        int pos = hash(x);
        
        if(tabela[pos] != -1){
            int pos2 = reHash(x);
            if(tabela[pos2] == -1){
                tabela[pos2] = x;
                resp = true;
            }
        }else{
            tabela[pos] = x;
            resp = true;
        }
        return resp;
    }
}

