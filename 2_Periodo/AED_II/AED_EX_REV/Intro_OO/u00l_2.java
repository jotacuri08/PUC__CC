package AED_EX_REV;

public class u00l_2 {
    class Retangulo{
        private double altura;
        private double base;

        public
        Retangulo(){
            altura = 0;
            base = 0;
        }
        Retangulo(double alt, double b){
            altura = alt;
            base = b;
        }

        double get_area(){
            return base*altura;
        }

        double get_perimetro(){
            return (base*2) + (altura*2);
        }

        double get_diagonal(){
            return Math.sqrt((base*base)+(altura*altura));
        }
    }

    class Lixao{
        public static void main(String[] args){
            Retangulo r1;
            Retangulo r2;
            r1 = new Retangulo(10,20);
            r2 = new Retangulo(20,30);
            r1.get_area();
            r2.get_area();
            r1.get_perimetro();
            r2.get_perimetro();
            r1.get_diagonal();
            r2.get_diagonal();

        } 
    }
}