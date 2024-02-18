import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;
import java.io.BufferedWriter;
import java.io.FileWriter;


class No{
    Jogador jogador;
    No esq;
    No dir;

    No(Jogador jogador){
        this(jogador, null, null);
    }

    No(Jogador jogador, No esq, No dir){
        this.jogador = jogador;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    No raiz;
    int numComparacoes = 0;
    ArvoreBinaria(){
        raiz = null;
    }

    void inserir(Jogador jogador) throws Exception{
        raiz = inserir(jogador, raiz);
    }

    No inserir(Jogador jogador, No i) throws Exception{
        numComparacoes++;
        if(i == null){
            i = new No(jogador);
        }
        else if(jogador.getNome().compareTo(i.jogador.getNome()) < 0){
            i.esq = inserir(jogador, i.esq);
        }
        else if(jogador.getNome().compareTo(i.jogador.getNome()) > 0){
            i.dir = inserir(jogador, i.dir);
        }
        else {
            throw new Exception();
        }
        return i;
    }

    boolean pesquisa(String name){
        System.out.print(name);
        System.out.print(" raiz ");
        boolean resp = pesquisa(name, raiz);
        if(resp == true)
        System.out.println("SIM");
        else
        System.out.println("NAO");
        return resp;
    }

    boolean pesquisa(String name, No i){
        numComparacoes++;
        boolean resp = false;
        if(i == null){
            return resp;
        }
        else if(name.compareTo(i.jogador.getNome()) > 0){
            System.out.print("dir ");
            resp = pesquisa(name, i.dir);
        }
        else if(name.compareTo(i.jogador.getNome()) < 0){
            System.out.print("esq ");
            resp = pesquisa(name, i.esq);
        }
        else if(name.compareTo(i.jogador.getNome()) == 0){
            resp = true;
        }
        return resp;
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
        long startTime = System.currentTimeMillis();
        String nomeArquivo = "/tmp/players.csv"; 
        HashMap<Integer, Jogador> jogadoresPorId = new HashMap<>();
        ArvoreBinaria arvoreJogadores = new ArvoreBinaria();
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
            try{
                arvoreJogadores.inserir(jogadoresPorId.get(ID[j]));
            } catch (Exception e) {

            }
        }
       
        while(true){    
            String entrada = entradaPadrao.readLine();
            if ("FIM".equals(entrada)) {
                    break;
            }
            boolean resp = arvoreJogadores.pesquisa(entrada);
        }
        } catch (IOException e) {
            
        }
        long endTime = System.currentTimeMillis();
        long execTime = endTime - startTime;
        escreverLog("729577_arvoreBinaria.txt", "729577", execTime, arvoreJogadores.numComparacoes);
        
    }

    static void escreverLog(String nomeArquivo, String matricula, long tempoExecucao, int numComparacoes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeArquivo))) {
            bw.write(matricula + "\t" + tempoExecucao + "\t" + numComparacoes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
