import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;
import libtest.*;

/**
 * classe de test qui permet de verifier que la classe Frise
 * fonctionne correctement
 */
public class TestFrise {

	/**
	 * methode de lancement des tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		lancer(new TestFrise(), args);
	}

	
	public void test1_constructeur() {
		Frise frise = new Frise();
		assertEquals("frise devrait avoir 0 carte", 0, frise.getPaquet().getNbCartes());

	}

	public void test2_ajouterCarteTrie() {
		Frise frise = new Frise();
		frise.ajouterCarteTrie(new Carte("événement1:1930"));
		assertEquals("frise devrait avoir 1 carte", 1, frise.getPaquet().getNbCartes());
		assertEquals("la carte 1 a pour date 1930", 1930, frise.getPaquet().getCarte(0).getDate());
		
	}

	public void test3_ajoutCarteTrie() {
		Frise frise = new Frise();

		frise.ajouterCarteTrie(new Carte("événement3:1935"));
		frise.ajouterCarteTrie(new Carte("événement3:1934"));
		frise.ajouterCarteTrie(new Carte("événement1:1930"));
		frise.ajouterCarteTrie(new Carte("événement3:1933"));
		frise.ajouterCarteTrie(new Carte("événement2:1931"));
		frise.ajouterCarteTrie(new Carte("événement3:1932"));

		assertEquals("frise devrait avoir 6 cartes", 6, frise.getPaquet().getNbCartes());
		assertEquals("la carte 1 a pour date 1930", 1930, frise.getPaquet().getCarte(0).getDate());
		assertEquals("la carte 2 a pour date 1931", 1931, frise.getPaquet().getCarte(1).getDate());
		assertEquals("la carte 3 a pour date 1932", 1932, frise.getPaquet().getCarte(2).getDate());
		assertEquals("la carte 4 a pour date 1933", 1933, frise.getPaquet().getCarte(3).getDate());
		assertEquals("la carte 5 a pour date 1934", 1934, frise.getPaquet().getCarte(4).getDate());
		assertEquals("la carte 6 a pour date 1935", 1935, frise.getPaquet().getCarte(5).getDate());
	}

	public void test4_verifierCarteApres_bonEndroit(){
		Frise frise = new Frise();

		frise.ajouterCarteTrie(new Carte("événement2:1931"));
		frise.ajouterCarteTrie(new Carte("événement3:1932"));
		frise.ajouterCarteTrie(new Carte("événement3:1935"));

		assertEquals("la carte événement 3 en 1933", true, frise.verifierCarteApres(new Carte("événement3:1933"), 2));
	}

}
