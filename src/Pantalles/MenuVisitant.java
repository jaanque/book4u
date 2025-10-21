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

public class MenuVisitant extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Mode Visitant");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Barra superior
        HBox barraSuperior = crearBarraSuperior(primaryStage);
        root.setTop(barraSuperior);
        
        // Contenido central
        VBox contenidoCentral = crearContenidoCentral(primaryStage);
        root.setCenter(contenidoCentral);
        
        Scene scene = new Scene(root, 900, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox crearBarraSuperior(Stage stage) {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(15, 30, 15, 30));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: #2c3e50;");
        
        Label lblLogo = new Label("Book4u");
        lblLogo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblLogo.setTextFill(Color.WHITE);
        
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar estances...");
        txtBuscar.setPrefWidth(300);
        txtBuscar.setPrefHeight(35);
        
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        
        Button btnLogin = new Button("Iniciar Sessió");
        btnLogin.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnLogin.setOnAction(e -> {
            try {
                Login login = new Login();
                login.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Button btnRegistro = new Button("Registrar-se");
        btnRegistro.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnRegistro.setOnAction(e -> {
            try {
                registre registre = new registre();
                registre.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        hbox.getChildren().addAll(lblLogo, txtBuscar, espaciador, btnLogin, btnRegistro);
        
        return hbox;
    }
    
    private VBox crearContenidoCentral(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(40, 50, 40, 50));
        vbox.setAlignment(Pos.TOP_CENTER);
        
        // Banner informativo
        VBox banner = new VBox(10);
        banner.setPadding(new Insets(20));
        banner.setAlignment(Pos.CENTER);
        banner.setStyle(
            "-fx-background-color: #fff3cd;" +
            "-fx-border-color: #ffc107;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;"
        );
        
        Label lblAvisoTitulo = new Label("Mode Visitant");
        lblAvisoTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblAvisoTitulo.setTextFill(Color.web("#856404"));
        
        Label lblAvisoDescripcion = new Label(
            "Pots navegar pel catàleg i consultar estances, però per fer reserves\n" +
            "necessites registrar-te o iniciar sessió."
        );
        lblAvisoDescripcion.setFont(Font.font("Arial", 13));
        lblAvisoDescripcion.setTextFill(Color.web("#856404"));
        lblAvisoDescripcion.setAlignment(Pos.CENTER);
        
        banner.getChildren().addAll(lblAvisoTitulo, lblAvisoDescripcion);
        
        Label lblTitulo = new Label("Catàleg d'Estances");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        Label lblInfo = new Label("Explora les nostres estances disponibles");
        lblInfo.setFont(Font.font("Arial", 14));
        lblInfo.setTextFill(Color.web("#7f8c8d"));
        
        // Simulación de catálogo
        Label lblCatalogo = new Label("📍 Aquí es mostraran les estances disponibles...");
        lblCatalogo.setFont(Font.font("Arial", 15));
        lblCatalogo.setTextFill(Color.web("#95a5a6"));
        lblCatalogo.setPadding(new Insets(50));
        
        vbox.getChildren().addAll(banner, lblTitulo, lblInfo, lblCatalogo);
        
        return vbox;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
