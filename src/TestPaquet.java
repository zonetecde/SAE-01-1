import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;

/**
 * classe de test qui permet de verifier que la classe Paquet
 * fonctionne correctement
 */
public class TestPaquet {

	/**
	 * methode de lancement des tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		lancer(new TestPaquet(), args);
	}

	/**
	 * test du constructeur vide
	 */
	
	public void test1_constructeur() {
		Paquet paquet = new Paquet();
		assertEquals("paquet devrait avoir 0 carte", 0, paquet.getNbCartes());
	}

	/**
	 * test du constructeur parametres
	 */
	
	public void test2_constructeurParam() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		assertEquals("paquet devrait avoir 3 cartes", 3, paquet.getNbCartes());
	}

	/**
	 * test getCarte
	 */
	public void test3_getCarte_ok() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		assertEquals("paquet devrait avoir 3 cartes", 3, paquet.getNbCartes());

		Carte c = paquet.getCarte(1);
		assertEquals("la carte 1 a pour date événement2:1931", 1931, c.getDate());
	}

	/**
	 * test getCarte hors tableau
	 */
	public void test4_getCarte_horsTableau() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		assertEquals("paquet devrait avoir 3 cartes", 3, paquet.getNbCartes());

		Carte c = paquet.getCarte(3);
		assertEquals("la carte 3 n'existe pas", null, c);
	}

	/**
	 * test ajoutCarteFin ok
	 */
	
	public void test5_ajoutCarteFin() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		paquet.ajouterCarteFin(new Carte("événement4:1933"));

		assertEquals("paquet devrait avoir 4 cartes", 4, paquet.getNbCartes());

		// chaque carte doit etre bien placee: place i => valeur i+1
		for (int i=0;i<4;i++) {
			Carte c = paquet.getCarte(i);
			assertEquals("la carte "+i+" a pour date 193" +i, Integer.parseInt("193"+i), c.getDate());    
		}
	}

	/**
	 * test retirerCarte ok
	 */
	
	public void test6_retirerCarte() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		Carte c = paquet.retirerCarte(1);
		
		// test paquet
		assertEquals("paquet devrait avoir 2 cartes", 2, paquet.getNbCartes());
		assertEquals("premiere carte valeur événement1:1930", 1930, paquet.getCarte(0).getDate());
		assertEquals("seconde carte valeur événement3:1932", 1932, paquet.getCarte(1).getDate());
			
		// test carte retournee
		assertEquals("carte retiree a pour date événement2:1931", 1931, c.getDate());
	}

	/**
	 * test retirerCarte hors tableau
	 */
	public void test7_retirerCarte_horsTableau() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		Carte c = paquet.retirerCarte(3);
		
		// test paquet
		assertEquals("paquet devrait avoir 3 cartes", 3, paquet.getNbCartes());
		assertEquals("carte retiree n'existe pas", null, c);
	}

	/**
	 * test ajouterCarteFin sur paquet vide
	 */
	
	public void test8_ajoutCarteFin_paquetVide() {
		Paquet paquet = new Paquet();
		paquet.ajouterCarteFin(new Carte("événement1:1930"));

		assertEquals("paquet devrait avoir 1 carte", 1, paquet.getNbCartes());
		assertEquals("la carte 0 a pour date 1930", 1930, paquet.getCarte(0).getDate());
	}

	/**
	 * test retirerCarte sur paquet vide
	 */
	
	public void test9_retirerCarte_paquetVide() {
		Paquet paquet = new Paquet();
		Carte c = paquet.retirerCarte(0);

		assertEquals("paquet devrait avoir 0 carte", 0, paquet.getNbCartes());
		assertEquals("carte retiree n'existe pas", null, c);
	}

	/**
	 * test getCarte avec index negatif
	 */
	
	public void test10_getCarte_indexNegatif() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		Carte c = paquet.getCarte(-1);
		assertEquals("la carte -1 n'existe pas", null, c);
	}

	/**
	 * test retirerCarte avec index negatif
	 */
	
	public void test11_retirerCarte_indexNegatif() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		Carte c = paquet.retirerCarte(-1);
		
		// test paquet
		assertEquals("paquet devrait avoir 3 cartes", 3, paquet.getNbCartes());
		assertEquals("carte retiree n'existe pas", null, c);
	}

	/**
	 * test ajouterCarteDebut
	 */
	
	public void test12_ajouterCarteDebut() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		paquet.ajouterCarteDebut(new Carte("événement0:1929"));

		assertEquals("paquet devrait avoir 4 cartes", 4, paquet.getNbCartes());
		assertEquals("la carte 0 a pour date 1929", 1929, paquet.getCarte(0).getDate());
	}

	/**
	 * test ajouterCarte
	 */
	
	public void test13_ajouterCarte() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		paquet.ajouterCarte(new Carte("événement5:1934"), 1);

		assertEquals("paquet devrait avoir 4 cartes", 4, paquet.getNbCartes());
		assertEquals("la carte d'indice 1 a pour date 1934", 1934, paquet.getCarte(1).getDate());
	}

	public void test14_piocherHasard() {
		Carte[] tab = new Carte[3];
		tab[0] = new Carte("événement1:1930");
		tab[1] = new Carte("événement2:1931");
		tab[2] = new Carte("événement3:1932");
					
		Paquet paquet = new Paquet(tab);
		Carte c = paquet.piocherHasard();
		
		assertEquals("paquet devrait avoir 2 cartes", 2, paquet.getNbCartes());
		assertEquals("carte piochée existe", c != null, true);
	}

	public void test15_piocherHasardPaquetVide() {
		Paquet paquet = new Paquet();
		Carte c = paquet.piocherHasard();
		
		assertEquals("paquet devrait avoir 0 carte", 0, paquet.getNbCartes());
		assertEquals("carte piochée n'existe pas", c == null, true);
	}


}