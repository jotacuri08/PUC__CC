package AED_EX_REV;

public class u00_3 {
    public static void maior_menor(int vet[]){
        int maior = vet[0], menor = vet[0];
        for(int i = 1; i < vet.length; i++){
            if(vet[i] < menor){
                menor = vet[i];
            }else if(vet[i] > maior){
                maior = vet[i];
            }
        }
        System.out.println( menor + maior);
    }

    public static void main(){
        int vet[] = {10,20,30};
        maior_menor(vet);
    }
}
