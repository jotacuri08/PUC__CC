import mypackage.MyIO;

public class u00g_20 {
    
    public static void main(String[] args) {

        int[] array1 = {2, 6, 4, 10};
        int[] array2 = {1, 3, 5, 6, 9, 13};
        int[] array_tudo = new int[10];
        int[] array_uniao = new int[10];
        int[] array_inter = new int[10];
        
        for (int i = 0; i < 4; i++) {
            array_inter[i] = 0;
            for (int j = 0; j < 6; j++) {
                if (array1[i] == array2[j]) {
                    array_inter[i] = array2[j];
                    break;
                }
            }
        }
        
        for (int i = 0; i < 4; i++) {
            array_tudo[i] = array1[i];
        }
        for (int i = 0; i < 6; i++) {
            array_tudo[i + 4] = array2[i];
        }
        
        int uniaoIndex = 0;
        for (int i = 0; i < 10; i++) {
            boolean isIntersected = false;
            for (int j = 0; j < 4; j++) {
                if (array_tudo[i] == array_inter[j]) {
                    isIntersected = true;
                    break;
                }
            }
            if (!isIntersected) {
                array_uniao[uniaoIndex] = array_tudo[i];
                uniaoIndex++;
            }
        }
        
        for (int i = 0; i < uniaoIndex; i++) {
            if (array_uniao[i] != 0) {
                MyIO.println(array_uniao[i]);
            }
        }
    }
}
