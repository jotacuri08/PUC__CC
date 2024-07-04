public class Questao_6 {
    public static void main (String[] args){
        String strDigitada = new String();
        boolean notFim = false;
        do {
            strDigitada = MyIO.readLine();
            notFim = not_Fim(strDigitada);
            
            // Se a entrada não for "FIM", continue verificando os tipos de caracteres
            if (notFim) {
                vogal(strDigitada);
                consoante(strDigitada);
                inteiro(strDigitada);
                real(strDigitada);
                MyIO.println("");
            }
        } while (notFim);
    }

    public static boolean not_Fim(String strDigitada){
        boolean notFim = false;
        
        // Verifica se a entrada não começa com "FIM"
        if(strDigitada.charAt(0) != 'F' || strDigitada.charAt(1) != 'I' || strDigitada.charAt(2) != 'M'){
            notFim = true;
        }
        
        return notFim;
    }

    public static boolean vogal(String strDigitada){
        for(int i = 0; i < strDigitada.length(); i++){
            // Verifica se o caractere atual não é uma vogal
            if(strDigitada.charAt(i) != 'a' && strDigitada.charAt(i) != 'e' && strDigitada.charAt(i) != 'i' && strDigitada.charAt(i) != 'o' && strDigitada.charAt(i) != 'u'){
                MyIO.print("NAO ");
                return false;
            }
        }
        MyIO.print("SIM ");
        return true;
    }

    public static boolean consoante(String strDigitada){
        for(int i = 0; i < strDigitada.length(); i++){
            // Verifica se o caractere atual é uma vogal ou não está no intervalo 'a' a 'z'
            if(strDigitada.charAt(i) == 'a' || strDigitada.charAt(i) == 'e' || strDigitada.charAt(i) == 'i' || strDigitada.charAt(i) == 'o' || strDigitada.charAt(i) == 'u'){
                MyIO.print("NAO ");
                return false;
            } else if(strDigitada.charAt(i) < 'a' || strDigitada.charAt(i) > 'z'){
                MyIO.print("NAO ");
                return false;
            }
        }
        MyIO.print("SIM ");
        return true;
    }

    public static boolean inteiro(String strDigitada){
        char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean isInt = true;
        int cont_nao_inteiro;
        for(int i = 0; i < strDigitada.length(); i++){
            cont_nao_inteiro = 0;
            for(int j = 0; j < numeros.length; j++){
                // Verifica se o caractere atual não é um número
                if(strDigitada.charAt(i) != numeros[j]){
                    cont_nao_inteiro++;
                }
            }
            if(cont_nao_inteiro == numeros.length){
                MyIO.print("NAO ");
                isInt = false;
                return isInt;
            }
        }
        MyIO.print("SIM ");
        return isInt;
    }

    public static boolean real(String strDigitada) {
        char[] numeros = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ',', '.'};
        boolean isReal = true;
        int cont_nao_inteiro;
        int cont_pontos = 0;
        for (int i = 0; i < strDigitada.length(); i++){
            cont_nao_inteiro = 0;
            if(strDigitada.charAt(i) == '.' || strDigitada.charAt(i) == ','){
                cont_pontos++;
            }
            for(int j = 0; j < numeros.length; j++){
                // Verifica se o caractere atual não é um número ou se há mais de um ponto
                if(strDigitada.charAt(i) != numeros[j]){
                    cont_nao_inteiro++;
                }
            }
            if(cont_nao_inteiro == numeros.length){
                isReal = false;
                MyIO.print("NAO");
                return isReal;
            }
            if(cont_pontos > 1){
                isReal = false;
                MyIO.print("NAO");
                return isReal;
            }
        }
        MyIO.print("SIM");
        return isReal;
    }
}
