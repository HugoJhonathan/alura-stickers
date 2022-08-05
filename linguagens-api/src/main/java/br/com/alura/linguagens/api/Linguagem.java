package br.com.alura.linguagens.api;


import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "principaisLinguagens")
public class Linguagem {

    @Id
    private String id;
    private String title;
    private String image;
    private Integer ranking;

    public Linguagem(){

    }

    public Linguagem(String title, String image, Integer ranking) {
        this.title = title;
        this.image = image;
        this.ranking = ranking;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getRanking() {
        return ranking.toString();
    }

    public void incrementar(){
        this.ranking++;
    }

}
