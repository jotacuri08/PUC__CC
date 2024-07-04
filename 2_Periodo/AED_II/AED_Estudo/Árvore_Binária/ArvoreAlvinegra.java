package Árvore_Binária;

public class ArvoreAlvinegra {
    NoAN raiz;
    ArvoreAlvinegra(){
        raiz = null;
    }
    
    boolean isNoTipoQuatro(NoAN i){
        boolean resp = false;
        if(i == null || i.esq == null || i.dir == null){
            return resp;
        }
        else if(i.esq.cor == true && i.dir.cor == true){
            resp = true;
            return resp;
        }
        else{
            return resp;
        }
    }

    void fragmentarNoTipoQuatro(NoAN i) throws Exception{
        if(i == null || i.dir == null || i.esq == null){
            throw new Exception("erro");
        }
        else if(i.cor == false && i.esq.cor == true && i.dir.cor == true){
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
        }
    }

    NoAN rotacionarDir(NoAN i){
        NoAN noEsq = raiz.esq;
        NoAN noEsqDir = raiz.esq.dir;
        noEsq.dir = raiz;
        raiz.esq = noEsqDir;
        return noEsq;
    }

    void inserir(int elemento){
        if(raiz == null){
            raiz = new NoAN(elemento);
        }
        else if(raiz.dir == null && raiz.esq == null){
            if(elemento < raiz.elemento){
                raiz.esq = new NoAN(elemento);
            }
            else if(elemento > raiz.elemento){
                raiz.dir = new NoAN(elemento);
            }
        }
        else if(raiz.dir == null){
            if(elemento > raiz.elemento){
                raiz.dir = new NoAN(elemento);
            }
            else if(elemento < raiz.elemento){
                if(elemento < raiz.esq.elemento){
                    raiz.dir.esq = new NoAN(elemento);
                    raiz = rotacionarDir(raiz);
                    raiz.cor = false;
                    raiz.esq.cor = true;
                    raiz.dir.cor = true;
                }
            }
        }
    }
}
