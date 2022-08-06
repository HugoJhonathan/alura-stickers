import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class GeradoraDeFigurinhasIMDB {

    public final static int ALTURA_MAXIMA = 1000;
    public final static int ALTURA_DO_RODAPE= (int) (ALTURA_MAXIMA * 0.20);

    public void cria(InputStream inputStream, String nomeArquivo, String title, String rank, Double nota) throws Exception {

        String text = title;
        Color color = Color.RED;
        Integer fontSize = 1;

        BufferedImage imagemOriginal = this.capaRedimensionada(inputStream, rank, true);
        BufferedImage fotoDasEstrelas = this.estrelasDeClassificacao(nota, 500);

        int larguraFoto = imagemOriginal.getWidth();

        BufferedImage novaImagem = new BufferedImage(
                larguraFoto,
                imagemOriginal.getHeight()+fotoDasEstrelas.getHeight()+ALTURA_DO_RODAPE,
                BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();

        graphics.drawImage(imagemOriginal, 0, 0, larguraFoto, imagemOriginal.getHeight(), null);

        var font = Font.createFont(Font.TRUETYPE_FONT, new File("impact.ttf"))
                .deriveFont(Font.LAYOUT_LEFT_TO_RIGHT, fontSize);

        graphics.setFont(font);

        // for para ajustar o tamanho do texto até o limite da largura, ou da altura do rodapé
        int widthOfText, heightOfText;
        do {
            graphics.setFont(font.deriveFont((float) graphics.getFont().getSize() + 1));
            widthOfText = graphics.getFontMetrics().stringWidth(text);
            heightOfText = (graphics.getFontMetrics().getAscent() - graphics.getFontMetrics().getDescent() - graphics.getFontMetrics().getLeading());
        }
        while (widthOfText < larguraFoto && heightOfText < ALTURA_DO_RODAPE-30);

        graphics.setColor(color);

        // configuração de antialiasing
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // escreve a mensagem no rodapé
        graphics.drawString(text, (larguraFoto-widthOfText)/2, (novaImagem.getHeight() - (ALTURA_DO_RODAPE-heightOfText)/2));

        // adiciona as estrelas
        graphics.drawImage(fotoDasEstrelas, (novaImagem.getWidth() - fotoDasEstrelas.getWidth())/2, imagemOriginal.getHeight(), fotoDasEstrelas.getWidth(), fotoDasEstrelas.getWidth()/10, null);

        // salva as fotos na pasta saida
        if (!new File("saida/filmes").exists()) {
            new File("saida/filmes").mkdirs();
        }
        ImageIO.write(novaImagem, "png", new File("saida/filmes/" + nomeArquivo.replace(':', '-')));
    }

    public static BufferedImage capaRedimensionada(InputStream inputStream, String rank, boolean capa) throws IOException {

        BufferedImage acrilicoImage = ImageIO.read(new File("images/test.png"));
        BufferedImage flag1LugarImage = ImageIO.read(new File("images/1-lugar.png"));
        BufferedImage flag2LugarImage = ImageIO.read(new File("images/2-lugar.png"));
        BufferedImage flag3LugarImage = ImageIO.read(new File("images/3-lugar.png"));

        BufferedImage imagemOriginal;

        try {
            imagemOriginal = ImageIO.read(inputStream);
        } catch (IOException e) {
            System.out.println("\u001b[31;1m Não foi possível ler esta imagem");
            imagemOriginal = ImageIO.read(new URL("https://i.imgur.com/VHxP6Zx.png").openStream());
        }

        // Redimensionamento da imagem, para caber dentro do espaço de 674x1000 sem cortar na altura e largura
        double proporcao = (double)  imagemOriginal.getHeight() / (double) imagemOriginal.getWidth();
        int heightAtualizada = ALTURA_MAXIMA;
        int larguraAtualizada = (int) Math.round(heightAtualizada / proporcao);

        BufferedImage novaImagem = new BufferedImage(larguraAtualizada, heightAtualizada, BufferedImage.TRANSLUCENT);

        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);



        if(capa) {
            graphics.fill(new RoundRectangle2D.Float(0, 0, larguraAtualizada, heightAtualizada, 55, 55));
            graphics.setComposite(AlphaComposite.SrcAtop);
            graphics.drawImage(imagemOriginal, 0, 0, larguraAtualizada, heightAtualizada, null);
            graphics.drawImage(acrilicoImage, 0, 0, larguraAtualizada, heightAtualizada, null);
        }else{
            graphics.drawImage(imagemOriginal, 0, 0, larguraAtualizada, heightAtualizada, null);
        }

        if(rank != null) {
            // Adiciona as medalhas 1º, 2º, e 3º Lugar
            if (Integer.parseInt(rank) == 1) {
                graphics.drawImage(flag1LugarImage, 20, 20, 201, 265, null);
            }
            if (Integer.parseInt(rank) == 2) {
                graphics.drawImage(flag2LugarImage, 20, 20, 201, 265, null);
            }
            if (Integer.parseInt(rank) == 3) {
                graphics.drawImage(flag3LugarImage, 20, 20, 201, 265, null);
            }
        }
        return novaImagem;
    }

    public static BufferedImage estrelasDeClassificacao(double nota, int largura) throws IOException {

        BufferedImage estrelaCompletaImg = ImageIO.read(new File("images/star.png"));
        BufferedImage estrelaVaziaImg = ImageIO.read(new File("images/star-outlined.png"));
        BufferedImage estrelaMetadeImg = ImageIO.read(new File("images/star-half.png"));

        int tamanhoEstrela = Math.abs(largura / 10);

        BufferedImage todasEstrelasImg = new BufferedImage(largura, tamanhoEstrela, BufferedImage.SCALE_FAST);
        Graphics2D graphics = (Graphics2D) todasEstrelasImg.getGraphics();

        double numberoDecimal = Math.round((nota % 1) * 10);

        for (int i = 1; i <= 10; i++) {
            if (i <= Math.abs(nota)) {
                graphics.drawImage(estrelaCompletaImg, (i * tamanhoEstrela) - tamanhoEstrela, 0, tamanhoEstrela, tamanhoEstrela, null);
            } else if ((i == (int) Math.abs(nota) + 1) && (numberoDecimal > 4)) {
                graphics.drawImage(estrelaMetadeImg, (i * tamanhoEstrela) - tamanhoEstrela, 0, tamanhoEstrela, tamanhoEstrela, null);
            } else {
                graphics.drawImage(estrelaVaziaImg, (i * tamanhoEstrela) - tamanhoEstrela, 0, tamanhoEstrela, tamanhoEstrela, null);
            }
        }
        return todasEstrelasImg;
    }

}

