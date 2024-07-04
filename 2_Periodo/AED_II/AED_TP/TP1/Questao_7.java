import java.io.*;
import java.net.*;


class Questao_7 {
   public static String getHtml(String endereco){
      URL url;
      InputStream is = null;
      BufferedReader br;
      String resp = "", line;

      try {
         url = new URL(endereco);
         is = url.openStream();  // throws an IOException
         br = new BufferedReader(new InputStreamReader(is));

         while ((line = br.readLine()) != null) {
            resp += line + "\n";
         }
      } catch (MalformedURLException mue) {
         mue.printStackTrace();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      } 

      try {
         is.close();
      } catch (IOException ioe) {
         // nothing to see here

      }

      return resp;
   }

   public static boolean not_Fim(String nomePag){
        boolean notFim = false;
        if(nomePag.charAt(0) != 'F' || nomePag.charAt(1) != 'I' || nomePag.charAt(2) != 'M'){
            notFim = true;
        }
        return notFim;
   }
   public static void main(String[] args) {
      MyIO.setCharset("UTF-8");
      String nomePag, endereco, html;
      boolean notFim = false;
      int cont_a, cont_a_acento, cont_e, cont_e_acento, cont_i, cont_i_acento, cont_o, cont_o_acento, cont_u, cont_u_acento;
      int cont_a_crase, cont_e_crase, cont_i_crase, cont_o_crase, cont_u_crase;
      int cont_a_til, cont_o_til;
      int cont_a_circunflexo, cont_e_circunflexo, cont_i_circunflexo, cont_o_circunflexo, cont_u_circunflexo;
      int cont_consoantes;
      int cont_br, cont_table;
      do{
        cont_a = 0; cont_a_acento = 0; cont_e = 0; cont_e_acento = 0; cont_i = 0; cont_i_acento = 0; cont_o = 0; cont_o_acento = 0; cont_u = 0; cont_u_acento = 0;
        cont_a_crase = 0; cont_e_crase = 0; cont_i_crase = 0; cont_o_crase = 0; cont_u_crase = 0;
        cont_a_til = 0; cont_o_til = 0;
        cont_a_circunflexo = 0; cont_e_circunflexo = 0; cont_i_circunflexo = 0; cont_o_circunflexo = 0; cont_u_circunflexo = 0;
        cont_consoantes = 0;
        cont_br = 0; cont_table = 0;
        nomePag = MyIO.readLine();
        notFim = not_Fim(nomePag);
        if(!notFim){
            break;
        }
        endereco = MyIO.readLine();
        html = getHtml(endereco);
        for(int i = 0; i < html.length(); i++){
            if(html.charAt(i) == 'a'){
                cont_a++;
            }else if(html.charAt(i) == '\u00E1'){
                cont_a_acento++;
            }else if(html.charAt(i) == '\u00E0'){
                cont_a_crase++;
            }else if(html.charAt(i) == '\u00E3'){
                cont_a_til++;
            }else if(html.charAt(i) == '\u00E2'){
                cont_a_circunflexo++;
            }else if(html.charAt(i) == 'e'){
                cont_e++;
            }else if(html.charAt(i) == '\u00E9'){
                cont_e_acento++;
            }else if(html.charAt(i) == '\u00E8'){
                cont_e_crase++;
            }else if(html.charAt(i) == '\u00EA'){
                cont_e_circunflexo++;
            }else if(html.charAt(i) == 'i'){
                cont_i++;
            }else if(html.charAt(i) == '\u00ED'){
                cont_i_acento++;
            }else if(html.charAt(i) == '\u00EC'){
                cont_i_crase++;
            }else if(html.charAt(i) == '\u00EE'){
                cont_i_circunflexo++;
            }else if(html.charAt(i) == 'o'){
                cont_o++;
            }else if(html.charAt(i) == '\u00F3'){
                cont_o_acento++;
            }else if(html.charAt(i) == '\u00F2'){
                cont_o_crase++;
            }else if(html.charAt(i) == '\u00F5'){
                cont_o_til++;
            }else if(html.charAt(i) == '\u00F4'){
                cont_o_circunflexo++;
            }else if(html.charAt(i) == 'u'){
                cont_u++;
            }else if(html.charAt(i) == '\u00FA'){
                cont_u_acento++;
            }else if(html.charAt(i) == '\u00F9'){
                cont_u_crase++;
            }else if(html.charAt(i) == '\u00FB'){
                cont_u_circunflexo++;
            }else if(html.charAt(i) > 'a' && html.charAt(i) <= 'z'){
                cont_consoantes++;
            }else if(html.charAt(i) == '<' && html.charAt(i+1) == 'b' && html.charAt(i+2) == 'r' && html.charAt(i+3) == '>'){
                cont_br++;
                cont_consoantes-=2;
            }else if(html.charAt(i) == '<' && html.charAt(i+1) == 't' && html.charAt(i+2) == 'a' && html.charAt(i+3) == 'b' && html.charAt(i+4) == 'l' && html.charAt(i+5) == 'e' && html.charAt(i+6) == '>'){
                cont_table++;
                cont_consoantes-=3;
                cont_a--;
                cont_e--;
            }
        }
        MyIO.println("a(" + cont_a + ") e(" + cont_e + ") i(" + cont_i + ") o(" + cont_o + ") u(" + cont_u + ") \u00E1(" + cont_a_acento + ") \u00E9(" + cont_e_acento + ") \u00ED(" + cont_i_acento + ") \u00F3(" + cont_o_acento + ") \u00FA(" + cont_u_acento + ") \u00E0(" + cont_a_crase + ") \u00E8(" + cont_e_crase + ") \u00EC(" + cont_i_crase + ") \u00F2(" + cont_o_crase + ") \u00F9(" + cont_u_crase + ") \u00E3(" + cont_a_til + ") \u00F5(" + cont_o_til + ") \u00E2(" + cont_a_circunflexo + ") \u00EA(" + cont_e_circunflexo + ") \u00EE(" + cont_i_circunflexo + ") \u00F4(" + cont_o_circunflexo + ") \u00FB(" + cont_u_circunflexo + ") consoante(" + cont_consoantes + ") <br>(" + cont_br + ") <table>(" + cont_table + ") " + nomePag);
        }while(notFim);
                
   }
}

