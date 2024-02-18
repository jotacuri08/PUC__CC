import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileWriter;

class Celula {
    Jogador jogador;
    Celula prox;
    Celula ant;

    public Celula() {
        this(null);
    }

    public Celula(Jogador jogador) {
        this.jogador = jogador;
        this.prox = this.ant = null;
    }

}

class Lista {
    Celula primeiro, ultimo;
    static int comparacoes = 0;
    static int movimentacoes = 0;
    public Lista() {
        this.primeiro = new Celula();
        this.ultimo = this.primeiro;
    }

    public void inserirInicio(Jogador jogador) {
        Celula tmp = new Celula(jogador);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if(primeiro == ultimo) {
            ultimo = tmp;
        }
        else {
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    public void inserirFinal(Jogador jogador) {
        ultimo.prox = new Celula(jogador);
        ultimo.prox.ant = ultimo;
        ultimo = ultimo.prox;
    }

    public Jogador removerInicio() throws Exception{
        if(primeiro == ultimo) {
            throw new Exception("Erro: Lista Vazia");
        }

        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        Jogador resp = primeiro.jogador;
        tmp.prox = primeiro.ant = null;
        tmp = null;
        return resp;
    }

    public Jogador removerFinal() throws Exception {
        if(primeiro == ultimo) {
            throw new Exception("Erro: lista vazia");
        }

        Jogador resp = ultimo.jogador;
        ultimo = ultimo.ant;
        ultimo.prox.ant = ultimo.prox = null;
        return resp;
    }

    public void inserir(Jogador jogador, int pos) throws Exception{
        int n = tamanho();
        if(pos < 0 || pos > n) {
            throw new Exception("Erro: Posição invalida");
        }
        else if(pos == 0) {
            inserirInicio(jogador);
        }
        else if(pos == n) {
            inserirFinal(jogador);
        }
        else {
            Celula i = primeiro;
            for(int j = 0; j < pos; j++, i = i.prox);
            Celula tmp = new Celula(jogador);
            tmp.ant = i;
            tmp.prox = i.prox;
            tmp.ant.prox = tmp.prox.ant = tmp;
            tmp = i = null;
        }
    }

    public Jogador remover(int pos) throws Exception{
        int n = tamanho();
        Jogador resp;
        if(ultimo == primeiro) {
            throw new Exception("Erro: Lista vazia");
        }
        else if(pos < 0 || pos > n - 1) {
            throw new Exception("Erro: posicao invalida");
        }
        else if(pos == 0) {
            resp = removerInicio();
        }
        else if(pos == n - 1) {
            resp = removerFinal();
        }
        else {
            Celula i = primeiro.prox;
            for(int j = 0; j < pos; j++, i = i.prox);
            resp = i.jogador;
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            i.prox = i.ant = null;
            i = null;
        }
        return resp;
    }

    public Jogador encontrarPivo(Celula esq, Celula dir) {
        Celula i, j;
        for(i = esq, j = dir; i != j && i.prox != j; i = i.prox, j = j.ant);
        Jogador resp = i.jogador;
        return resp;
    }

    public void quickSort(Celula esq, Celula dir) {
        Celula i = esq, j = dir;
        Jogador pivo = encontrarPivo(esq, dir);

        while(i != j.prox && j.prox != i.ant ) {
            while(true) {
                comparacoes += 2;
                int compEstado = i.jogador.getEstadoNascimento().compareTo(pivo.getEstadoNascimento());
                int compNome = i.jogador.getNome().compareTo(pivo.getNome());
                if(compEstado < 0) {
                    i = i.prox;
                    comparacoes--;
                }
                else if(compEstado == 0 && compNome < 0) {
                    i = i.prox;
                } 
                else {
                    break;
                }
            }

            while(true) {
                comparacoes += 2;
                int compEstado = j.jogador.getEstadoNascimento().compareTo(pivo.getEstadoNascimento());
                int compNome = j.jogador.getNome().compareTo(pivo.getNome());
                if(compEstado > 0) {
                    comparacoes--;
                    j = j.ant;
                }
                else if(compEstado == 0 && compNome > 0) {
                    j = j.ant;
                } 
                else {
                    break;
                }
            }

            if(i != j.prox) {
                movimentacoes += 2;
                Jogador tmp = i.jogador;
                i.jogador = j.jogador;
                j.jogador = tmp;
                i = i.prox;
                j = j.ant;
            }
        }

        if(esq != j.prox && esq != j) quickSort(esq, j);
        if(i != dir.prox && i != dir) quickSort(i, dir);
    }

    public void mostrar() {
        Celula i;
        for(i = primeiro.prox; i != null; i = i.prox) {
            MyIO.println("[" + i.jogador.getId() + " ## " + i.jogador.getNome() + " ## " + i.jogador.getAltura() 
            + " ## " + i.jogador.getPeso() + " ## " + i.jogador.getAnoNascimento() + " ## " + i.jogador.getUniversidade() 
            + " ## " + i.jogador.getCidadeNascimento() + " ## " + i.jogador.getEstadoNascimento() + "]");
        }
    }

    // Método para criar o arquivo de registro de log.
    public void registroDeLog(String matricula, long tempoExecucao) {
        try (FileWriter fw = new FileWriter(matricula + "_quicksort2_java.txt");
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(matricula + "\t" + comparacoes + "\t" + movimentacoes + "\t" + tempoExecucao);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int tamanho() {
        int tamanho = 0;
        for(Celula i = primeiro.prox; i != null; i = i.prox, tamanho++);
        return tamanho;
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
        Lista lista = new Lista();
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
                lista.inserirFinal(jogadoresPorId.get(ID[j]));;
            } catch (Exception e) {

            }
        }
       
        long tempoInicio = System.currentTimeMillis();
        lista.quickSort(lista.primeiro.prox, lista.ultimo);
        long tempoFinal = System.currentTimeMillis();
        long tempoTotal = tempoFinal - tempoInicio;
        lista.mostrar();
        lista.registroDeLog("729577", tempoTotal);

        
        } catch (IOException e) {
            
        }
    }
}
