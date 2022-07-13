package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;
/**main
 I got a runtime error message saying no mainScreen was found. This happened because I changed mainScreen to mainMenu but for some reason it did not refactor on my main.java file.
 I fixed this by changing Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml")); to  Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));

 In the Future this app can be improved by adding a log in screen for better data security.
 It can also be improved by adding a dark mode and other UI designs.

 */
public class Main extends Application {
    /**Inventory */
    public Inventory inv = new Inventory();
    /**start
     @param primaryStage main stage*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainMenu.fxml"));
        primaryStage.setTitle("Home Screen C482");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    /**main
     @param args launch arg*/
    public static void main(String[] args) {
        launch(args);
    }
}
