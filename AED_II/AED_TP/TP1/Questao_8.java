import java.io.IOException;
import java.io.RandomAccessFile;

public class Questao_8 {
    public static void main(String[] args) {
        try {
            int quant_num = MyIO.readInt();
            double num_digitado;
            RandomAccessFile arq = new RandomAccessFile("Arq.txt", "rw");
            for (int i = 0; i < quant_num; i++) {
                num_digitado = MyIO.readDouble();
                arq.writeDouble(num_digitado);
            }
            long posicao_atual = arq.getFilePointer();
            arq.close();
            arq = new RandomAccessFile("Arq.txt", "r");
            for (long i = posicao_atual - 8; i >= 0; i -= 8) {
                arq.seek(i);
                double real = arq.readDouble();
                if(real == (int)real){
                    MyIO.println((int)real);
                }else{
                    MyIO.println(real);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
