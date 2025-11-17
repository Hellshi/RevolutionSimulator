import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/user_view.fxml")));

        stage.setTitle("Sistema MVC");

        // Define o tamanho da cena
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);

        stage.show();

        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}