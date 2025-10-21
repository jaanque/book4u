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

public class PantallaInicial extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Benvingut");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #2c3e50, #3498db);");
        
        VBox contenedorCentral = crearContenidoPrincipal(primaryStage);
        root.setCenter(contenedorCentral);
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private VBox crearContenidoPrincipal(Stage stage) {
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(80, 100, 80, 100));
        vbox.setAlignment(Pos.CENTER);
        
        // Logo y título principal
        Label lblTitulo = new Label("Book4u");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        lblTitulo.setTextFill(Color.WHITE);
        
        Label lblSubtitulo = new Label("Reserva la teva estància ideal");
        lblSubtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        lblSubtitulo.setTextFill(Color.web("#ecf0f1"));
        
        Label lblDescripcion = new Label("Plataforma de reserves d'estances de manera senzilla, segura i intuïtiva");
        lblDescripcion.setFont(Font.font("Arial", 14));
        lblDescripcion.setTextFill(Color.web("#bdc3c7"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setMaxWidth(500);
        lblDescripcion.setAlignment(Pos.CENTER);
        
        // Separador
        Region separador = new Region();
        separador.setPrefHeight(20);
        
        // Botones principales
        Button btnLogin = crearBoton("Iniciar Sessió", "#3498db", "#2980b9");
        btnLogin.setOnAction(e -> abrirLogin(stage));
        
        Button btnRegistro = crearBoton("Registrar-se", "#27ae60", "#229954");
        btnRegistro.setOnAction(e -> abrirRegistro(stage));
        
        Button btnVisitante = crearBoton("Continuar com a visitant", "#95a5a6", "#7f8c8d");
        btnVisitante.setOnAction(e -> abrirMenuVisitante(stage));
        
        // Añadir elementos
        vbox.getChildren().addAll(
            lblTitulo,
            lblSubtitulo,
            lblDescripcion,
            separador,
            btnLogin,
            btnRegistro,
            btnVisitante
        );
        
        return vbox;
    }
    
    private Button crearBoton(String texto, String colorNormal, String colorHover) {
        Button btn = new Button(texto);
        btn.setPrefSize(400, 50);
        btn.setStyle(
            "-fx-background-color: " + colorNormal + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        
        btn.setOnMouseEntered(e -> btn.setStyle(
            "-fx-background-color: " + colorHover + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        
        btn.setOnMouseExited(e -> btn.setStyle(
            "-fx-background-color: " + colorNormal + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        
        return btn;
    }
    
    private void abrirLogin(Stage stage) {
        try {
            Login login = new Login();
            login.start(stage);
        } catch (Exception e) {
            mostrarAlerta("Error", "No s'ha pogut obrir la pantalla de login", e.getMessage());
        }
    }
    
    private void abrirRegistro(Stage stage) {
        try {
            registre registre = new registre();
            registre.start(stage);
        } catch (Exception e) {
            mostrarAlerta("Error", "No s'ha pogut obrir la pantalla de registre", e.getMessage());
        }
    }
    
    private void abrirMenuVisitante(Stage stage) {
        try {
            MenuVisitant menuVisitant = new MenuVisitant();
            menuVisitant.start(stage);
        } catch (Exception e) {
            mostrarAlerta("Error", "No s'ha pogut obrir el menú de visitant", e.getMessage());
        }
    }
    
    private void mostrarAlerta(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
