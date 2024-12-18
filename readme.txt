Pour lancer le jeu avec l'interface graphique,
cd ./src # important
javac ProgJeu.java
java ProgJeu

Si cela ne fonctionne pas,
cd ./src
javac --module-path ./javafx-sdk/lib --add-modules javafx.controls,javafx.web *.java 
java --module-path ./javafx-sdk/lib --add-modules javafx.controls,javafx.web 