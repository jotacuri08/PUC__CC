import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

class TabelaHash {
    private Jogador[] tabela;
    private Jogador[] reserva;
    private int tamanhoReserva;
    private int tamanhoTabela;

    public TabelaHash(int tamanhoTabela, int tamanhoReserva) {
        this.tabela = new Jogador[tamanhoTabela];
        this.reserva = new Jogador[tamanhoReserva];
        this.tamanhoReserva = tamanhoReserva;
        this.tamanhoTabela = tamanhoTabela;
    }

    private int hash(Jogador jogador) {
        return jogador.getAltura() % tamanhoTabela;
    }

    public void inserir(Jogador jogador) {
        int posicao = hash(jogador);

        if (tabela[posicao] == null) {
            tabela[posicao] = jogador;
        } else {
            // Insere na área de reserva
            for (int i = 0; i < tamanhoReserva; i++) {
                if (reserva[i] == null) {
                    reserva[i] = jogador;
                    return;
                }
            }
        }
    }

    public int pesquisar(String nome) {
        // Pesquisa na tabela hash
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null && tabela[i].getNome().equals(nome)) {
                return i; // Encontrado na tabela hash
            }
        }

        // Pesquisa na área de reserva
        for (int i = 0; i < reserva.length; i++) {
            if (reserva[i] != null && reserva[i].getNome().equals(nome)) {
                return tamanhoTabela + i; // Encontrado na área de reserva
            }
        }

        return -1; // Não encontrado
    }
}


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

    public void imprimirJogador(int index){
        System.out.println("[" + index + "]" + " ## " + this.nome + " ## " + this.altura + " ## " + this.peso + " ## " + this.anoNascimento + " ## " + this.universidade + " ## " + this.cidadeNascimento + " ## " + this.estadoNascimento + " ##");
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

                if ("FIM".equals(entrada)) {
                    break;
                }

                int idProcurado = Integer.parseInt(entrada);
                ID[a] = idProcurado;
                a++;

            } catch (NumberFormatException e) {
                
            }
        }

        TabelaHash tabelaHash = new TabelaHash(21, 9);

    // Inserir jogadores na tabela hash
    for (Jogador jogador : jogadoresPorId.values()) {
        tabelaHash.inserir(jogador);
    }

    while(true){    
        String entrada = entradaPadrao.readLine();
        if ("FIM".equals(entrada)) {
                break;
        }
        int posicao = tabelaHash.pesquisar(entrada);
        if (posicao == -1) {
            System.out.println(entrada + " NAO");
        } else {
            System.out.println(entrada + " SIM");
        }
    }
    
        } catch (IOException e) {
            
        }
    }
}
