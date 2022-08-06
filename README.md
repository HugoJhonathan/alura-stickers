<img height="30px" src="https://i.imgur.com/pL1Tneg.png">

# [1] Comsumindo API de Filmes
Nesta primeira aula, construimos uma aplicação do zero para consumir a API do IMDb e exibir os filmes mais populares, destacando seus pôsteres e visualizando sua classificação... Tudo isso sem usar nenhuma biblioteca externa!

![](https://i.imgur.com/9pkCyp5.png)

# [2] Gerando figurinhas para WhatsApp
Nesta segunda aula criamos um gerador de figurinhas explorando outras bibliotecas nativas do Java, para que possamos enviar por Whatsapp os nossos filmes preferidos!

![](https://i.imgur.com/3lbPcEU.png)


# [3] Ligando as pontas, Refatoração e Orientação a Objetos
Chegou o momento de pegarmos os filmes do IMDb e gerar figurinhas com os pôsteres, aproveitando para melhorar nosso código com as refatorações necessárias para torná-lo mais flexível e fácil de entender.

# [4] Criando nossa própria API com Spring
Agora vamos construir uma API REST para expor nosso próprio conteúdo, utilizando ferramentas profissionais como o Spring Framework e um banco de dados NoSQL (MongoDB).

[Projeto da API](https://github.com/HugoJhonathan/alura-stickers/tree/api)

| Method | URL | Result |
|--------|------|-------|
|**[GET](https://github.com/HugoJhonathan/alura-stickers/blob/api/linguagens-api/src/main/java/br/com/alura/linguagens/api/LinguagemController.java#L23)**     | `http://localhost:8080/linguagens`      | Lista todas as Linguagens ordenadas pelo **`ranking`**. |
|**[POST](https://github.com/HugoJhonathan/alura-stickers/blob/api/linguagens-api/src/main/java/br/com/alura/linguagens/api/LinguagemController.java#L29)**    | `http://localhost:8080/linguagens`      | Cria uma nova Linguagem, passando **`title`** **`image`** e **`ranking`** no corpo da requisição. |
|**[PATCH](https://github.com/HugoJhonathan/alura-stickers/blob/api/linguagens-api/src/main/java/br/com/alura/linguagens/api/LinguagemController.java#L45)**   | `http://localhost:8080/linguagens/{id}` | Incrementa em 1 o **`ranking`** da Linguagem cujo **`id`** foi passado no parâmetro. |
|**[DELETE](https://github.com/HugoJhonathan/alura-stickers/blob/api/linguagens-api/src/main/java/br/com/alura/linguagens/api/LinguagemController.java#L39)**  | `http://localhost:8080/linguagens/{id}` | Deleta uma Linguagem cujo **`id`** foi passado no parâmetro. |

## 👽 Utilizando o nosso gerador de Figurinhas para gerar as imagens das Linguagens vinda da API

![](https://i.imgur.com/VDuUSu3.png)

````json
[
    {
        "id": "62ed8ad2ec665976f7f6ff14",
        "title": "C",
        "image": "https://github.com/abrahamcalf/programming-languages-logos/blob/master/src/c/c.png?raw=true",
        "ranking": "4"
    },
    {
        "id": "62ed8b0dec665976f7f6ff15",
        "title": "Cpp",
        "image": "https://github.com/abrahamcalf/programming-languages-logos/blob/master/src/cpp/cpp.png?raw=true",
        "ranking": "1"
    },
    {
        "id": "62ed8b22ec665976f7f6ff16",
        "title": "Csharp",
        "image": "https://github.com/abrahamcalf/programming-languages-logos/blob/master/src/csharp/csharp.png?raw=true",
        "ranking": "1"
    }
]
````

<br>
<br>
<br>

[<img align="left" height="50" margin="10" src="https://i.imgur.com/Z0GFTAc.png">](https://www.alura.com.br/)  Projeto de Gerador de Figurinhas desenvolvido durante a semana de Imersão Java da Alura, que ocorreu entre os dias 18 e 22 de julho de 2022.
