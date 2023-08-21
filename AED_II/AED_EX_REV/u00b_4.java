package AED_EX_REV;

public class u00b_4 {
    public static void maior_menor(int vet[]){
        int maior = vet[0];
        int menor = vet[0];
        
        for (int i = 1; i < vet.length; i += 2) {
            int a = vet[i];
            int b = vet[i - 1];
            
            if (a > b) {
                if (a > maior) {
                    maior = a;
                }
                if (b < menor) {
                    menor = b;
                }
            } else {
                if (b > maior) {
                    maior = b;
                }
                if (a < menor) {
                    menor = a;
                }
            }
        }
        
        System.out.println("Menor: " + menor);
        System.out.println("Maior: " + maior);
    }

    public static void main(String[] args){
        int vet[] = {10, 20, 30};
        maior_menor(vet);
    }
}
