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

    public static void mergesort(int esq, int dir, Jogador[] jogadores) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergesort(esq, meio, jogadores);
            mergesort(meio + 1, dir, jogadores);
            intercalar(esq, meio, dir, jogadores);
        }
    }
    
    public static void intercalar(int esq, int meio, int dir, Jogador[] jogadores) {
        int n1 = meio - esq + 1;
        int n2 = dir - meio;
    
        Jogador[] a1 = new Jogador[n1];
        Jogador[] a2 = new Jogador[n2];
    
        for (int i = 0; i < n1; i++) {
            a1[i] = jogadores[esq + i];
        }
    
        for (int j = 0; j < n2; j++) {
            a2[j] = jogadores[meio + 1 + j];
        }
    
        int i = 0, j = 0;
    
        for (int k = esq; k <= dir; k++) {
            if (i < n1 && (j >= n2 || (a1[i] != null && a1[i].getUniversidade() != null && (a2[j] == null || a1[i].getUniversidade().compareTo(a2[j].getUniversidade()) <= 0)))) {
                jogadores[k] = a1[i++];
            } else {
                jogadores[k] = a2[j++];
            }
        }
    }
    
    
    

    private static void swap(int index1, int index2, Jogador[] jogadores) {
        Jogador temp = jogadores[index1];
        jogadores[index1] = jogadores[index2];
        jogadores[index2] = temp;
}
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

        for (int i = 0; i < (ID.length - 1); i++) {
            int menor = i; 
            for (int j = (i + 1); j < ID.length; j++){
                if(jogadores[menor] != null && jogadores[j] != null){
                    for (int k = 0; k < jogadores[menor].getNome().length() ; k++){
                        if (jogadores[menor].getNome().compareTo(jogadores[j].getNome()) > 0) {
                            menor = j;
                        }
                    }
                }
            }
                swap(menor, i, jogadores);
        }

        mergesort(0, jogadores.length - 1, jogadores);

        for (int i = 0; i < jogadores.length; i++){
            if (jogadores[i] != null) {
                jogadores[i].imprimir();
            }
        }

    }
}


