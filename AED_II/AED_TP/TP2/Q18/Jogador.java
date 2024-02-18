
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class Jogador {
    private int id;
    private String nome;
    private int altura;
    private int peso;
    private String universidade;
    private int anoNascimento;
    private String cidadeNascimento;
    private String estadoNascimento;

    public Jogador(int id, String nome, int altura, int peso, String universidade, int anoNascimento, String cidadeNascimento, String estadoNascimento){
        this.id = id;
        this.nome = nome;
        this.altura = altura;
        this.peso = peso;
        this.universidade = universidade;
        this.anoNascimento = anoNascimento;
        this.cidadeNascimento = cidadeNascimento;
        this.estadoNascimento = estadoNascimento;
    }

    public Jogador(){

    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getNome(){
        return nome;
    }

    public void setAltura(int altura){
        this.altura = altura;
    }

    public int getAltura(){
        return altura;
    }

    public void setPeso(int peso){
        this.peso = peso;
    }

    public int getPeso(){
        return peso;
    }

    public void setUniversidade(String universidade){
        this.universidade = universidade;
    }

    public String getUniversidade(){
        return universidade;
    }

    public void setAnoNascimento(int anoNascimento){
        this.anoNascimento = anoNascimento;
    }

    public int getAnoNascimento(){
        return anoNascimento;
    }

    public void setCidadeNascimento(String cidadeNascimento){
        this.cidadeNascimento = cidadeNascimento;
    }

    public String getCidadeNascimento(){
        return cidadeNascimento;
    }

    public void setEstadoNascimento(String estadoNascimento){
        this.estadoNascimento = estadoNascimento;
    }

    public String getEstadoNascimento(){
        return estadoNascimento;
    }

    public Jogador clone(){
        Jogador novoJogador = new Jogador(id, nome, altura, peso, universidade, anoNascimento, cidadeNascimento, estadoNascimento);
        return novoJogador;
    }

    public void imprimir(){
        System.out.println("[" + this.id + " ## " + this.nome + " ## " + this.altura + " ## " + this.peso + " ## " + this.anoNascimento + " ## " + this.universidade + " ## " + this.cidadeNascimento + " ## " + this.estadoNascimento + "]" );
    }
    

    public void ler(BufferedReader br, String linha) throws IOException {
        if (linha != null && !linha.equals("FIM")) {
            String[] valores = linha.split(",", -1);

            // Verifica se existem valores suficientes no array antes de acessá-los
            if (valores.length >= 8) {
                // Certifique-se de tratar exceções ao fazer parsing dos valores
                try {
                    this.id = Integer.parseInt(valores[0]);
                    this.nome = (valores[1].isEmpty()) ? "nao informado" : valores[1];
                    this.altura = Integer.parseInt(valores[2]);
                    this.peso = Integer.parseInt(valores[3]);
                    this.universidade = (valores[4].isEmpty()) ? "nao informado" : valores[4];
                    this.anoNascimento = Integer.parseInt(valores[5]);
                    this.cidadeNascimento = (valores[6].isEmpty()) ? "nao informado" : valores[6];
                    this.estadoNascimento = (valores[7].isEmpty()) ? "nao informado" : valores[7];
                } catch (NumberFormatException e) {
                    
                }
            }
        }
    }

    private static int partition(int low, int high, Jogador[] jogadores) {
        Jogador pivot = jogadores[low];
        int left = low + 1;
        int right = high;
        while (left <= right) {
            // Verifique se jogadores[left] não é nulo antes de acessá-lo
            while (left <= right && jogadores[left] != null && (jogadores[left].getEstadoNascimento().compareTo(pivot.getEstadoNascimento()) < 0 ||
                                        (jogadores[left].getEstadoNascimento().equals(pivot.getEstadoNascimento()) &&
                                        jogadores[left].getNome().compareTo(pivot.getNome()) < 0))) {
                left++;
            }
        
            // Verifique se jogadores[right] não é nulo antes de acessá-lo
            while (left <= right && jogadores[right] != null && (jogadores[right].getEstadoNascimento().compareTo(pivot.getEstadoNascimento()) > 0 ||
                                        (jogadores[right].getEstadoNascimento().equals(pivot.getEstadoNascimento()) &&
                                        jogadores[right].getNome().compareTo(pivot.getNome()) > 0))) {
                right--;
            }
        
            if (left <= right) {
                swap(left, right, jogadores);
            } else {
                break;
            }
        }
        
    
        swap(low, right, jogadores);
        return right;
    }
    
    // 2. Função de Quicksort permanece a mesma:
    private static void quickSortUntil10th(int low, int high, Jogador[] jogadores) {
        if (low < high) {
            int pivotIndex = partition(low, high, jogadores);
    
            if (pivotIndex == 9) {
                return;  // Se o pivot está na 10ª posição, pare a ordenação.
            } else if (pivotIndex < 9) {
                quickSortUntil10th(pivotIndex + 1, high, jogadores);
            } else {
                quickSortUntil10th(low, pivotIndex - 1, jogadores);
            }
        }
    }
    

    private static void swap(int index1, int index2, Jogador[] jogadores) {
            Jogador temp = jogadores[index1];
            jogadores[index1] = jogadores[index2];
            jogadores[index2] = temp;
    }
    // ...
    
    public static void main(String[] args) {
        String nomeArquivo = "/tmp/players.csv"; 
        HashMap<Integer, Jogador> jogadoresPorId = new HashMap<>();
        int[] ID = new int[3923];
        int a = 0;
        Jogador[] jogadores = new Jogador[3923];

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            br.readLine(); // Ignora a primeira linha (cabeçalho)

            // Armazena todos os jogadores no HashMap
            String linha;
            while ((linha = br.readLine()) != null) {
                Jogador jogador = new Jogador();
                jogador.ler(br, linha);
                jogadoresPorId.put(jogador.getId(), jogador);
            }

            BufferedReader entradaPadrao = new BufferedReader(new InputStreamReader(System.in));

        // Ler IDs da entrada padrão
        while (true) {
            try {
                String entrada = entradaPadrao.readLine();

                // Using Objects.equals() to prevent NullPointerException
                if ("FIM".equals(entrada)) {
                    break;
                }

                int idProcurado = Integer.parseInt(entrada);
                ID[a] = idProcurado;
                a++;

            } catch (NumberFormatException e) {
                // Handle exception as per your requirement
            }
        }

        for (int j = 0; j < a; j++) {
            jogadores[j] = jogadoresPorId.get(ID[j]);
        }
        
        } catch (IOException e) {
            
        }

        

        quickSortUntil10th(0, jogadores.length - 1, jogadores);

        for (int i = 0; i < 10; i++){
            if (jogadores[i] != null) {
                jogadores[i].imprimir();
            }
        }

    }
}


