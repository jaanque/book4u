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
import java.time.LocalDate;

public class NovaReserva extends Application {
    
    private String correuUsuari;
    private DatePicker datePickerEntrada;
    private DatePicker datePickerSortida;
    private Spinner<Integer> spinnerPersones;
    
    public NovaReserva() {
        this("usuari@example.com");
    }
    
    public NovaReserva(String correu) {
        this.correuUsuari = correu;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Nova Reserva");
        
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
        
        HBox contenidoCentral = crearContenidoCentral(primaryStage);
        scrollPane.setContent(contenidoCentral);
        
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root, 1200, 750);
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
        
        Button btnNovaReserva = crearBotoNavegacio("Nova reserva", true);
        
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
    
    private HBox crearContenidoCentral(Stage stage) {
        HBox hbox = new HBox(40);
        hbox.setPadding(new Insets(40, 60, 40, 60));
        hbox.setAlignment(Pos.TOP_CENTER);
        
        // Panel izquierdo - Galería de imágenes
        VBox panelIzquierdo = crearGaleriaImagenes();
        
        // Panel derecho - Información y reserva
        VBox panelDerecho = crearPanelReserva(stage);
        
        hbox.getChildren().addAll(panelIzquierdo, panelDerecho);
        
        return hbox;
    }
    
    private VBox crearGaleriaImagenes() {
        VBox vbox = new VBox(15);
        vbox.setPrefWidth(350);
        
        // Grid de imágenes 2x3
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        
        for (int i = 0; i < 6; i++) {
            StackPane imagenBox = new StackPane();
            imagenBox.setPrefSize(160, 140);
            imagenBox.setStyle(
                "-fx-background-color: #d5d5d5;" +
                "-fx-background-radius: 10;"
            );
            
            Label lblIcon = new Label("📷");
            lblIcon.setFont(Font.font(40));
            imagenBox.getChildren().add(lblIcon);
            
            int row = i / 2;
            int col = i % 2;
            grid.add(imagenBox, col, row);
        }
        
        vbox.getChildren().add(grid);
        
        return vbox;
    }
    
    private VBox crearPanelReserva(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setPrefWidth(500);
        vbox.setAlignment(Pos.TOP_LEFT);
        
        // Título
        Label lblNomEstancia = new Label("<Nom estància>");
        lblNomEstancia.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        lblNomEstancia.setTextFill(Color.web("#95a5a6"));
        
        Label lblCaracteristiques = new Label("<Capacitat, nº llits i altres característiques>");
        lblCaracteristiques.setFont(Font.font("Arial", 14));
        lblCaracteristiques.setTextFill(Color.web("#7f8c8d"));
        lblCaracteristiques.setWrapText(true);
        
        // Valoración y anunciante
        HBox valoracioBox = new HBox(30);
        valoracioBox.setAlignment(Pos.CENTER_LEFT);
        
        Label lblValoracio = new Label("VALORACIÓ I USUARI ANUNCIANT");
        lblValoracio.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblValoracio.setTextFill(Color.web("#95a5a6"));
        lblValoracio.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-padding: 15 30 15 30;" +
            "-fx-background-radius: 20;"
        );
        
        valoracioBox.getChildren().add(lblValoracio);
        
