import java.util.Scanner;

// java jeu ../cartes/timeline.txt ../cartes/timeline2.txt

/**
 * Classe Jeu
 * 
 * Cette classe permet de gérer le jeu Timeline
 * 
 * @author Guillaume & Rayane
 * @version 1.0
 */
public class Jeu {
    Scanner sc = new Scanner(System.in);

    // Cartes que possède le joueur
    private Paquet main;
    // Cartes restantes dans la pioche
    private Paquet pioche;
    // Frise actuelle
    private Frise frise;
    // Score du joueur
    private int score;
    private String nomJoueur;

    private HighScore highScores;

    // Sauvgearde des args pour rejouer
    private String[] cheminFichierTimeline;
    private int tMain;

    // Couleurs pour l'affichage
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[36m";

    /**
     * Constructeur de la classe Jeu
     * 
     * @param cheminFichierTimeline chemin du fichier contenant les cartes de la frise
     * @param tMain taille de la main du joueur
     */
    public Jeu(String[] cheminFichierTimeline) {
        this.cheminFichierTimeline = cheminFichierTimeline;
        this.highScores = new HighScore();
    }

    /**
     * Méthode pour initialiser une partie
     * Initialise tous les attributs de la classe
     */
    public void initialisationPartie(String pseudo, int nbreCarte){
        // Initialisation des attributs
        if(cheminFichierTimeline[0] == null) return;

        this.pioche = new Paquet(cheminFichierTimeline[0]);
        for(int i = 1; i < this.cheminFichierTimeline.length; i++){
            Paquet p = new Paquet(this.cheminFichierTimeline[i]);

            for(int c = 0; c < p.getNbCartes(); c++){
                this.pioche.ajouterCarteFin(p.getCarte(c));
            }
        }

        this.main = new Paquet();
        this.frise = new Frise();
        this.score = 0;

        this.nomJoueur = pseudo;
        printColorer("Bienvenue " + this.nomJoueur + " !", ANSI_BLUE);

        // Initialisation de la taille de la main du joueur
        this.tMain = nbreCarte;
        
        printColorer("Le nombre de carte a été définie à " + tMain + "\n", ANSI_BLUE);

        // Ajouter des cartes à la main du joueur
        for (int i = 0; i < tMain; i++) {
            Carte c = this.pioche.piocherHasard();
            if (c == null) break;

            this.main.ajouterCarteFin(c);
        }

        // Ajouter une carte à la frise en début de partie
        Carte cF = this.pioche.piocherHasard();
        this.frise.ajouterCarteTrie(cF);
        cF.retourner();
    }

    /**
     * Méthode pour choisir une carte dans la main du joueur
     * 
     * @return l'indice de la carte choisie
     */
    private int choisirCarte() {
        printColorer("\nquelle carte de votre main ?", ANSI_YELLOW);
        Carte carteJouee = null;
        int nbCarte = -1;
        while (carteJouee == null) {
            nbCarte = sc.nextInt();
            carteJouee = this.main.getCarte(nbCarte);
        }
        return nbCarte;
    }

    /**
     * Méthode pour attendre un certain nombre de millisecondes
     * 
     * @param milliseconde le nombre de millisecondes à attendre
     */
    private void attendre(int milliseconde) {
        try {
            Thread.sleep(milliseconde);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode pour afficher un texte coloré
     * 
     * @param str le texte à afficher
     * @param color la couleur du texte
     */
    private void printColorer(String str, String color) {
        System.out.println(color + str + ANSI_RESET);
    }

    /**
     * Méthode pour choisir la place de la carte à insérer
     * 
     * @return la place de la carte
     */
    private int choisirPlace() {
        printColorer("Derriere quelle carte de la frise ? (-1 pour avant la première carte)", ANSI_YELLOW);
        int placeCarte = -2;

        while (placeCarte < -1 || placeCarte >= this.frise.getPaquet().getNbCartes()) {
            placeCarte = sc.nextInt();
        }

        return placeCarte;
    }

    /**
     * Méthode pour afficher la carte avant et après la carte choisie
     * 
     * @param placeCarte la place de la carte
     */
    private void afficherBorneCarte(int placeCarte) {
        printColorer("Vous avec choisi de placer la carte", ANSI_GREEN);
        // Si on place la carte au tout début de la frise
        if (placeCarte == -1) {
            printColorer("Avant ....\n     - " + this.frise.getPaquet().getCarte(0), ANSI_GREEN);
        }
        // Si on place la carte à la fin de la frise
        else if (placeCarte == this.frise.getPaquet().getNbCartes() - 1) {
            printColorer("Après ....\n     - " + this.frise.getPaquet().getCarte(placeCarte), ANSI_GREEN);
        }
        //Si elle est placée entre deux cartes
        else if (placeCarte >= 0 && placeCarte < this.frise.getPaquet().getNbCartes()) {
            printColorer("Entre ....\n     - " + this.frise.getPaquet().getCarte(placeCarte) + "\n     - " + this.frise.getPaquet().getCarte(placeCarte + 1), ANSI_GREEN);
        } else {
            printColorer("Erreur dans le choix de la place de la carte", ANSI_RED);
        }
    }

    /**
     * Méthode pour vérifier si la carte peut être placée à l'endroit choisi avec affichage
     * @param carteJouee  La carte jouée
     * @param placeCarte La place de la carte
     * @param nbCarteChoisi L'indice de la carte choisie
     */
    private boolean verifierEmplacement(Carte carteJouee, int placeCarte, int nbCarteChoisi){
        System.out.println(carteJouee.getNom() + " " + placeCarte + " " + nbCarteChoisi);
        boolean resultat = this.frise.insererCarteApres(carteJouee, placeCarte);

        if (resultat) {
            printColorer("\n !!! Une carte de placee !!!", ANSI_GREEN);

            // Retirer la carte de la main du joueur
            this.main.retirerCarte(nbCarteChoisi);

            return true;
        } else {
            printColorer("\n !!! La carte n'est pas au bon endroit !!!", ANSI_RED);

            // Retirer la carte de la main du joueur
            this.main.retirerCarte(nbCarteChoisi);
            // Ajoute une nouvelle carte à la fin de la main du joueur
            Carte c = this.pioche.piocherHasard();
            this.main.ajouterCarteFin(c);

            return false;
        }
    }

    /**
     * Méthode pour exécuter un tour de jeu
     */
    private void executerTour() {
        afficherEtatPartie();

        int nbCarteChoisi = choisirCarte();
        Carte carteJouee = this.main.getCarte(nbCarteChoisi);

        printColorer("Vous avez choisi de jouer la carte :", ANSI_GREEN);
        printColorer(carteJouee.toString() + "\n", ANSI_GREEN);

        int placeCarte = choisirPlace();

        attendre(400);

        afficherBorneCarte(placeCarte);

        attendre(2000);

        printColorer("\nVérifions si la carte peut être placée à cet endroit", ANSI_YELLOW);

        attendre(750);

        // Révèle la date de la carte
        carteJouee.retourner();

        printColorer(carteJouee.toString(), ANSI_YELLOW);

        attendre(1250);

        verifierEmplacement(carteJouee, placeCarte, nbCarteChoisi);

        score++; // Un coup est passé, le score augmente

        attendre(2000);
    }

    /**
     * Méthode pour afficher l'état de la partie
     */
    private void afficherEtatPartie() {
        StringBuffer str = new StringBuffer();
        str.append("--------------------------\n");
        str.append("frise\n");
        str.append(this.frise.toString() + "\n\n");

        str.append("--------------------------\n");
        str.append("main du joueur\n");
        str.append(this.main.toString());

        printColorer(str.toString(), ANSI_BLUE);
    }

    /**
     * Méthode pour récupérer les cartes
     * @return tableau de strings contenant les cartes,
     * 0 : cartes de la frise
     * 1 : cartes de la main
     */
    public String[] recupererCartes(){
        String[] cartes = new String[2];
        cartes[0] = this.frise.getPaquet().toString();
        cartes[1] = this.main.toString();
        return cartes;
    }

    /**
     * Méthode pour commencer le jeu
     */
    public void commencerJeu() {
        while (pioche.getNbCartes() > 0 && main.getNbCartes() > 0) {
            executerTour();
        }

        if (pioche.getNbCartes() == 0) {
            printColorer("Vous avez perdu la partie car la pioche est vide !", ANSI_RED);
        } else {
            printColorer("Vous avez gagné la partie !", ANSI_GREEN);
        }

        // Affichage du score
        printColorer("\nVotre score est de : " + score, ANSI_BLUE);

        // Sauvegarde du score dans les highscores
        highScores.ajouterScore(tMain, new Score(nomJoueur, score));

        // Affichage des highscores
        printColorer("\nMeilleurs scores : \n" + highScores.toString(), ANSI_BLUE);

        printColorer("\nVoulez-vous rejouer ? (o/n)", ANSI_YELLOW);
        String rejouer = sc.next();

        if (rejouer.equals("o")) {
            commencerJeu();
        } else {
            printColorer("Merci d'avoir joué !", ANSI_YELLOW);
        }
    }

    public boolean insererCarte(int carteId, int positionInsertion){
        Carte carteJouee = this.main.getCarte(carteId);
        carteJouee.retourner();
        return verifierEmplacement(carteJouee, positionInsertion, carteId);
    }

    public Carte getCarteMain(int carteId){
        return this.main.getCarte(carteId);
    }
}
