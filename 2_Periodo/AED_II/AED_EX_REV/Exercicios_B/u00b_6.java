package AED_EX_REV;

public class u00b_6 {
    public boolean isVogal(char c) {
        c = Character.toLowerCase(c);
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    boolean isConsoante(String s, int n){
        boolean resp= true;
        if (n != s.length()){
        if (s.charAt(n)<'0' || s.charAt(n)>'9'){
        if (isVogal(s.charAt(n)) == true){
        resp= false;
        } else {
        resp=isConsoante(s, n + 1);
        }
        } else {
        resp=false;
        }
        }
        return resp;
        }           
}
