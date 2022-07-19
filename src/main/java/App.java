import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
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
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BOLD = "\u001b[1m";
    public static final String SPACING = "    ";

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Hello, World!");
        String apiKey = System.getenv("API_KEY");

        // [1]. Fazer uma conexão HTTP e buscar os tops 250 filmes
        String url = "https://alura-imdb-api.herokuapp.com/movies";
        var client = HttpClient.newHttpClient();
        var endereco = URI.create(url);
        var request = HttpRequest.newBuilder(endereco).GET().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        var body = response.body();

        // [2]. Extrair só os dados que interessam (titulo, poster, classificação
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);
        
        // [3]. Exibir e manipular os dados
        System.out.println();
        int index = 0;
        for (Map<String, String> filme : listaDeFilmes) {

            PrintStream out = new PrintStream(System.out, true, UTF_8);

            ANSI_BACKGROUND_TABLE = index % 2 == 0 ? "\u200B\u001b[48;2;43;43;43m" : "\u200B\u001b[48;2;60;63;65m";
            index++;

            System.out.println(ANSI_BACKGROUND_TABLE);
            double rating = Double.parseDouble(filme.get("imDbRating"));
            String estrelas = "";

            for(int i = 0; i<10; i++){
                if(i < (int) rating){
                    estrelas = estrelas.concat(ANSI_YELLOW+"\u2605"+ANSI_RESET+ANSI_BACKGROUND_TABLE);
                }else{
                    estrelas = estrelas.concat(ANSI_YELLOW+"\u2606"+ANSI_RESET+ANSI_BACKGROUND_TABLE);
                }
            }

            out.println(SPACING+"Title : "+ANSI_BOLD+filme.get("title")+ANSI_RESET+ANSI_BACKGROUND_TABLE);
            out.println(SPACING+"Poster: "+ANSI_BOLD+filme.get("image")+ANSI_RESET+ANSI_BACKGROUND_TABLE);
            out.println(SPACING+ANSI_BLACK+ANSI_GREEN_BACKGROUND+" Rating: \u001b[1m"+filme.get("imDbRating")+"  "+ANSI_RESET+ANSI_BACKGROUND_TABLE);
            out.println(SPACING+estrelas);
            out.println();
          }

    }
}
