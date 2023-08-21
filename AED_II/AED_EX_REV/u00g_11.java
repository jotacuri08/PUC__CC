import mypackage.MyIO;
public class u00g_11 {
    public static void main(String [] args){
        int num = MyIO.readInt("Digite o numero ");
        if(num % 2 == 0){
                num--;
            }
        while (num > 0){
            MyIO.println(num);
            num -= 2;
        }
    }
}
