import mypackage.MyIO;


public class u00g_2 {
    public static int MaiorInt(int control, int variable) {
        if(variable > control) {
            return variable;
        }
        else {
            return control;
        }
    }

    public static int MenorInt(int control, int variable) {
        if(variable < control) {
            return variable;
        }
        else {
            return control;
        }
    }
    
    
    public static void main(String[] args) {
        int Inteiro_1 = MyIO.readInt("Type the interger value: ");
        int Inteiro_2 = MyIO.readInt("Type the second interger value: ");
        int Inteiro_3 = MyIO.readInt("Type the third interger value: ");
        int Aux = MaiorInt(Inteiro_1, Inteiro_2);
        int maior = MaiorInt(Aux, Inteiro_3);
        int Aux_2 = MenorInt(Inteiro_1, Inteiro_2);
        int menor = MenorInt(Aux_2, Inteiro_3);
        System.out.println("The interger with the lowest value is: " + menor);
        System.out.println("The interger with the highest value is: " + maior );
    }


}

