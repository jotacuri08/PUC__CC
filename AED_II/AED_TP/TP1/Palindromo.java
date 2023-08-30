import mypackage.MyIO;
public class Palindromo {
    public static boolean Palindromo(String str){
        for(int i = 0, j = str.length() - 1; i < str.length()/2; i++, j--){
            if(str.charAt(i) != str.charAt(j)){
                MyIO.println("NAO");
                return false;
            }
        }
        MyIO.println("SIM");
        return true;
    }
    public static void main(String[] args){
        String str;
        do {
            str = MyIO.readLine();
            if (!str.equals("FIM")) {
                Palindromo(str);
            }
        } while (!str.equals("FIM"));
    }
}