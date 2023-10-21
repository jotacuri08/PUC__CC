package AED_EX_REV;

public class u00_1 {
    
    public static boolean int_array(int vet[], int num){
        for(int i= 0; i < vet.length; i++){
            if(vet[i] == num){
                System.out.println("contido");
                return true;
            }
        }
        System.out.println("nao contido");
        return false;
    }
    
    public static void main(String[] args){
        int vet[] = {10,20,30}, num = 30;
        int_array(vet, num);
    }
}