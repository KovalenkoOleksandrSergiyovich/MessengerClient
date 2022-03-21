package messenger.app.messenger.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScreenController {
    private static HashMap<String, Scene> sceneMap = new HashMap<>();
    private static HashMap<String, Pane> screenMap = new HashMap<>();
    private static HashMap<String, IBaseController> controllerMap = new HashMap<>();
    private static Stage stage;

    public static void setStage(Stage stage) {
        ScreenController.stage = stage;
    }

    public static void addScene(String name, Scene scene){
        sceneMap.put(name, scene);
    }

    public static void addScreen(String name, Pane pane){
        screenMap.put(name, pane);
    }

    public static Pane getScreen(String name){
        return screenMap.get(name);
    }

    public static void addController(String name, IBaseController controller){
        controllerMap.put(name, controller);
    }

    public static void updateScene(String name, String screenName){
        Scene scene = sceneMap.get(name);
        scene.setRoot(screenMap.get(screenName));
    }

    public static void removeScreen(String name){
        sceneMap.remove(name);
    }

    public static void activate(String name){
        System.out.println(name);
        stage.setScene(sceneMap.get(name));
        IBaseController controller = controllerMap.get(name);
        System.out.println(controller);
        if (controller != null) {
            controller.screenActivated();
        }
    }

    public static void updateSceneHeight(String name, double height) {
        stage.setHeight(height);
    }
 }
