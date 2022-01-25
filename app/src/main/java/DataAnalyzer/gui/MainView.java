package DataAnalyzer.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class MainView extends Application {

    FXMLLoader loader = new FXMLLoader();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/FxApplication.fxml");
        BorderPane page = loader.load(inputStream);

        primaryStage.setScene(new Scene(page));
        primaryStage.show();
    }
}
