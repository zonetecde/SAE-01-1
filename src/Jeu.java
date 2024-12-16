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

            c.retourner();
            this.frise.ajouterCarteTrie(c);
        
        }
    }

    private void executerTour(){
        afficherEtatPartie();

        System.out.println("quelle carte de votre main ?");
        Carte carteJouee = null;
        int nbCarte = -1;
        while(carteJouee == null){
            nbCarte = sc.nextInt();
            carteJouee = this.main.getCarte(nbCarte);
        }
        System.out.println(carteJouee.toString());

        System.out.println("derriere quelle carte de la frise ?");
        int placeCarte = -2;

        while(placeCarte < 0 || placeCarte >= this.frise.getPaquet().getNbCartes()){
            placeCarte = sc.nextInt();
        }

        if(this.frise.getPaquet().getCarte(placeCarte+1) == null){
            System.out.println("après ....\n     - "+this.frise.getPaquet().getCarte(placeCarte));
        }else{
            System.out.println("entre ....\n     - "+this.frise.getPaquet().getCarte(placeCarte)+"\n     - "+this.frise.getPaquet().getCarte(placeCarte+1));
        }

        carteJouee.retourner();
        System.out.println(carteJouee);
        boolean resultat = this.frise.insererCarteApres(carteJouee, placeCarte);

        if(resultat){
            System.out.println(carteJouee.toString()+"\n !!! Une carte de placee !!!");

            this.main.retirerCarte(nbCarte);
        }else{
            System.out.println(carteJouee.toString()+"\n !!! La carte n'est pas au bon endroit !!!");

            this.main.retirerCarte(nbCarte);
            this.main.ajouterCarteFin(this.pioche.piocherHasard());
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
        while(pioche.getNbCartes() > 0 && main.getNbCartes() > 0){
            executerTour();
        }

        if(pioche.getNbCartes() == 0){
            System.out.println("Vous avez perdu la partie car la pioche est vide !");
        }else{
            System.out.println("Vous avez gagné la partie !");
        }
    }
}
