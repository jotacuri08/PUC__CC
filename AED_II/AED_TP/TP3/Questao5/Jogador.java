import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

class Celula{
    public Jogador jogador;
    public Celula prox;
    public Celula(){
        this(null);
    }

    public Celula (Jogador jogador){
        this.jogador = jogador;
        this.prox = null;
    }
    
}

class Lista {
    private Celula primeiro,ultimo;
    public Lista() {
        primeiro = new Celula();
        ultimo = primeiro;
    }

    public void inserirFim(Jogador jogador) throws Exception {
        ultimo.prox = new Celula(jogador);
        ultimo = ultimo.prox;
    }

    public void inserirInicio(Jogador jogador) throws Exception {
        Celula tmp = new Celula(jogador);
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;
        if(primeiro == ultimo){
            ultimo = tmp;
        }
        tmp = null;

    }

    public void inserir(Jogador jogador, int pos) throws Exception {
        int tamanho = tamanho();
        if (pos < 0 || pos > tamanho){ 
            throw new Exception();
        } else if (pos == 0){ 
            inserirInicio(jogador);
        } else if (pos == tamanho){ 
            inserirFim(jogador);
        } else {
        Celula i = primeiro;
        for(int j = 0; j < pos; j++, i = i.prox); 
        Celula tmp = new Celula(jogador);
        tmp.prox = i.prox;
        i.prox = tmp;
        tmp = i = null;
        }
    }

    public Jogador removerInicio() throws Exception {
        if(primeiro == ultimo)
            throw new Exception();
        
        Celula tmp = primeiro;
        primeiro = primeiro.prox;
        Jogador jogador = primeiro.jogador;
        tmp.prox = null;
        tmp = null;
        return jogador;
    }

    public Jogador remover(int pos) throws Exception {
        int tamanho = tamanho();
        Jogador jogador;
        if (primeiro == ultimo || pos < 0 || pos >= tamanho){ throw new Exception();
        } else if (pos == 0) { jogador = removerInicio();
        } else if (pos == tamanho - 1){ jogador = removerFim();
        } else {
        Celula i = primeiro;
        for(int j = 0; j < pos; j++, i = i.prox);
        Celula tmp = i.prox;
        jogador = tmp.jogador; i.prox = tmp.prox;
        tmp.prox = null; i = tmp = null;
        }
        return jogador;
    }

    public Jogador removerFim() throws Exception {
    if (primeiro == ultimo)
        throw new Exception();
    Celula i;
    for(i = primeiro; i.prox != ultimo; i = i.prox);
    Jogador jogador = ultimo.jogador;
    ultimo = i;
    i = ultimo.prox = null;
    return jogador;
    }

    public int tamanho() {
      int tamanho = 0; 
      for(Celula i = primeiro; i != ultimo; i = i.prox, tamanho++);
      return tamanho;
   }

    public void imprimir() {
    int posicao = 0;
        for (Celula i = primeiro.prox; i != null; i = i.prox) {
        if (i.jogador != null) {
            i.jogador.imprimirJogador(posicao);
        }
        posicao++;
    }

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

    public void imprimirJogador(int pos){
        System.out.println("[" + pos + "]" + " ## " + this.nome + " ## " + this.altura + " ## " + this.peso + " ## " + this.anoNascimento + " ## " + this.universidade + " ## " + this.cidadeNascimento + " ## " + this.estadoNascimento + " ##");
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
        Lista listaJogadores = new Lista();
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
                listaJogadores.inserirFim(jogadoresPorId.get(ID[j]));
            } catch (Exception e) {

            }
        }
       
        String entrada = entradaPadrao.readLine();
        int numRegistros = 0;
        if (entrada != null && !entrada.isEmpty()) {
            try {
                numRegistros = Integer.parseInt(entrada);
                // Processamento adicional
            } catch (NumberFormatException e) {
            }
        }
        for (int i = 0; i < numRegistros; i++){
                String entrada3 = entradaPadrao.readLine();
                if (entrada3 != null && !entrada3.isEmpty()){
                    if(entrada3.charAt(0) == 'I'){
                        if(entrada3.charAt(1) == 'I'){
                            String numeros = entrada3.replaceAll("[^\\d]", ""); // Remove tudo que não é dígito
                            int id = Integer.parseInt(numeros);
                            try{
                            listaJogadores.inserirInicio(jogadoresPorId.get(id));
                            }catch (Exception e) {}
                        }else if(entrada3.charAt(1) == 'F'){
                            String numeros = entrada3.replaceAll("[^\\d]", ""); // Remove tudo que não é dígito
                            int id = Integer.parseInt(numeros);
                            try{
                            listaJogadores.inserirFim(jogadoresPorId.get(id));
                            }catch (Exception e) {}
                        }else if(entrada3.charAt(1) == '*'){
                            String[] partes = entrada3.split("\\s+"); // Divide a string com base em um ou mais espaços
                            int posicao = Integer.parseInt(partes[1]);
                            int id = Integer.parseInt(partes[2]);
                            try{
                            listaJogadores.inserir(jogadoresPorId.get(id), posicao);
                            }catch (Exception e) {}
                        }
                    }else if(entrada3.charAt(0) == 'R'){
                        if(entrada3.charAt(1) == 'I'){
                            try{
                            Jogador removido = listaJogadores.removerInicio();
                            System.out.println("(R) " + removido.nome);
                            }catch (Exception e) {}
                        }else if(entrada3.charAt(1) == 'F'){
                            try{
                            Jogador removido = listaJogadores.removerFim();
                            System.out.println("(R) " + removido.nome);
                            }catch (Exception e) {}
                        }else if(entrada3.charAt(1) == '*'){
                            String numeros = entrada3.replaceAll("[^\\d]", ""); // Remove tudo que não é dígito
                            int posicao = Integer.parseInt(numeros);
                            try{
                            Jogador removido = listaJogadores.remover(posicao);
                            System.out.println("(R) " + removido.nome);
                            }catch (Exception e) {}
                        }
                    }
                }
        }
       
        
        
        listaJogadores.imprimir();


        } catch (IOException e) {
            
        }
    }
}
