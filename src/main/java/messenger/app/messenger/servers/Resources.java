package messenger.app.messenger.servers;

import javafx.scene.Scene;

import java.io.InputStream;
import java.util.HashMap;

public class Resources {
    private static HashMap<String, String> templateMap = new HashMap<>();

    public static void setTemplate(String template, String fileStream) {
        templateMap.put(template, fileStream);
    }

    public static String getTemplate(String template) {
       return templateMap.get(template);
    }
}
