package Árvore_Binária;

import java.util.Random;

public class ArvoreBinaria {
    No raiz;
    ArvoreBinaria(){
        raiz = null;
    }

    void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    No inserir(int x, No i) throws Exception{
        if(i == null){
            i = new No(x);
        }
        else if(x < i.elemento){
            i.esq = inserir(x, i.esq);
        }
        else if(x > i.elemento){
            i.dir = inserir(x, i.dir);
        }
        else{
            throw new Exception("erro");
        }
        return i;
    }

    void inserirPai(int x) throws Exception{
        if(raiz == null) {
            raiz = new No(x);
        }
        else if (x < raiz.elemento) {
            inserirPai(x, raiz.esq, raiz);
        }
        else if (x > raiz.elemento) {
            inserirPai(x, raiz.dir, raiz);
        }
        else {
            throw new Exception("erro");
        }
    }

    void inserirPai(int x, No i, No pai) throws Exception{
        if (i == null){
            if(x < pai.elemento){
                pai.esq = new No(x);
            }
            else{
                pai.dir = new No(x);
            }
        }
        else if (x < i.elemento){
            inserirPai(x, i.esq, i);
        }
        else if (x > i.elemento){
            inserirPai(x, i.dir, i);
        }
        else{
            throw new Exception("erro");
        }
    }

    boolean pesquisar (int x) {
        return pesquisar(x, raiz);
    }

    boolean pesquisar(int x, No i) {
        boolean resp = false;
        if(i == null){
            return resp;
        }
        else if(x == i.elemento){
            resp = true;
        }
        else if(x < i.elemento){
            resp = pesquisar(x, i.esq);
        }
        else{
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    void caminharCentral(No i){
        if(i != null) {
            caminharCentral(i.esq);
            System.out.print(i.elemento + " ");
            caminharCentral(i.dir);
        }
    }

    void caminharPre(No i) {
        if (i != null) {
            System.out.print(i.elemento + " ");
            caminharPre(i.esq);
            caminharPre(i.dir);
        }
    }

    void caminharPos(No i) {
        if (i != null) {
            caminharPos(i.esq);
            caminharPos(i.dir);
            System.out.print(i.elemento + " ");
        }
    }
       
    int getMaior() throws Exception{
        int maior;
        No i = raiz;
        if(i == null){
            throw new Exception("erro");
        }
        else if(i.dir == null){
            maior = i.elemento;
            return maior;
        }
        do{
            i = i.dir;
        }while(i.dir != null);
        maior = i.elemento;
        return maior;
    }
    
    int getMenor() throws Exception{
        int menor = -1;
        No i;
        if(raiz != null){
            for(i = raiz; i.esq != null; i = i.esq){
                menor = i.elemento;
            }
        }
        return menor;
    }

    void remover(int x) throws Exception {
        raiz = remover(x, raiz);
    }

    No remover(int x, No i) throws Exception {
        if(i == null){
            throw new Exception("erro");
        }
        else if(x < i.elemento){
            i.esq = remover(x, i.esq);
        }
        else if(x > i.elemento){
            i.dir = remover(x, i.dir);
        }
        else if(i.dir == null){
            i = i.esq;
        }
        else if(i.esq == null){
            i = i.dir;
        }
        else{
            i.esq = maiorEsq(i, i.esq);
        }
        return i;
    }
    
    No maiorEsq(No i, No j){
        if(j.dir == null){
            i.elemento = j.elemento;
            j = j.esq;
        }
        else{
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }
    
    No rotacionarEsq(No no){
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        return noDir;
    }

    No rotacionarDir(No no){
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;
        return noEsq;
    }

    No rotacaoDuplaDirEsq(No no){
        No noDir = no.dir;
        rotacionarDir(noDir);
        rotacionarEsq(no);
        return noDir;
    }

    No rotacaoDuplaEsqDir(No no){
        No noEsq = no.esq;
        rotacionarEsq(noEsq);
        rotacionarDir(no);
        return noEsq;
    }

    int altura(){
        int altura = altura(raiz, 0); 
        return altura;
    }

    int altura(No i, int altura){
        if(i == null){
            altura--;
        }
        else{
            int alturaEsq = altura(i.esq, altura+1);
            int alturaDir = altura(i.dir, altura + 1);
            if(alturaEsq > alturaDir){
                altura = alturaEsq;
            }
            else{
                altura = alturaDir;
            }
        }
        return altura;
    }

    int getSoma(){
        int soma = getSoma(raiz);
        return soma;
    }

    int getSoma(No i){
        if(i != null){
            int somaEsq = getSoma(i.esq);
            int somaDir = getSoma(i.dir);
            return i.elemento + somaEsq + somaDir;
        }
        return 0;
    }

    int getPares(){
        return getPares(raiz);
    }

    int getPares(No i){
        if(i != null){
            int num;
            if(i.elemento % 2 == 0){
                num = 1;
            }else{
                num = 0;
            }
            num += getPares(i.esq);
            num += getPares(i.dir);
            return num;
        }
        return 0;
    }

    public static boolean isEqual(ArvoreBinaria a, ArvoreBinaria b){
        return isEqual(a.raiz, b.raiz);
    }
    
    public static boolean isEqual(No i, No i2){
        // Se ambos os nós são nulos, são considerados iguais (caso base)
        if(i == null && i2 == null){
            return true;
        }
        // Se apenas um dos nós é nulo ou se os valores são diferentes, as árvores não são iguais
        else if(i == null || i2 == null || i.elemento != i2.elemento){
            return false;
        }
        // Se os valores nos nós são iguais, verifica as subárvores esquerda e direita
        else {
            return isEqual(i.esq, i2.esq) && isEqual(i.dir, i2.dir);
        }
    }

    boolean hasOnze(){
        boolean resp = hasOnze(raiz);
        return resp;
    }
    
    boolean hasOnze(No i){
        if(i == null){
            return false;
        }
        else if(i.elemento % 11 == 0){
            return true;
        }
        else{
            return hasOnze(i.esq) || hasOnze(i.dir);
        }
    }

    
}
