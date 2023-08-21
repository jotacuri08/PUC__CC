import mypackage.MyIO;
public class u00g_35 {
    public static void main (String[] args){
    int L, C;
    do{
    L = MyIO.readInt("Digite quantas linhas vao ter: ");
    C = MyIO.readInt("Digite quantas colunas vao ter: ");
    }while(L != C);
    int[][] matriz = new int[L][C];
    for(int i = 0; i < L; i++){
        for(int j = 0; j < C; j++){
            matriz[i][j] = MyIO.readInt("Digite o elemento " + (i + 1) + (j + 1) + " da matriz ");
        }
    }
    for(int i=0; i < L; i++){
        for(int j=0; j <= i; j++){
            if(i == j){
                MyIO.print(matriz[i][j] + "|");
            }
        }
    MyIO.println("");
    }
    for(int i = 0; i < L; i++){
        MyIO.print(matriz[i][L - i - 1]);
    }
}
}