        // Descripción
        VBox descripcioBox = new VBox(10);
        descripcioBox.setPadding(new Insets(20));
        descripcioBox.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-background-radius: 10;"
        );
        
        Label lblDescripcioTitol = new Label("DECRIPCIÓ");
        lblDescripcioTitol.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblDescripcioTitol.setTextFill(Color.web("#7f8c8d"));
        
        descripcioBox.getChildren().add(lblDescripcioTitol);
        
        // Selección de fechas y personas
        VBox seleccioBox = new VBox(15);
        seleccioBox.setPadding(new Insets(20));
        seleccioBox.setStyle(
            "-fx-background-color: #d5d5d5;" +
            "-fx-background-radius: 10;"
        );
        
        Label lblSeleccio = new Label("SELECCIONAR DATES, PERSONES");
        lblSeleccio.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblSeleccio.setTextFill(Color.web("#7f8c8d"));
        
        // DatePickers
        HBox datesBox = new HBox(15);
        
        VBox entradaBox = new VBox(5);
        Label lblEntrada = new Label("Data entrada:");
        lblEntrada.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        datePickerEntrada = new DatePicker(LocalDate.now());
        datePickerEntrada.setPrefWidth(200);
        entradaBox.getChildren().addAll(lblEntrada, datePickerEntrada);
        
        VBox sortidaBox = new VBox(5);
        Label lblSortida = new Label("Data sortida:");
        lblSortida.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        datePickerSortida = new DatePicker(LocalDate.now().plusDays(1));
        datePickerSortida.setPrefWidth(200);
        sortidaBox.getChildren().addAll(lblSortida, datePickerSortida);
        
        datesBox.getChildren().addAll(entradaBox, sortidaBox);
        
        // Spinner personas
        HBox personesBox = new HBox(10);
        personesBox.setAlignment(Pos.CENTER_LEFT);
        Label lblPersones = new Label("Nombre de persones:");
        lblPersones.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        spinnerPersones = new Spinner<>(1, 10, 2);
        spinnerPersones.setPrefWidth(80);
        personesBox.getChildren().addAll(lblPersones, spinnerPersones);
        
        seleccioBox.getChildren().addAll(lblSeleccio, datesBox, personesBox);
        
        // Botones
        HBox botonesBox = new HBox(20);
        botonesBox.setAlignment(Pos.CENTER);
        
        Button btnPreu = new Button("PREU");
        btnPreu.setPrefSize(200, 50);
        btnPreu.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;"
        );
        btnPreu.setOnAction(e -> calcularPreu());
        
        Button btnReservar = new Button("RESERVAR");
        btnReservar.setPrefSize(200, 50);
        btnReservar.setStyle(
            "-fx-background-color: #bdc3c7;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 25;" +
            "-fx-cursor: hand;"
        );
        btnReservar.setOnAction(e -> reservar(stage));
        
        botonesBox.getChildren().addAll(btnPreu, btnReservar);
        
        vbox.getChildren().addAll(
            lblNomEstancia,
            lblCaracteristiques,
            valoracioBox,
            descripcioBox,
            seleccioBox,
            botonesBox
        );
        
        return vbox;
    }
    
    private void calcularPreu() {
        if (datePickerEntrada.getValue() == null || datePickerSortida.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dates incompletes",
                "Selecciona les dates",
                "Has de seleccionar data d'entrada i sortida.");
            return;
        }
        
        long dies = java.time.temporal.ChronoUnit.DAYS.between(
            datePickerEntrada.getValue(),
            datePickerSortida.getValue()
        );
        
        if (dies <= 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dates invàlides",
                "Error en les dates",
                "La data de sortida ha de ser posterior a la d'entrada.");
            return;
        }
        
        int persones = spinnerPersones.getValue();
        int preuPerNit = 50; // Simulat
        int preuTotal = (int) (dies * preuPerNit);
        int creditsNecessaris = preuTotal / 10;
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Preu calculat",
            "Resum de la reserva",
            "Dies: " + dies + "\n" +
            "Persones: " + persones + "\n" +
            "Preu per nit: " + preuPerNit + "€\n" +
            "Preu total: " + preuTotal + "€\n" +
            "Crèdits necessaris: " + creditsNecessaris);
    }
    
    private void reservar(Stage stage) {
        if (datePickerEntrada.getValue() == null || datePickerSortida.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Dates incompletes",
                "Selecciona les dates",
                "Has de seleccionar data d'entrada i sortida.");
            return;
        }
        
        Alert confirmacio = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacio.setTitle("Confirmar reserva");
        confirmacio.setHeaderText("Vols confirmar la reserva?");
        confirmacio.setContentText("Es descomptaran els crèdits del teu compte.");
        
        confirmacio.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva confirmada",
                    "Reserva realitzada amb èxit!",
                    "Pots consultar-la a 'Les meves reserves'.");
                
                try {
                    PantallaReserves reserves = new PantallaReserves(correuUsuari);
                    reserves.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
