import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Config {
    IMDB(1, "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060", new ExtratorDeConteudoIMDB()),
    NASA(2, "http://localhost:8080/linguagens", new ExtratorDeConteudoLinguagem());

    private int i;
    private String s;
    private ExtratorDeConteudo extratorDeConteudo;

    public static Config getEnum(Integer id){
        for(Config config : Config.values()){
            if(id.equals(config.getI())){
                return config;
            }
        }
        throw new IllegalArgumentException("Enum "+id+" não existe!");
    }

}
