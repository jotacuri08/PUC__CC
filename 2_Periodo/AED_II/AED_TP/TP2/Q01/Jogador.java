import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

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
                    System.err.println("Erro ao fazer parsing dos valores do jogador: " + e.getMessage());
                }
            }
        }
    }

    // ...
    
    public static void main(String[] args) {
        String nomeArquivo = "/tmp/players.csv"; // Nome do arquivo CSV
        String linha;
        Jogador[] jogadores = new Jogador[3921];
        int cont = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            br.readLine(); // Ignora a primeira linha (cabeçalho)

            BufferedReader entradaPadrao = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String entrada = entradaPadrao.readLine();

                if (entrada.equalsIgnoreCase("FIM")) {
                    break; // Sai do loop quando o usuário digitar "FIM"
                }

                try {
                    int idProcurado = Integer.parseInt(entrada);
                    boolean jogadorEncontrado = false;

                    // Agora, criar um novo BufferedReader para ler as linhas do arquivo CSV
                    BufferedReader brCSV = new BufferedReader(new FileReader(nomeArquivo));

                    // Ignorar a primeira linha (cabeçalho)
                    brCSV.readLine();

                    // Procurar o jogador com o ID no arquivo CSV
                    while ((linha = brCSV.readLine()) != null) {
                        Jogador jogador = new Jogador();
                        jogador.ler(brCSV, linha);

                        if (jogador.getId() == idProcurado) {
                            jogador.imprimir();
                            jogadorEncontrado = true;
                            break; // Encontrou o jogador, sai do loop de busca
                        }
                    }

                    brCSV.close(); // Fechar o BufferedReader do arquivo CSV
                } catch (NumberFormatException e) {
                    System.err.println("Digite um ID válido ou 'FIM' para sair.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
