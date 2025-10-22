package Pantalles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PantallaMevesEstances extends Application {
    private String correuUsuari;
    public PantallaMevesEstances() { this("usuari@example.com"); }
    public PantallaMevesEstances(String correu) { this.correuUsuari = correu; }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);

        Button btnCrear = new Button("Crear nova estància");
        btnCrear.setOnAction(e -> obrirFormulari());

        vbox.getChildren().add(new Label("Les meves estances"));
        vbox.getChildren().add(btnCrear);

        // Simula una llista: repeteix aquest bloc per cada estància
        for (int i = 0; i < 3; i++) {
            HBox fila = new HBox(15);
            fila.setAlignment(Pos.CENTER_LEFT);
            Label titol = new Label("Títol estància " + (i+1));
            Button btnEditar = new Button("Modificar");
            Button btnEliminar = new Button("Eliminar");
            fila.getChildren().addAll(titol, btnEditar, btnEliminar);
            vbox.getChildren().add(fila);
        }
        root.setCenter(vbox);

        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setTitle("Gestionar les meves estances");
        primaryStage.show();
    }

    private void obrirFormulari() {
        // Aquí obre el formulari (nova pantalla o PopUp)
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Formulari crear estància (prototip)", ButtonType.OK);
        alert.showAndWait();
    }

    public static void main(String[] args) { launch(args);}
}
