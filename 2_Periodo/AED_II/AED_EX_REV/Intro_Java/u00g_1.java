import mypackage.MyIO;

class u00g_1 {
    public static void main(String[] args){
        float lado_1 = MyIO.readFloat("Digite o lado 1 ");
        float lado_2 = MyIO.readFloat("Digite o lado 2 ");
        float lado_3 = MyIO.readFloat("Digite o lado 3 ");

        if(lado_1 == lado_2 && lado_2 == lado_3){
            MyIO.println("Equilatero");
        }else if(lado_1 == lado_2 || lado_1 == lado_3){
            MyIO.println("Isoceles");           
        }else{
            MyIO.println("Escaleno");

        }
    }
}