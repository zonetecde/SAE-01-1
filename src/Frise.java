public class Frise {
    private Paquet cartes;

    public Frise() {
        this.cartes = new Paquet();
    }

    public Paquet getPaquet() {
        return this.cartes;
    }

    public void ajouterCarteTrie(Carte c) {
        if(c == null) return;

        int i = 0;
    
        while (i < cartes.getNbCartes() && c.getDate() > cartes.getCarte(i).getDate()) {
            i++;
        }
    
        if (i == 0) {
            cartes.ajouterCarteDebut(c);
        } else {
            cartes.ajouterCarte(c, i);
        }
    }
    

    public boolean verifierCarteApres(Carte c, int p) {
        if(c == null) return false;
        if(p < -1 || p > cartes.getNbCartes()) return false;

        Carte cartePrecedente = null;
        Carte carteSuivante = null;

        if(cartes.getCarte(p) != null){
            cartePrecedente = cartes.getCarte(p);
        }

        if(cartes.getCarte(p+1) != null){
            carteSuivante = cartes.getCarte(p+1);
        }

        if(cartePrecedente == null && carteSuivante == null){
            return true;
        }else if(cartePrecedente == null){
            return c.getDate() < carteSuivante.getDate();
        }else if(carteSuivante == null){
            return cartePrecedente.getDate() < c.getDate();
        }else{
            return cartePrecedente.getDate() < c.getDate() && c.getDate() < carteSuivante.getDate();
        }
    }


    public boolean insererCarteApres(Carte c, int p) {
        if(c == null) return false;

        if(cartes.getNbCartes() == 0 || verifierCarteApres(c, p)) {
            ajouterCarteTrie(c);
            return true;
        } else {
            return false;
        }
    }

    public String toString(){
        return cartes.toString();
    }
}