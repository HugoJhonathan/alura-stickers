import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoLinguagem implements ExtratorDeConteudo<ConteudoNASA>{

    public List<ConteudoNASA> extraiConteudos(String json){

        var parser = new JsonParser();

        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<ConteudoNASA> conteudos = new ArrayList<>();

        // popular a lista de conteudos
        for (Map<String, String> atributos: listaDeAtributos) {
            String titulo = atributos.get("title");
            String urlImagem = atributos.get("image");
            String ranking = atributos.get("ranking");

            var conteudo = new ConteudoNASA(titulo, urlImagem, ranking);

            conteudos.add(conteudo);
        }

        return conteudos;

    }

}
