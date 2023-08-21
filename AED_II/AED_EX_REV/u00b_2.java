package AED_EX_REV;

public class u00b_2 {
    public static boolean int_array(int vet[], int num){
        int pos = vet.length/2;
        if(vet[pos] > num) {
            for(int i = vet.length/2; i < vet.length; i++){
                if(vet[i] == num){
                    System.out.println("contido");
                    return true;
                }
            }
        }else{
            for(int i = pos + 1; i < vet.length; i++){
                if(vet[i] == num){
                    System.out.println("contido");
                    return true;
                }
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
