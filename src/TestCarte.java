import static libtest.Lanceur.lancer;
import static libtest.OutilTest.assertEquals;
import libtest.*;

/**
 * classe de test qui permet de verifier que la classe Carte
 * fonctionne correctement
 */
public class TestCarte {

	/**
	 * methode de lancement des tests
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		lancer(new TestCarte(), args);
	}
	
	/**
	 * test 
	 */
	public void test1_constructeurCarte_chaine() {
		Carte c = new Carte("carte:1");
		assertEquals("la carte a pour date 1", 1, c.getDate());
		assertEquals("la carte a pour nom carte", "carte", c.getNom());
	}

	public void test2_constructeurCarte_dateNegative() {
		Carte c = new Carte("carte:-1");
		assertEquals("la carte a pour date -1", -1, c.getDate());
		assertEquals("la carte a pour nom carte", "carte", c.getNom());
	}
	
	public void test3_toString_carteConnue(){
		Carte c = new Carte("carte:1");
		c.retourner();
		assertEquals("la carte a pour date 1", "1 -> carte", c.toString());
	}
	
	public void test4_toString_carteInconnue(){
		Carte c = new Carte("carte:1");
		assertEquals("la carte a pour date 1", "??? -> carte", c.toString());
	}

	

}
