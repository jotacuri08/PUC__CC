import mypackage.MyIO;
public class u00g_42 {
    public static void main(String[] args){
        String str = MyIO.readString("Digite a string: ");
        for(int i = 0, j = str.length() - 1; i < str.length()/2; i++, j--){
            if(str.charAt(i) != str.charAt(j)){
                MyIO.println("nao e palindro");
                return;
            }
        }
        MyIO.println("Palindromo");
    }
}
