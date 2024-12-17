import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import netscape.javascript.JSObject;

public class Gui extends Application {
    private WebEngine webEngine;
    private Jeu jeu;

    // Classe de pont séparée avec accès au WebEngine
    public class JavaBridge {
        public void commencerPartieSolo(String pseudo, int nbreCarte, JSObject resolveFunction) {
            // Initialisation de la partie
            jeu.initialisationPartie(pseudo, nbreCarte);

            // Récupère les infos actuelles de la partie pour les afficher
            String[] infosPartie = jeu.recupererCartes();
            var frise = infosPartie[0];
            var main = infosPartie[1];

            // Retournement de la réponse à JavaScript
            Platform.runLater(() -> {
                resolveFunction.call("afficherInfosPartie", frise, main);
            });
        }

        public void print(String message) {
            System.out.println(message);
        }

        // Méthode optionnelle pour des interactions plus complexes
        public void executeJavaScriptFunction(String functionName) {
            Platform.runLater(() -> {
                try {
                    webEngine.executeScript(functionName);
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'exécution de la fonction : " + e.getMessage());
                }
            });
        }
    }

    @Override
    public void start(Stage stage) {
        // Création de la WebView
        WebView webView = new WebView();
        webEngine = webView.getEngine();

        // Configuration du gestionnaire de chargement
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                try {
                    // Récupération de l'objet window JavaScript
                    JSObject window = (JSObject) webEngine.executeScript("window");
                    
                    // Création et enregistrement du pont Java
                    JavaBridge javaBridge = new JavaBridge();
                    window.setMember("javaBridge", javaBridge);
                    
                    System.out.println("Pont Java-JavaScript initialisé avec succès");
                } catch (Exception e) {
                    System.err.println("Erreur lors de l'initialisation du pont : " + e.getMessage());
                }
            }
        });

        // Chargement de la page HTML
        try {
            webEngine.load(new File("./web/index.html").toURI().toString());
        } catch (Exception e) {
            System.err.println("Erreur de chargement de la page HTML : " + e.getMessage());
            e.printStackTrace();
        }

        jeu = new Jeu(new String[] { "../cartes/timeline.txt", "../cartes/timeline2.txt" });

        // Configuration de la scène
        Scene scene = new Scene(webView, 800, 600);
        stage.setTitle("Le jeu de la frise chronologique");
        stage.setScene(scene);
        stage.show();

     
    }

    public static void main(String[] args) {
        launch(args);
    }
}