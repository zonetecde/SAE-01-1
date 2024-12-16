
/**
 * represente une carte trés simple juste avec une valeur
 */
class Carte{

  private String nom;
  private int date;
  private boolean dateVisible;

  /**
   * constructeur de carte à partir d'une chaine
   * format de la chaine: evenement:date
   * 
   * @param chaine chaine contenant l'evenement et la date
   */
  public Carte(String chaine){
    int separationIndex = -1;
    int i = 0;

    while(separationIndex == -1 && i < chaine.length()){
      if(chaine.charAt(i) == ':')
        separationIndex = i;
      else
        i++;
    }

    String evenement = chaine.substring(0, separationIndex);
    int date = Integer.parseInt(chaine.substring(separationIndex + 1));

    this.nom = evenement;
    this.date = date;
  }

  /**
   * getter
   */
  public String getNom(){
    return this.nom;
  }

  public int getDate(){
    return this.date;
  }

  public String toString(){
    if(this.dateVisible)
      return this.date + " -> " + this.nom;
    else
      return "??? -> " + this.nom;
  }

  public void retourner() {
      this.dateVisible = !this.dateVisible;
  }
}
