package Pantalles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PantallaCredits extends Application {
    
    private String correuUsuari;
    private int creditsActuals = 0;
    private Slider sliderCredits;
    private Label lblQuantitat;
    private Label lblPreu;
    
    public PantallaCredits() {
        this("usuari@example.com");
    }
    
    public PantallaCredits(String correu) {
        this.correuUsuari = correu;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Comprar Crèdits");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Barra superior
        HBox barraSuperior = crearBarraSuperior(primaryStage);
        root.setTop(barraSuperior);
        
        // Contenido central
        VBox contenidoCentral = crearContenidoCentral(primaryStage);
        root.setCenter(contenidoCentral);
        
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox crearBarraSuperior(Stage stage) {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(15, 30, 15, 30));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: #2c3e50;");
        
        Button btnVolver = new Button("← Tornar");
        btnVolver.setStyle(
            "-fx-background-color: #34495e;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnVolver.setOnAction(e -> {
            try {
                MenuClient menu = new MenuClient("Usuari", correuUsuari);
                menu.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Label lblTitulo = new Label("PANTALLA CRÈDITS");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.WHITE);
        
        hbox.getChildren().addAll(btnVolver, lblTitulo);
        return hbox;
    }
    
    private VBox crearContenidoCentral(Stage stage) {
        VBox vbox = new VBox(40);
        vbox.setPadding(new Insets(60, 100, 60, 100));
        vbox.setAlignment(Pos.CENTER);
        
        Label lblTitulo = new Label("Comprar Crèdits");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        // Tarjeta de conversión
        VBox tarjetaConversion = new VBox(30);
        tarjetaConversion.setPadding(new Insets(50));
        tarjetaConversion.setAlignment(Pos.CENTER);
        tarjetaConversion.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-background-radius: 15;"
        );
        
        HBox conversionBox = new HBox(40);
        conversionBox.setAlignment(Pos.CENTER);
        
        lblPreu = new Label("50 €");
        lblPreu.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        lblPreu.setTextFill(Color.web("#7f8c8d"));
        
        Label lblFlecha = new Label("→");
        lblFlecha.setFont(Font.font("Arial", 48));
        lblFlecha.setTextFill(Color.web("#7f8c8d"));
        
        lblQuantitat = new Label("5 \"Crèdits\"");
        lblQuantitat.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        lblQuantitat.setTextFill(Color.web("#7f8c8d"));
        
        conversionBox.getChildren().addAll(lblPreu, lblFlecha, lblQuantitat);
        
        // Slider para seleccionar créditos
        sliderCredits = new Slider(1, 100, 5);
        sliderCredits.setShowTickLabels(false);
        sliderCredits.setShowTickMarks(false);
        sliderCredits.setMajorTickUnit(10);
        sliderCredits.setBlockIncrement(1);
        sliderCredits.setPrefWidth(500);
        sliderCredits.setStyle(
            "-fx-control-inner-background: white;"
        );
        
        sliderCredits.valueProperty().addListener((obs, oldVal, newVal) -> {
            int creditos = newVal.intValue();
            lblQuantitat.setText(creditos + " \"Crèdits\"");
            lblPreu.setText((creditos * 10) + " €");
        });
        
        tarjetaConversion.getChildren().addAll(conversionBox, sliderCredits);
        
        // Botón comprar
        Button btnComprar = new Button("COMPRAR");
        btnComprar.setPrefSize(300, 60);
        btnComprar.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        );
        
        btnComprar.setOnMouseEntered(e -> btnComprar.setStyle(
            "-fx-background-color: #95a5a6;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        ));
        
        btnComprar.setOnMouseExited(e -> btnComprar.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 10;" +
            "-fx-cursor: hand;"
        ));
        
        btnComprar.setOnAction(e -> comprarCredits(stage));
        
        Label lblInfo = new Label("10€ = 1Crèdit");
        lblInfo.setFont(Font.font("Arial", 16));
        lblInfo.setTextFill(Color.web("#7f8c8d"));
        
        vbox.getChildren().addAll(lblTitulo, tarjetaConversion, btnComprar, lblInfo);
        
        return vbox;
    }
    
    private void comprarCredits(Stage stage) {
        int creditos = (int) sliderCredits.getValue();
        int precio = creditos * 10;
        
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar compra");
        confirmacion.setHeaderText("Confirmar compra de crèdits");
        confirmacion.setContentText(
            "Vols comprar " + creditos + " crèdits per " + precio + "€?\n\n" +
            "El pagament es processarà mitjançant Stripe."
        );
        
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Aquí iría la integración con Stripe
                Alert exito = new Alert(Alert.AlertType.INFORMATION);
                exito.setTitle("Compra exitosa");
                exito.setHeaderText("Crèdits comprats correctament!");
                exito.setContentText(
                    "S'han afegit " + creditos + " crèdits al teu compte.\n" +
                    "Total pagat: " + precio + "€"
                );
                exito.showAndWait();
                
                // Volver al menú
                try {
                    MenuClient menu = new MenuClient("Usuari", correuUsuari);
                    menu.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
