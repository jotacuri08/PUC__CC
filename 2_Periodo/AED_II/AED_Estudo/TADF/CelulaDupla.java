package TADF;

public class CelulaDupla {
    public int elemento;
    public CelulaDupla prox, ant;
    public CelulaDupla(){
        this(0);
    }
    public CelulaDupla (int x){
        this.elemento = x;
        this.prox = this.ant = null;
    }
}
