package Árvore_Binária;

public class NoAN {
    public int elemento;
    public boolean cor;
    public NoAN esq, dir;
    public NoAN(int elemento){
        this(elemento, false, null, null);
    }
    public NoAN(int elemento, boolean cor){
        this(elemento, cor, null, null);
    }
    public NoAN(int elemento, boolean cor, NoAN esq, NoAN dir){
        this.elemento = elemento;
        this.cor = cor;
        this.esq = esq;
        this.dir = dir;
    }
}
