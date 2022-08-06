import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoIMDB implements ExtratorDeConteudo<ConteudoIMDB>{

    public List<ConteudoIMDB> extraiConteudos(String json){

        var parser = new JsonParser();

        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<ConteudoIMDB> conteudos  = new ArrayList<>();

        // popular a lista de conteudos
        for (Map<String, String> atributos: listaDeAtributos) {
            String titulo = atributos.get("title");
            String urlImagem = atributos.get("image");
            String imdbRating = atributos.get("imDbRating");
            String rank = atributos.get("rank");
            String urlPosterAltaQualidade = urlImagem.replaceAll(".\\w{13}\\,\\d\\,\\w{3}\\,\\w{7}","");
            var conteudo = new ConteudoIMDB(titulo, urlPosterAltaQualidade, imdbRating, rank);

            conteudos.add(conteudo);
        }

        return conteudos;

    }

}
