import java.util.List;

public interface ExtratorDeConteudo<T> {

    List<T> extraiConteudos(String json);

}
