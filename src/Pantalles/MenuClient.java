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

public class MenuClient extends Application {

    private String nomUsuari;
    private String correuUsuari;
    private int creditsDisponibles = 0;

    public MenuClient() {
        this("Usuari", "usuari@example.com");
    }

    public MenuClient(String nom, String correu) {
        this.nomUsuari = nom;
        this.correuUsuari = correu;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Menú Principal");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        HBox barraSuperior = crearBarraSuperior(primaryStage);
        root.setTop(barraSuperior);

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
        txtBuscar.setStyle("-fx-background-radius: 5; -fx-border-radius: 5;");

        Region espaciador = new Region();
        HBox.setHgrow(espaciador, Priority.ALWAYS);

        Label lblCredits = new Label("Crèdits: " + creditsDisponibles);
        lblCredits.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblCredits.setTextFill(Color.web("#f39c12"));

        Button btnComprarCredits = new Button("Comprar");
        btnComprarCredits.setStyle(
                "-fx-background-color: #f39c12;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 5 15 5 15;"
        );
        btnComprarCredits.setOnAction(e -> comprarCredits(stage));

        Button btnNotificacions = new Button("🔔");
        btnNotificacions.setStyle(
                "-fx-background-color: #e74c3c;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 5 12 5 12;"
        );
        btnNotificacions.setOnAction(e -> veureNotificacions());

        MenuButton menuPerfil = new MenuButton(nomUsuari);
        menuPerfil.setStyle(
                "-fx-background-color: #3498db;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5;"
        );

        MenuItem itemPerfil = new MenuItem("El meu perfil");
        itemPerfil.setOnAction(e -> gestionarPerfil(stage));

        MenuItem itemEstancias = new MenuItem("Gestionar les meves estances");
        itemEstancias.setOnAction(e -> {
            try {
                PantallaMevesEstances gestioEstances = new PantallaMevesEstances(correuUsuari);
                gestioEstances.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        MenuItem itemTancarSessio = new MenuItem("Tancar sessió");
        itemTancarSessio.setOnAction(e -> tancarSessio(stage));

        menuPerfil.getItems().addAll(itemPerfil, itemEstancias, itemTancarSessio);

        hbox.getChildren().addAll(
                lblLogo,
                txtBuscar,
                espaciador,
                lblCredits,
                btnComprarCredits,
                btnNotificacions,
                menuPerfil
        );

        return hbox;
    }

    private VBox crearContenidoCentral(Stage stage) {
        VBox vbox = new VBox(25);
        vbox.setPadding(new Insets(40, 50, 40, 50));
        vbox.setAlignment(Pos.TOP_CENTER);

        Label lblBenvinguda = new Label("Benvingut/da, " + nomUsuari + "!");
        lblBenvinguda.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblBenvinguda.setTextFill(Color.web("#2c3e50"));

        GridPane gridOpciones = new GridPane();
        gridOpciones.setHgap(20);
        gridOpciones.setVgap(20);
        gridOpciones.setAlignment(Pos.CENTER);

        VBox btnCataleg = crearTarjetaMenu("🏠", "Catàleg d'estances",
                "Navega i cerca estances disponibles", "#3498db");
        btnCataleg.setOnMouseClicked(e -> navegarCataleg(stage));

        VBox btnReserves = crearTarjetaMenu("📅", "Les meves reserves",
                "Consulta i gestiona les teves reserves", "#27ae60");
        btnReserves.setOnMouseClicked(e -> veureReserves(stage));

        VBox btnHistorial = crearTarjetaMenu("📜", "Historial",
                "Consulta reserves passades", "#9b59b6");
        btnHistorial.setOnMouseClicked(e -> veureHistorial(stage));

        VBox btnPerfil = crearTarjetaMenu("👤", "El meu perfil",
                "Actualitza les teves dades", "#e67e22");
        btnPerfil.setOnMouseClicked(e -> gestionarPerfil(stage));

        gridOpciones.add(btnCataleg, 0, 0);
        gridOpciones.add(btnReserves, 1, 0);
        gridOpciones.add(btnHistorial, 0, 1);
        gridOpciones.add(btnPerfil, 1, 1);

        vbox.getChildren().addAll(lblBenvinguda, gridOpciones);

        return vbox;
    }

    private VBox crearTarjetaMenu(String icono, String titulo, String descripcion, String color) {
        VBox tarjeta = new VBox(15);
        tarjeta.setPrefSize(350, 180);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(20));
        tarjeta.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);" +
                        "-fx-cursor: hand;"
        );

        Label lblIcono = new Label(icono);
        lblIcono.setFont(Font.font(50));

        Label lblTitulo = new Label(titulo);
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTitulo.setTextFill(Color.web(color));

        Label lblDescripcion = new Label(descripcion);
        lblDescripcion.setFont(Font.font("Arial", 13));
        lblDescripcion.setTextFill(Color.web("#7f8c8d"));
        lblDescripcion.setWrapText(true);
        lblDescripcion.setAlignment(Pos.CENTER);
        lblDescripcion.setMaxWidth(300);

        tarjeta.setOnMouseEntered(e -> {
            lblTitulo.setTextFill(Color.WHITE);
            lblDescripcion.setTextFill(Color.web("#ecf0f1"));
            tarjeta.setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3);" +
                            "-fx-cursor: hand;"
            );
        });

        tarjeta.setOnMouseExited(e -> {
            lblTitulo.setTextFill(Color.web(color));
            lblDescripcion.setTextFill(Color.web("#7f8c8d"));
            tarjeta.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-background-radius: 10;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);" +
                            "-fx-cursor: hand;"
            );
        });

        tarjeta.getChildren().addAll(lblIcono, lblTitulo, lblDescripcion);

        return tarjeta;
    }

    private void navegarCataleg(Stage stage) {
        try {
            NovaReserva novaReserva = new NovaReserva(correuUsuari);
            novaReserva.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No s'ha pogut obrir el catàleg",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void veureReserves(Stage stage) {
        try {
            PantallaReserves reserves = new PantallaReserves(correuUsuari);
            reserves.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No s'ha pogut obrir les reserves",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void veureHistorial(Stage stage) {
        try {
            PantallaHistoric historic = new PantallaHistoric(correuUsuari);
            historic.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No s'ha pogut obrir l'historial",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void gestionarPerfil(Stage stage) {
        try {
            Perfil perfil = new Perfil(correuUsuari);
            perfil.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No s'ha pogut obrir el perfil",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void comprarCredits(Stage stage) {
        try {
            PantallaCredits pantallaCredits = new PantallaCredits(correuUsuari);
            pantallaCredits.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                    "No s'ha pogut obrir la pantalla de crèdits",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    private void veureNotificacions() {
        Alert notificacions = new Alert(Alert.AlertType.INFORMATION);
        notificacions.setTitle("Notificacions");
        notificacions.setHeaderText("Les teves notificacions");
        notificacions.setContentText(
                "🔔 No tens notificacions noves.\n\n" +
                        "Funcionalitat en desenvolupament:\n" +
                        "- Notificacions de reserves\n" +
                        "- Recordatoris de pagament\n" +
                        "- Novetats d'estances"
        );
        notificacions.showAndWait();
    }

    private void tancarSessio(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Tancar sessió");
        alert.setHeaderText("Estàs segur que vols tancar la sessió?");
        alert.setContentText("Hauràs de tornar a iniciar sessió per accedir.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    PantallaInicial inicial = new PantallaInicial();
                    inicial.start(stage);
                } catch (Exception e) {
                    e.printStackTrace();
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

