public class EstrelasDeClassificacao {

    public static final String ANSI_YELLOW = "\u001B[33m";

    public String gerarString(double rating){

        String estrelas = "";

        for(int i = 0; i<10; i++){
            if(i < (int) rating){
                estrelas = estrelas.concat(ANSI_YELLOW+"\u2605");
            }else{
                estrelas = estrelas.concat(ANSI_YELLOW+"\u2606");
            }
        }

        return estrelas;
    }

}
