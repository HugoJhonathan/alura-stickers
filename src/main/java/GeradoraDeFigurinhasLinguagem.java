import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class GeradoraDeFigurinhasLinguagem extends GeradoraDeFigurinhasIMDB {

    public final static int ALTURA_MAXIMA = 1000;
    public final static int ALTURA_DO_RODAPE= (int) (ALTURA_MAXIMA * 0.20);

    public void cria(InputStream inputStream, String nomeArquivo, String title) throws Exception {

        BufferedImage imagemOriginal = this.capaRedimensionada(inputStream, null, false);
        int larguraFoto = imagemOriginal.getWidth();

        String text = title;
        Color color = Color.YELLOW;
        Integer fontSize = 1;

        BufferedImage novaImagem = new BufferedImage(
                larguraFoto,
                imagemOriginal.getHeight()+ALTURA_DO_RODAPE,
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

        // salva as fotos na pasta saida
        if (!new File("saida/linguagens").exists()) {
            new File("saida/linguagens").mkdirs();
        }
        ImageIO.write(novaImagem, "png", new File("saida/linguagens/" + nomeArquivo.replace(':', '-')));
    }


}

