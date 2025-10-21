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

public class DetallReserva extends Application {
    
    private String correuUsuari;
    private String nomReserva;
    
    public DetallReserva() {
        this("usuari@example.com", "Reserva X");
    }
    
    public DetallReserva(String correu, String nomReserva) {
        this.correuUsuari = correu;
        this.nomReserva = nomReserva;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Detall Reserva");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        VBox topSection = new VBox();
        topSection.getChildren().addAll(
            crearBarraSuperior(primaryStage),
            crearBarraNavegacio(primaryStage)
        );
        root.setTop(topSection);
        
        VBox contenidoCentral = crearContenidoCentral(primaryStage);
        root.setCenter(contenidoCentral);
        
        Scene scene = new Scene(root, 1100, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private HBox crearBarraSuperior(Stage stage) {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(15, 30, 15, 30));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: #2c3e50;");
        
        Button btnHome = new Button("🏠");
        btnHome.setFont(Font.font(20));
        btnHome.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-text-fill: white;" +
            "-fx-cursor: hand;" +
            "-fx-font-size: 24px;"
        );
        btnHome.setOnAction(e -> {
            try {
                MenuClient menu = new MenuClient("Usuari", correuUsuari);
                menu.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        TextField txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar");
        txtBuscar.setPrefWidth(400);
        txtBuscar.setPrefHeight(35);
        
        Button btnFiltrar = new Button("↓ Filtrar");
        btnFiltrar.setStyle(
            "-fx-background-color: #95a5a6;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        
        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);
        
        Label lblCredits = new Label("0 $");
        lblCredits.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblCredits.setTextFill(Color.WHITE);
        
        Button btnPerfil = new Button("●");
        btnPerfil.setFont(Font.font(24));
        btnPerfil.setStyle(
            "-fx-background-color: black;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 50;" +
            "-fx-cursor: hand;"
        );
        
        hbox.getChildren().addAll(btnHome, txtBuscar, btnFiltrar, espaciador, lblCredits, btnPerfil);
        return hbox;
    }
    
    private HBox crearBarraNavegacio(Stage stage) {
        HBox hbox = new HBox(0);
        hbox.setPadding(new Insets(0));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle("-fx-background-color: #bdc3c7;");
        
        Button btnNovaReserva = crearBotoNavegacio("Nova reserva", false);
        btnNovaReserva.setOnAction(e -> {
            try {
                NovaReserva novaReserva = new NovaReserva(correuUsuari);
                novaReserva.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Button btnMevesReserves = crearBotoNavegacio("Les meves reserves", false);
        btnMevesReserves.setOnAction(e -> {
            try {
                PantallaReserves reserves = new PantallaReserves(correuUsuari);
                reserves.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        Button btnHistoric = crearBotoNavegacio("Històric", false);
        btnHistoric.setOnAction(e -> {
            try {
                PantallaHistoric historic = new PantallaHistoric(correuUsuari);
                historic.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        
        hbox.getChildren().addAll(btnNovaReserva, btnMevesReserves, btnHistoric);
        return hbox;
    }
    
    private Button crearBotoNavegacio(String text, boolean actiu) {
        Button btn = new Button(text);
        btn.setPrefHeight(50);
        btn.setPrefWidth(200);
        btn.setStyle(
            "-fx-background-color: " + (actiu ? "#ecf0f1" : "#bdc3c7") + ";" +
            "-fx-text-fill: " + (actiu ? "#2c3e50" : "#7f8c8d") + ";" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: " + (actiu ? "bold" : "normal") + ";" +
            "-fx-background-radius: 0;" +
            "-fx-cursor: hand;"
        );
        
        if (!actiu) {
            btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #95a5a6;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 0;" +
                "-fx-cursor: hand;"
            ));
            
            btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: #bdc3c7;" +
                "-fx-text-fill: #7f8c8d;" +
                "-fx-font-size: 14px;" +
                "-fx-background-radius: 0;" +
                "-fx-cursor: hand;"
            ));
        }
        
        return btn;
    }
    
    private VBox crearContenidoCentral(Stage stage) {
        VBox vbox = new VBox(30);
        vbox.setPadding(new Insets(40, 80, 40, 80));
        vbox.setAlignment(Pos.TOP_CENTER);
        
        Label lblTitulo = new Label("<Reserva X>");
        lblTitulo.setText(nomReserva);
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        lblTitulo.setTextFill(Color.web("#95a5a6"));
        
        // Tarjeta de modificar reserva
        VBox tarjetaModificar = new VBox(30);
        tarjetaModificar.setMaxWidth(700);
        tarjetaModificar.setPadding(new Insets(50));
        tarjetaModificar.setAlignment(Pos.CENTER);
        tarjetaModificar.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-background-radius: 15;"
        );
        
        Label lblModificar = new Label("Modificar reserva i preu\ns'actualitza");
        lblModificar.setFont(Font.font("Arial", FontWeight.NORMAL, 28));
        lblModificar.setTextFill(Color.web("#7f8c8d"));
        lblModificar.setAlignment(Pos.CENTER);
        lblModificar.setWrapText(true);
        
        Button btnModificar = new Button("MODIFICAR");
        btnModificar.setPrefSize(250, 50);
        btnModificar.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;"
        );
        
        btnModificar.setOnMouseEntered(e -> btnModificar.setStyle(
            "-fx-background-color: #95a5a6;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;"
        ));
        
        btnModificar.setOnMouseExited(e -> btnModificar.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;"
        ));
        
        btnModificar.setOnAction(e -> modificarReserva(stage));
        
        tarjetaModificar.getChildren().addAll(lblModificar, btnModificar);
        
        vbox.getChildren().addAll(lblTitulo, tarjetaModificar);
        
        return vbox;
    }
    
    private void modificarReserva(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modificar reserva");
        alert.setHeaderText("Funcionalitat en desenvolupament");
        alert.setContentText("Aviat podràs modificar les dades de la reserva\n(dates, persones, etc.)");
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
