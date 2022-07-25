import java.io.InputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

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

        // [1]. Fazer uma conexão HTTP e buscar os tops 250 filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        HttpClient client = HttpClient.newHttpClient();
        URI endereco = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // [2]. Extrair só os dados que interessam (titulo, poster, classificação
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // [3]. Exibir e manipular os dados
        System.out.println();
        int index = 0;

        var geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {

            PrintStream out = new PrintStream(System.out, true, UTF_8);

            ANSI_BACKGROUND_TABLE = index % 2 == 0 ? "\u200B\u001b[48;2;43;43;43m" : "\u200B\u001b[48;2;60;63;65m";
            ANSI_RESET = "\u001B[0m" + ANSI_BACKGROUND_TABLE;
            index++;

            System.out.println(ANSI_BACKGROUND_TABLE);
            double rating = Double.parseDouble(filme.get("imDbRating"));
            String estrelas = "";

            for(int i = 0; i<10; i++){
                if(i < (int) rating){
                    estrelas = estrelas.concat(ANSI_YELLOW+"\u2605"+ANSI_RESET);
                }else{
                    estrelas = estrelas.concat(ANSI_YELLOW+"\u2606"+ANSI_RESET);
                }
            }

            InputStream inputStream;
            String urlPosterAltaQualidade = filme.get("image").replaceAll(".\\w{13}\\,\\d\\,\\w{3}\\,\\w{7}","");

            out.println(SPACING+"Rank  : #"+ANSI_BOLD+filme.get("rank")+ANSI_RESET);
            out.println(SPACING+"Title : "+ANSI_BOLD+filme.get("title")+ANSI_RESET);
            out.println(SPACING+"Poster: "+ANSI_BOLD+urlPosterAltaQualidade+ANSI_RESET);
            out.println(SPACING+ANSI_BLACK+ANSI_GREEN_BACKGROUND+" Rating: \u001b[1m"+filme.get("imDbRating")+"  "+ANSI_RESET);
            out.println(SPACING+estrelas);
            out.println();

            try {
                inputStream = new URL(urlPosterAltaQualidade).openStream();
            }catch (Exception e) {
                // default cover
                inputStream = new URL("https://m.media-amazon.com/images/M/MV5BMGVmMWNiMDktYjQ0Mi00MWIxLTk0N2UtN2ZlYTdkN2IzNDNlXkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_UX128_CR0,3,128,176_AL_.jpg").openStream();
            }
            String nomeArquivo = filme.get("rank")+". "+filme.get("title")+".png";
            geradora.cria(inputStream, nomeArquivo, "RATING: "+filme.get("imDbRating"), filme.get("rank"), rating);
            System.out.print(ANSI_RESET_ALL);
          }
        System.out.println("Finalizado!");
    }
}
