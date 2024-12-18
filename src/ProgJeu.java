public class ProgJeu {
    public static void main(String[] args) {
        // Execute la commande javac --module-path ./javafx-sdk/lib --add-modules javafx.controls,javafx.web *.java */ puis java --module-path ./javafx-sdk/lib --add-modules javafx.controls,javafx.web Gui dans le dossier src

        ProcessBuilder pb = new ProcessBuilder("javac", "--module-path", "./javafx-sdk/lib", "--add-modules", "javafx.controls,javafx.web", "*.java");

        pb.inheritIO();
        try {
            pb.start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        pb = new ProcessBuilder("java", "--module-path", "./javafx-sdk/lib", "--add-modules", "javafx.controls,javafx.web", "Gui");

        pb.inheritIO();
        try {
            pb.start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
