import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

public class App {

    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42;1m";
    public static  String ANSI_BACKGROUND_TABLE = "";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RESET_ALL = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLD = "\u001b[1m";
    public static final String SPACING = "    ";

    public static void main(String[] args) throws Exception {

        String apiKey = System.getenv("API_KEY");

        Scanner sc = new Scanner(System.in);

        System.out.println("\n"+ANSI_BOLD+"Qual API gerar?"+ANSI_RESET+
                "\n1 - Filmes" +
                "\n2 - Linguagens");
        Integer escolha = sc.nextInt();

        String url = Config.getEnum(escolha).getS();

        ExtratorDeConteudo extrator = Config.getEnum(escolha).getExtratorDeConteudo();

        String json = new ClienteHttp().buscaDados(url);

        PrintStream out = new PrintStream(System.out, true, UTF_8);

        if(escolha == 1){

            List<ConteudoIMDB> conteudos = extrator.extraiConteudos(json);

            var geradora = new GeradoraDeFigurinhasIMDB();
            for (int i = 0; i < conteudos.size(); i++) {

                var conteudo = conteudos.get(i);

                ANSI_BACKGROUND_TABLE = i % 2 == 0 ? "\u200B\u001b[48;2;43;43;43m" : "\u200B\u001b[48;2;60;63;65m";
                ANSI_RESET = "\u001B[0m" + ANSI_BACKGROUND_TABLE;

                System.out.println(ANSI_BACKGROUND_TABLE);

            double rating = Double.parseDouble(conteudo.imdbRating());

            var estrelas = new EstrelasDeClassificacao().gerarString(rating);

            out.println(SPACING+"Rank  : #"+ANSI_BOLD+conteudo.rank()+ANSI_RESET);
                out.println(SPACING+"Title : "+ANSI_BOLD+conteudo.titulo()+ANSI_RESET);
                out.println(SPACING+"Poster: "+ANSI_BOLD+conteudo.urlImagem()+ANSI_RESET);
            out.println(SPACING+ANSI_BLACK+ANSI_GREEN_BACKGROUND+" Rating: \u001b[1m"+conteudo.imdbRating()+"  "+ANSI_RESET);
            out.println(SPACING+estrelas);
                out.println();

                InputStream inputStream;

                try {
                    inputStream = new URL(conteudo.urlImagem()).openStream();
                }catch (Exception e) {
                    // default cover
                    System.out.println("\u001b[31;1m Não foi possível ler esta imagem");
                    inputStream = new URL("https://i.imgur.com/VHxP6Zx.png").openStream();
                }

                String nomeArquivo = conteudo.rank()+". "+conteudo.titulo()+".png";
                geradora.cria(inputStream, nomeArquivo,  "RATING: "+conteudo.imdbRating(), conteudo.rank(), Double.parseDouble(conteudo.imdbRating()));
                System.out.print(ANSI_RESET_ALL);

            }
        }else if(escolha == 2){

            List<ConteudoNASA> conteudos = extrator.extraiConteudos(json);

            var geradora = new GeradoraDeFigurinhasLinguagem();

            for (int i = 0; i < conteudos.size(); i++) {

                var conteudo = conteudos.get(i);

                ANSI_BACKGROUND_TABLE = i % 2 == 0 ? "\u200B\u001b[48;2;43;43;43m" : "\u200B\u001b[48;2;60;63;65m";
                ANSI_RESET = "\u001B[0m" + ANSI_BACKGROUND_TABLE;

                System.out.println(ANSI_BACKGROUND_TABLE);

                out.println(SPACING+"Title : "+ANSI_BOLD+conteudo.titulo()+ANSI_RESET);
                out.println(SPACING+"Poster: "+ANSI_BOLD+conteudo.urlImagem()+ANSI_RESET);
                out.println(SPACING+"Ranking: "+ANSI_BOLD+conteudo.ranking()+ANSI_RESET);
                out.println();

                InputStream inputStream;

                try {
                    inputStream = new URL(conteudo.urlImagem()).openStream();
                }catch (Exception e) {
                    // default cover
                    System.out.println("\u001b[31;1m Não foi possível ler esta imagem");
                    inputStream = new URL("https://i.imgur.com/VHxP6Zx.png").openStream();
                }

                String nomeArquivo = "" + conteudo.titulo() + ".png";
                geradora.cria(inputStream, nomeArquivo, conteudo.titulo());
                System.out.print(ANSI_RESET_ALL);

            }
        }
        System.out.println("Finalizado!");
    }
}