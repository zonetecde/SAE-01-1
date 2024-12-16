import java.util.Scanner;

public class Jeu {
    private Scanner sc;

    private Paquet main;
    private Paquet pioche;
    private Frise frise;

    // nombre de cartes dans la main du joueur 
    private int tailleMain;

    public Jeu(String cheminFichierTimeline, int tMain) {
        this.main = new Paquet();
        this.pioche = new Paquet(cheminFichierTimeline);

        this.frise = new Frise();

        this.tailleMain = tMain;    

        this.sc = new Scanner(System.in);

        for(int i = 0; i < this.tailleMain; i++){
            Carte c = this.pioche.piocherHasard();
            if(c == null) break;

            this.main.ajouterCarteFin(c);
        }

        // Ajouter ou non des cartes
        // TODO: demander si il faut ajouter des cartes au début à la frise
        for(int i = 1; i <= 2; i++){
            Carte c = this.pioche.piocherHasard();

            if(c == null) break;

            this.frise.ajouterCarteTrie(c);
        }
    }

    private void executerTour(){
        afficherEtatPartie();

        // Demander quelle carte jouer ...
        System.out.println("\nquelle carte de votre main ?");
        int nbCarte = sc.nextInt();
        Carte carteJouee = this.main.getCarte(nbCarte);
        System.out.println(carteJouee.toString());

        // Demander quelle place jouer cette carte

        System.out.println("derriere quelle carte de la frise ?");
        int placeCarte = sc.nextInt();
        System.out.println("entre ....\n     - "+this.frise.getPaquet().getCarte(placeCarte)+"\n     - "+this.frise.getPaquet().getCarte(placeCarte+1));

        carteJouee.retourner();
        boolean resultat = this.frise.insererCarteApres(carteJouee, placeCarte);
        this.main.retirerCarte(nbCarte);

        if(resultat){
            System.out.println(carteJouee.toString()+"\n !!! Une carte de placee !!!");
        }else{
            System.out.println(carteJouee.toString()+"\n !!! La carte n'est pas au bon endroit !!!");
        }
    }

    private void afficherEtatPartie(){
        StringBuffer str = new StringBuffer();
        str.append("--------------------------\n");
        str.append("frise\n");
        str.append(this.frise.toString() + "\n\n");



        str.append("--------------------------\n");
        str.append("main du joueur\n");
        str.append(this.main.toString());
 
        System.out.println(str.toString());
    }

    public void commencerJeu(){
        for(int i = 0; i < 3; i++){
            executerTour();
        }
    }
}
