package Questao4;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

class NoAN{
    Jogador jogador;
    boolean cor;
    NoAN esq;
    NoAN dir;

    NoAN(Jogador jogador){
        this(jogador,false, null, null);
    }

    NoAN(Jogador jogador, boolean cor){
        this(jogador, cor, null, null);
    }

    NoAN(Jogador jogador, boolean cor, NoAN esq, NoAN dir){
        this.jogador = jogador;
        this.cor = cor;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreAlvinegra {
    NoAN raiz;
    ArvoreAlvinegra(){
        raiz = null;
    }


    public void inserir(Jogador jogador) throws Exception {
      // Se a arvore estiver vazia
      if (raiz == null) {
         raiz = new NoAN(jogador);
      // Senao, se a arvore tiver um elemento
      } else if (raiz.esq == null && raiz.dir == null) {
         if (jogador.getNome().compareTo(raiz.jogador.getNome()) < 0) {
            raiz.esq = new NoAN(jogador);
         } else {
            raiz.dir = new NoAN(jogador);
         }

      // Senao, se a arvore tiver dois elementos (raiz e dir)
      } else if (raiz.esq == null) {
         if (jogador.getNome().compareTo(raiz.jogador.getNome()) < 0) {
            raiz.esq = new NoAN(jogador);

         } else if (jogador.getNome().compareTo(raiz.dir.jogador.getNome()) < 0) {
            raiz.esq = new NoAN(raiz.jogador);
            raiz.jogador = jogador;

         } else {
            raiz.esq = new NoAN(raiz.jogador);
            raiz.jogador = raiz.dir.jogador;
            raiz.dir.jogador = jogador;
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, se a arvore tiver dois elementos (raiz e esq)
      } else if (raiz.dir == null) {
         if (jogador.getNome().compareTo(raiz.jogador.getNome()) > 0) {
            raiz.dir = new NoAN(jogador);

         } else if (jogador.getNome().compareTo(raiz.esq.jogador.getNome()) > 0) {
            raiz.dir = new NoAN(raiz.jogador);
            raiz.jogador = jogador;

         } else {
            raiz.dir = new NoAN(raiz.jogador);
            raiz.jogador = raiz.esq.jogador;
            raiz.esq.jogador = jogador;
         }
         raiz.esq.cor = raiz.dir.cor = false;

      // Senao, a arvore tem tres ou mais elementos
      } else {
         inserir(jogador, null, null, null, raiz);
      }
      raiz.cor = false;
   }

   private void inserir(Jogador jogador, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
      if (i == null) {
         if (jogador.getNome().compareTo(raiz.esq.jogador.getNome()) < 0) {
            i = pai.esq = new NoAN(jogador, true);
         } else {
            i = pai.dir = new NoAN(jogador, true);
         }
         if (pai.cor == true) {
            balancear(bisavo, avo, pai, i);
         }
      } else {
         // Achou um 4-no: eh preciso fragmeta-lo e reequilibrar a arvore
         if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
            i.cor = true;
            i.esq.cor = i.dir.cor = false;
            if (i == raiz) {
               i.cor = false;
            } else if (pai.cor == true) {
               balancear(bisavo, avo, pai, i);
            }
         }
         if (jogador.getNome().compareTo(raiz.esq.jogador.getNome()) < 0) {
            inserir(jogador, avo, pai, i, i.esq);
         } else if (jogador.getNome().compareTo(raiz.esq.jogador.getNome())> 0) {
            inserir(jogador, avo, pai, i, i.dir);
         } else {
            throw new Exception("Erro inserir (elemento repetido)!");
         }
      }
   }

   private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
      // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
      if (pai.cor == true) {
         // 4 tipos de reequilibrios e acoplamento
         if (pai.jogador.getNome().compareTo(avo.jogador.getNome()) > 0) { // rotacao a esquerda ou direita-esquerda
            if (i.jogador.getNome().compareTo(pai.jogador.getNome())> 0) {
               avo = rotacaoEsq(avo);
            } else {
               avo = rotacaoDirEsq(avo);
            }
         } else { // rotacao a direita ou esquerda-direita
            if (i.jogador.getNome().compareTo(pai.jogador.getNome()) < 0) {
               avo = rotacaoDir(avo);
            } else {
               avo = rotacaoEsqDir(avo);
            }
         }
         if (bisavo == null) {
            raiz = avo;
         } else if (avo.jogador.getNome().compareTo(bisavo.jogador.getNome()) < 0) {
            bisavo.esq = avo;
         } else {
            bisavo.dir = avo;
         }
         // reestabelecer as cores apos a rotacao
         avo.cor = false;
         avo.esq.cor = avo.dir.cor = true;
      } // if(pai.cor == true)
   }

   private NoAN rotacaoDir(NoAN no) {
      NoAN noEsq = no.esq;
      NoAN noEsqDir = noEsq.dir;

      noEsq.dir = no;
      no.esq = noEsqDir;

      return noEsq;
   }

   private NoAN rotacaoEsq(NoAN no) {
      NoAN noDir = no.dir;
      NoAN noDirEsq = noDir.esq;

      noDir.esq = no;
      no.dir = noDirEsq;
      return noDir;
   }

   private NoAN rotacaoDirEsq(NoAN no) {
      no.dir = rotacaoDir(no.dir);
      return rotacaoEsq(no);
   }

   private NoAN rotacaoEsqDir(NoAN no) {
      no.esq = rotacaoEsq(no.esq);
      return rotacaoDir(no);
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


    boolean pesquisa(String name, NoAN i){
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
        String nomeArquivo = "/tmp/players.csv"; 
        HashMap<Integer, Jogador> jogadoresPorId = new HashMap<>();
        ArvoreAlvinegra arvoreJogadores = new ArvoreAlvinegra();
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
    }
}
