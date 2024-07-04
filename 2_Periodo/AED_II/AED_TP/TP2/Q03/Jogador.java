import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

class Jogador {
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
    
    public static class Metricas {
        long tempoExecucao;
        long comparacoes;
        long movimentacoes;
    
        public void incrementaComparacoes() {
            this.comparacoes++;
        }
    
        public void incrementaMovimentacoes() {
            this.movimentacoes++;
        }
    
    }

    public static void registroDeLog(String matricula, Metricas metricas) {
    try (FileWriter fw = new FileWriter(matricula + "_sequencial.txt");
         PrintWriter pw = new PrintWriter(fw)) {
        pw.println(matricula + "\t" + metricas.tempoExecucao + "\t" + metricas.comparacoes + "\t" + metricas.movimentacoes);
    } catch (IOException e) {
        e.printStackTrace();
    }
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

    
    // ...
    
    public static void main(String[] args) {
    Metricas metricas = new Metricas();
    long tempoInicial = System.currentTimeMillis();

    String nomeArquivo = "/tmp/players.csv"; 
    HashMap<Integer, Jogador> jogadoresPorId = new HashMap<>();
    int[] ID = new int[3923];
    int i = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
        br.readLine(); // Ignora a primeira linha (cabeçalho)

        String linha;
        while ((linha = br.readLine()) != null) {
            Jogador jogador = new Jogador();
            jogador.ler(br, linha);
            jogadoresPorId.put(jogador.getId(), jogador);
        }

        BufferedReader entradaPadrao = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String entrada = entradaPadrao.readLine();

            if (entrada.equalsIgnoreCase("FIM")) {
                break;
            }

            int idProcurado = Integer.parseInt(entrada);
            ID[i] = idProcurado;
            i++;
        }

        while (true) {
            metricas.incrementaComparacoes(); // Incrementa a cada comparação
            String nomeProcurado = entradaPadrao.readLine();

            if (nomeProcurado.equalsIgnoreCase("FIM")) {
                break;
            }

            boolean encontrado = false;
            for (Jogador jogador : jogadoresPorId.values()) {
                if (jogador.getNome().equals(nomeProcurado)) {
                    for (int j = 0; j < i; j++) {
                        if (jogador.getId() == ID[j]) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (encontrado) break;
                }
            }

            System.out.println(encontrado ? "SIM" : "NAO");
        }

        long tempoFinal = System.currentTimeMillis();
        metricas.tempoExecucao = tempoFinal - tempoInicial;

        registroDeLog("729577", metricas); // Grava as métricas no arquivo de log
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}