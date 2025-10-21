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

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends Application {
    
    private TextField txtCorreu;
    private PasswordField txtContrasenya;
    
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Inici de Sessió");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        VBox contenedorCentral = crearFormularioLogin(primaryStage);
        root.setCenter(contenedorCentral);
        
        Scene scene = new Scene(root, 500, 550);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private VBox crearFormularioLogin(Stage stage) {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(50, 60, 50, 60));
        vbox.setAlignment(Pos.CENTER);
        
        Label lblTitulo = new Label("Book4u");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 42));
        lblTitulo.setTextFill(Color.web("#2980b9"));
        
        Label lblSubtitulo = new Label("Reserva la teva estància ideal");
        lblSubtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 16));
        lblSubtitulo.setTextFill(Color.web("#7f8c8d"));
        
        Region separador = new Region();
        separador.setPrefHeight(30);
        
        Label lblFormulario = new Label("Inici de Sessió");
        lblFormulario.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblFormulario.setTextFill(Color.web("#34495e"));
        
        txtCorreu = new TextField();
        txtContrasenya = new PasswordField();
        
        VBox campoCorreo = crearCampoLogin("Correu electrònic", txtCorreu, "exemple@correu.com");
        VBox campoContrasenya = crearCampoLogin("Contrasenya", txtContrasenya, "Introdueix la teva contrasenya");
        
        Button btnLogin = new Button("Iniciar Sessió");
        btnLogin.setPrefSize(380, 45);
        btnLogin.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle(
            "-fx-background-color: #2980b9;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnLogin.setOnAction(e -> iniciarSesion(stage));
        
        HBox panelRegistro = new HBox(5);
        panelRegistro.setAlignment(Pos.CENTER);
        
        Label lblNoTieneCuenta = new Label("No tens compte?");
        lblNoTieneCuenta.setFont(Font.font("Arial", 13));
        lblNoTieneCuenta.setTextFill(Color.web("#7f8c8d"));
        
        Hyperlink linkRegistro = new Hyperlink("Registra't aquí");
        linkRegistro.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        linkRegistro.setTextFill(Color.web("#3498db"));
        linkRegistro.setBorder(null);
        linkRegistro.setPadding(new Insets(0));
        linkRegistro.setOnAction(e -> abrirRegistro(stage));
        
        panelRegistro.getChildren().addAll(lblNoTieneCuenta, linkRegistro);
        
        vbox.getChildren().addAll(
            lblTitulo,
            lblSubtitulo,
            separador,
            lblFormulario,
            campoCorreo,
            campoContrasenya,
            btnLogin,
            panelRegistro
        );
        
        return vbox;
    }
    
    private VBox crearCampoLogin(String etiqueta, TextField campo, String placeholder) {
        VBox contenedor = new VBox(8);
        contenedor.setAlignment(Pos.CENTER_LEFT);
        
        Label label = new Label(etiqueta);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#34495e"));
        
        campo.setPromptText(placeholder);
        campo.setPrefHeight(45);
        campo.setPrefWidth(380);
        campo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 0 15 0 15;" +
            "-fx-font-size: 14px;"
        );
        
        campo.setOnMouseEntered(e -> campo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #3498db;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 0 15 0 15;" +
            "-fx-font-size: 14px;"
        ));
        
        campo.setOnMouseExited(e -> {
            if (!campo.isFocused()) {
                campo.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #bdc3c7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 0 15 0 15;" +
                    "-fx-font-size: 14px;"
                );
            }
        });
        
        campo.setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                iniciarSesion((Stage) campo.getScene().getWindow());
            }
        });
        
        contenedor.getChildren().addAll(label, campo);
        return contenedor;
    }
    
    private void iniciarSesion(Stage stage) {
        String correu = txtCorreu.getText().trim();
        String contrasenya = txtContrasenya.getText();
        
        if (correu.isEmpty() || contrasenya.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING,
                "Camps buits",
                "Si us plau, omple tots els camps",
                "Has d'introduir el correu i la contrasenya.");
            return;
        }
        
        String contrasenyaEncriptada = encriptarContrasenya(contrasenya);
        
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            mostrarAlerta(Alert.AlertType.ERROR,
                "Error",
                "No hi ha usuaris registrats",
                "Registra't primer per poder iniciar sessió.");
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean usuarioEncontrado = false;
            
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 6) {
                    String correoGuardado = datos[3];
                    String contrasenyaGuardada = datos[5];
                    String estadoUsuario = datos.length >= 9 ? datos[8] : "actiu";
                    
                    if (correoGuardado.equals(correu)) {
                        usuarioEncontrado = true;
                        
                        if (!estadoUsuario.equals("actiu")) {
                            mostrarAlerta(Alert.AlertType.ERROR,
                                "Compte inactiu",
                                "El teu compte està " + estadoUsuario,
                                "Contacta amb l'administrador per més informació.");
                            return;
                        }
                        
                        if (contrasenyaGuardada.equals(contrasenyaEncriptada)) {
                            String nom = datos[0];
                            String cognoms = datos[1];
                            String rol = datos.length >= 8 ? datos[7] : "client";
                            
                            Stage menuStage = new Stage();
                            
                            try {
                                switch (rol.toLowerCase()) {
                                    case "client":
                                        MenuClient menuClient = new MenuClient(nom + " " + cognoms, correu);
                                        menuClient.start(menuStage);
                                        break;
                                        
                                    case "anunciant":
                                        mostrarAlerta(Alert.AlertType.INFORMATION,
                                            "Benvingut/da Anunciant",
                                            "Hola " + nom + " " + cognoms,
                                            "Menú d'anunciant en desenvolupament.");
                                        MenuClient menuClientTemp = new MenuClient(nom + " " + cognoms, correu);
                                        menuClientTemp.start(menuStage);
                                        break;
                                        
                                    case "administrador":
                                        mostrarAlerta(Alert.AlertType.INFORMATION,
                                            "Benvingut/da Administrador",
                                            "Hola " + nom + " " + cognoms,
                                            "Menú d'administrador en desenvolupament.");
                                        MenuClient menuClientTemp2 = new MenuClient(nom + " " + cognoms, correu);
                                        menuClientTemp2.start(menuStage);
                                        break;
                                        
                                    default:
                                        MenuClient menuDefault = new MenuClient(nom + " " + cognoms, correu);
                                        menuDefault.start(menuStage);
                                        break;
                                }
                                
                                stage.close();
                                
                            } catch (Exception ex) {
                                mostrarAlerta(Alert.AlertType.ERROR,
                                    "Error",
                                    "No s'ha pogut obrir el menú",
                                    ex.getMessage());
                                ex.printStackTrace();
                            }
                            
                            return;
                            
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR,
                                "Error d'autenticació",
                                "Contrasenya incorrecta",
                                "La contrasenya no és correcta. Torna-ho a intentar.");
                            return;
                        }
                    }
                }
            }
            
            if (!usuarioEncontrado) {
                mostrarAlerta(Alert.AlertType.ERROR,
                    "Usuari no trobat",
                    "El correu no està registrat",
                    "No existeix cap compte amb aquest correu electrònic.");
            }
            
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                "Error",
                "Error al llegir les dades",
                "No s'ha pogut accedir a la base de dades d'usuaris.");
            e.printStackTrace();
        }
    }
    
    private void abrirRegistro(Stage stage) {
        try {
            registre registre = new registre();
            registre.start(stage);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR,
                "Error",
                "No s'ha pogut obrir el registre",
                e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String encriptarContrasenya(String contrasenya) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contrasenya.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
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

