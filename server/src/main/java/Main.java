import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(6970);

        app.get("/lire-fichier", ctx -> {
            ctx.contentType("text/plain; charset=utf-8");

            String filename = ctx.queryParam("filename");
        
            if (filename == null) {
                ctx.result("Le paramètre filename est obligatoire");
                return;
            }

            if (filename.contains("/")) {
                ctx.result("Le nom du fichier ne doit pas contenir de slash");
                return;
            }

            try {
                String content = Files.readString(Paths.get(filename));
                ctx.result(content);
            } catch (IOException e) {
                ctx.result("Erreur lors de la lecture du fichier : " + e.getMessage());
            }
        });

        app.get("/ecrire-fichier", ctx -> {
            ctx.contentType("text/plain; charset=utf-8");

            String filename = ctx.queryParam("filename");
            String content = ctx.queryParam("content");

            if (filename == null || content == null) {
                ctx.result("Les paramètres filename et content sont obligatoires");
                return;
            }

            if(filename.contains("/"))
            {
                ctx.result("Le nom du fichier ne doit pas contenir de slash");
                return;
            }

            try {
                Files.writeString(Paths.get(filename), content);
                ctx.result("ok");
            } catch (IOException e) {
                ctx.result("Erreur lors de l'écriture du fichier : " + e.getMessage());
            }
        });
    }
}
