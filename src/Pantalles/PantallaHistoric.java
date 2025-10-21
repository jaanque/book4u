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

public class PantallaHistoric extends Application {
    
    private String correuUsuari;
    
    public PantallaHistoric() {
        this("usuari@example.com");
    }
    
    public PantallaHistoric(String correu) {
        this.correuUsuari = correu;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Històric de Reserves");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        VBox topSection = new VBox();
        topSection.getChildren().addAll(
            crearBarraSuperior(primaryStage),
            crearBarraNavegacio(primaryStage)
        );
        root.setTop(topSection);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        VBox contenidoCentral = crearContenidoCentral(primaryStage);
        scrollPane.setContent(contenidoCentral);
        
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root, 1100, 700);
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
        
        Button btnHistoric = crearBotoNavegacio("Històric", true);
        
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
        
        Label lblTitulo = new Label("Reserves històriques");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        lblTitulo.setTextFill(Color.web("#95a5a6"));
        
        // Crear tarjetas de reservas históricas
        VBox tarjeta1 = crearTarjetaReservaHistorica(
            "https://via.placeholder.com/800x200",
            "Reserva Passada 1",
            "5",
            "50"
        );
        
        VBox tarjeta2 = crearTarjetaReservaHistorica(
            "https://via.placeholder.com/800x200",
            "Reserva Passada 2",
            "3",
            "30"
        );
        
        VBox tarjeta3 = crearTarjetaReservaHistorica(
            "https://via.placeholder.com/800x200",
            "Reserva Passada 3",
            "7",
            "70"
        );
        
        vbox.getChildren().addAll(lblTitulo, tarjeta1, tarjeta2, tarjeta3);
        
        return vbox;
    }
    
    private VBox crearTarjetaReservaHistorica(String urlImagen, String nomReserva, String credits, String euros) {
        VBox tarjeta = new VBox(0);
        tarjeta.setMaxWidth(800);
        tarjeta.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 15;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);"
        );
        
        // Imagen
        StackPane imagenContainer = new StackPane();
        imagenContainer.setPrefHeight(200);
        imagenContainer.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-background-radius: 15 15 0 0;"
        );
        
        Label lblImagenPlaceholder = new Label("📷");
        lblImagenPlaceholder.setFont(Font.font(60));
        imagenContainer.getChildren().add(lblImagenPlaceholder);
        
        // Información de créditos gastados
        HBox infoContainer = new HBox();
        infoContainer.setPadding(new Insets(20));
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.setStyle("-fx-background-color: #bdc3c7;");
        
        Label lblInfo = new Label("X CRÈDITS GASTATS = X€");
        lblInfo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblInfo.setTextFill(Color.WHITE);
        lblInfo.setText(credits + " CRÈDITS GASTATS = " + euros + "€");
        
        infoContainer.getChildren().add(lblInfo);
        
        tarjeta.getChildren().addAll(imagenContainer, infoContainer);
        
        return tarjeta;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
