package Pantalles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class NovaReservaClient extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Nova Reserva Client");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(40, 40, 40, 40));
        vbox.setAlignment(Pos.TOP_CENTER);

        Label lblTitol = new Label("Nova reserva");
        lblTitol.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitol.setTextFill(Color.web("#95a5a6"));
        vbox.getChildren().add(lblTitol);

        // Graella d'exemple: 4 estances
        for (int i = 0; i < 4; i++) {
            final int index = i; // IMPORTANTE para uso en la lambda

            HBox fila = new HBox(20);
            fila.setPadding(new Insets(10));
            fila.setAlignment(Pos.CENTER_LEFT);

            // Imagen simulada de la estancia
            StackPane img = new StackPane();
            img.setStyle("-fx-background-color: #d5d5d5; -fx-background-radius: 20;");
            img.setPrefSize(320, 100);
            Label crd = new Label("× CRÈDITS GASTATS = X€");
            crd.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            crd.setTextFill(Color.WHITE);
            crd.setBackground(new Background(
                    new BackgroundFill(Color.rgb(128,128,128,0.5), new CornerRadii(10), Insets.EMPTY)));
            img.getChildren().add(crd);

            // Descripción/titulo
            VBox desc = new VBox(5);
            desc.setPrefWidth(220);
            Label titol = new Label("Descripció i títol");
            titol.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            titol.setTextFill(Color.web("#95a5a6"));
            desc.getChildren().add(titol);

            // Botón XS y reservar
            VBox botons = new VBox(8);
            botons.setPrefWidth(80);
            Button btnXS = new Button("XS");
            btnXS.setStyle("-fx-background-color: #bcbcbc; -fx-border-radius: 15; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 13px;");
            btnXS.setPrefWidth(60);
            Button btnReserva = new Button("Reservar");
            btnReserva.setStyle("-fx-background-color: #bcbcbc; -fx-border-radius: 15; -fx-text-fill: white; -fx-font-weight: bold;");
            btnReserva.setPrefWidth(60);

            // Solución: usar variable final index
            btnReserva.setOnAction(e -> reservarEstancia(index));
            botons.getChildren().addAll(btnXS, btnReserva);

            fila.getChildren().addAll(img, desc, botons);
            vbox.getChildren().add(fila);
        }

        root.setCenter(vbox);

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void reservarEstancia(int idEstancia) {
        // Aquí abrirás la ventana de detalle/reserva con popup o nueva pantalla
        System.out.println("Reserva solicitada per a estància: " + idEstancia);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

