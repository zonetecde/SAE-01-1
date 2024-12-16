public class ProgJeu {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Utilisation: java ProgJeu <fichier_timeline> <taille_main (defaut: 5)>");
            return;
        }   

        Jeu jeu = new Jeu(args[0], args.length == 1 ? 5 : Integer.parseInt(args[1]));

        jeu.commencerJeu();
    }
}
