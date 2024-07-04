

public class Questao_13 {
    
    public static boolean not_Fim(String strDigitada){
        boolean notFim = false;
        if(strDigitada.charAt(0) != 'F' || strDigitada.charAt(1) != 'I' || strDigitada.charAt(2) != 'M'){
            notFim = true;
        }
            return notFim;
    }

    public static char[] converter_string(String strDigitada){
        char[] array = new char[strDigitada.length()];
        for(int i = 0; i < strDigitada.length(); i++){
            array[i] = strDigitada.charAt(i);
        }
        return array;
    }

    public static boolean isVogal(char[] palavra, int posicao_atual){
        if(posicao_atual >= palavra.length){
            return true;
        }
        if(palavra[posicao_atual] == 'a' || palavra[posicao_atual] == 'e' || palavra[posicao_atual] == 'i' || palavra[posicao_atual] == 'o' || palavra[posicao_atual] == 'u'){
            return isVogal(palavra, posicao_atual + 1);
        }else{
            return false;
        }
    }

    public static boolean isConsoant(char[] palavra, int posicao_atual){
        if(posicao_atual >= palavra.length){
            return true;
        }
        if(palavra[posicao_atual] != 'a' && palavra[posicao_atual] != 'e' && palavra[posicao_atual] != 'i' && palavra[posicao_atual] != 'o' && palavra[posicao_atual] != 'u'){
            if(palavra[posicao_atual] > 'a' && palavra[posicao_atual] <= 'z'){
                return isConsoant(palavra, posicao_atual + 1);
            }
        }else{
            return false;
        }
        return false;
    }

    public static boolean inteiro(char[] palavra, int posicao_atual){
        if(posicao_atual >= palavra.length){
            return true;
        }
        if((int) palavra[posicao_atual] >= 48 && (int) palavra[posicao_atual] <= 57){
            return inteiro(palavra, posicao_atual + 1);
        }else{
            return false;
        }
    }

    public static boolean isfloat(char[] palavra, int posicao_atual, int cont_pontos){
        if(posicao_atual >= palavra.length){
            return true;
        }
        if((int) palavra[posicao_atual] >= 48 && (int) palavra[posicao_atual] <= 57){
            return isfloat(palavra, posicao_atual + 1, cont_pontos);
        }
        else if(palavra[posicao_atual] == ',' || palavra[posicao_atual] == '.'){
            return isfloat(palavra, posicao_atual + 1, cont_pontos + 1);
        }
        if(cont_pontos > 1){
            return false;
        }
        return false;

    }
    public static void main(String[] args){
        String strDigitada;
        boolean notFim = false;
        int posicao_atual = 0;
        int cont_pont = 0;
        do{
            strDigitada = MyIO.readLine();
            char[] array = converter_string(strDigitada);
            notFim = not_Fim(strDigitada);
            if(!notFim){
                break;
            }
            if(isVogal(array, posicao_atual)){
                MyIO.print("SIM ");
            }else{
                MyIO.print("NAO ");
            }
            if((isConsoant(array, posicao_atual))){
                MyIO.print("SIM ");
            }else{
                MyIO.print("NAO ");
            }
            if((inteiro(array, posicao_atual))){
                MyIO.print("SIM ");
            }else{
                MyIO.print("NAO ");
            }
            if((isfloat(array, posicao_atual, cont_pont))){
                MyIO.print("SIM");
            }else{
                MyIO.print("NAO");
            }
            MyIO.println("");
        }while(notFim);
    }
}
