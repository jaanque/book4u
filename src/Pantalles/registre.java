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
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class registre extends Application {
    
    private TextField txtNom;
    private TextField txtCognoms;
    private TextField txtDNI;
    private TextField txtCorreu;
    private TextField txtTelefon;
    private PasswordField txtContrasenya;
    private PasswordField txtConfirmarContrasenya;
    private TextField txtAdreca;
    
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - Registre d'Usuari");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        VBox formulario = crearFormulario();
        root.setCenter(formulario);
        
        HBox panelBotones = crearPanelBotones(primaryStage);
        root.setBottom(panelBotones);
        
        Scene scene = new Scene(root, 600, 750);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private VBox crearFormulario() {
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(30, 50, 30, 50));
        vbox.setAlignment(Pos.TOP_CENTER);
        
        Label lblTitulo = new Label("Registre de Nou Usuari");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        lblTitulo.setTextFill(Color.web("#2980b9"));
        
        Label lblSubtitulo = new Label("Ompleta el formulari per crear el teu compte a Book4u");
        lblSubtitulo.setFont(Font.font("Arial", 13));
        lblSubtitulo.setTextFill(Color.web("#7f8c8d"));
        
        txtNom = new TextField();
        txtCognoms = new TextField();
        txtDNI = new TextField();
        txtCorreu = new TextField();
        txtTelefon = new TextField();
        txtContrasenya = new PasswordField();
        txtConfirmarContrasenya = new PasswordField();
        txtAdreca = new TextField();
        
        vbox.getChildren().addAll(
            lblTitulo,
            lblSubtitulo,
            crearCampoFormulario("Nom *", txtNom, "Introdueix el teu nom"),
            crearCampoFormulario("Cognoms *", txtCognoms, "Introdueix els teus cognoms"),
            crearCampoFormulario("DNI *", txtDNI, "12345678A"),
            crearCampoFormulario("Correu electrònic *", txtCorreu, "exemple@correu.com"),
            crearCampoFormulario("Telèfon *", txtTelefon, "612345678"),
            crearCampoFormulario("Contrasenya *", txtContrasenya, "Mínim 8 caràcters"),
            crearCampoFormulario("Confirmar contrasenya *", txtConfirmarContrasenya, "Repeteix la contrasenya"),
            crearCampoFormulario("Adreça", txtAdreca, "Carrer, número, ciutat (opcional)")
        );
        
        return vbox;
    }
    
    private VBox crearCampoFormulario(String etiqueta, TextField campo, String placeholder) {
        VBox contenedor = new VBox(5);
        
        Label label = new Label(etiqueta);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#34495e"));
        
        campo.setPromptText(placeholder);
        campo.setPrefHeight(40);
        campo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 0 10 0 10;" +
            "-fx-font-size: 13px;"
        );
        
        campo.setOnMouseEntered(e -> campo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #3498db;" +
            "-fx-border-width: 2;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 0 10 0 10;" +
            "-fx-font-size: 13px;"
        ));
        
        campo.setOnMouseExited(e -> {
            if (!campo.isFocused()) {
                campo.setStyle(
                    "-fx-background-color: white;" +
                    "-fx-border-color: #bdc3c7;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;" +
                    "-fx-padding: 0 10 0 10;" +
                    "-fx-font-size: 13px;"
                );
            }
        });
        
        contenedor.getChildren().addAll(label, campo);
        return contenedor;
    }
    
    private HBox crearPanelBotones(Stage stage) {
        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(20, 50, 30, 50));
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #f5f5f5;");
        
        Button btnRegistrar = new Button("Registrar-se");
        btnRegistrar.setPrefSize(180, 45);
        btnRegistrar.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        
        btnRegistrar.setOnMouseEntered(e -> btnRegistrar.setStyle(
            "-fx-background-color: #2980b9;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnRegistrar.setOnMouseExited(e -> btnRegistrar.setStyle(
            "-fx-background-color: #3498db;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnRegistrar.setOnAction(e -> registrarUsuario(stage));
        
        Button btnCancelar = new Button("Cancel·lar");
        btnCancelar.setPrefSize(180, 45);
        btnCancelar.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        
        btnCancelar.setOnMouseEntered(e -> btnCancelar.setStyle(
            "-fx-background-color: #c0392b;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnCancelar.setOnMouseExited(e -> btnCancelar.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        ));
        
        btnCancelar.setOnAction(e -> cancelar(stage));
        
        hbox.getChildren().addAll(btnRegistrar, btnCancelar);
        return hbox;
    }
    
    private void registrarUsuario(Stage stage) {
        if (!validarCampos()) {
            return;
        }
        
        String nom = txtNom.getText().trim();
        String cognoms = txtCognoms.getText().trim();
        String dni = txtDNI.getText().trim().toUpperCase();
        String correu = txtCorreu.getText().trim();
        String telefon = txtTelefon.getText().trim();
        String contrasenya = txtContrasenya.getText();
        String adreca = txtAdreca.getText().trim();
        
        if (usuarioExiste(dni, correu)) {
            return;
        }
        
        String contrasenyaEncriptada = encriptarContrasenya(contrasenya);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            String linea = String.format("%s|%s|%s|%s|%s|%s|%s|client|actiu|%s",
                nom, cognoms, dni, correu, telefon, contrasenyaEncriptada, 
                adreca, LocalDateTime.now());
            
            writer.write(linea);
            writer.newLine();
            
            mostrarAlerta(Alert.AlertType.INFORMATION, 
                "Registre exitós", 
                "Registre completat amb èxit!",
                "Usuari: " + nom + " " + cognoms + "\nDNI: " + dni + "\nCorreu: " + correu +
                "\n\nJa pots iniciar sessió a Book4u.");
            
            limpiarCampos();
            
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, 
                "Error", 
                "Error al guardar l'usuari",
                "No s'ha pogut guardar l'usuari: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private boolean usuarioExiste(String dni, String correu) {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) {
            return false;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 4) {
                    String dniGuardado = datos[2];
                    String correoGuardado = datos[3];
                    
                    if (dniGuardado.equals(dni)) {
                        mostrarAlerta(Alert.AlertType.ERROR, 
                            "Error de registre", 
                            "DNI duplicat",
                            "El DNI ja està registrat al sistema.");
                        return true;
                    }
                    
                    if (correoGuardado.equals(correu)) {
                        mostrarAlerta(Alert.AlertType.ERROR, 
                            "Error de registre", 
                            "Correu duplicat",
                            "El correu electrònic ja està registrat al sistema.");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    private boolean validarCampos() {
        if (txtNom.getText().trim().isEmpty() || 
            txtCognoms.getText().trim().isEmpty() ||
            txtDNI.getText().trim().isEmpty() ||
            txtCorreu.getText().trim().isEmpty() ||
            txtTelefon.getText().trim().isEmpty() ||
            txtContrasenya.getText().isEmpty() ||
            txtConfirmarContrasenya.getText().isEmpty()) {
            
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Camps incomplets", 
                "Si us plau, omple tots els camps obligatoris",
                "Tots els camps marcats amb * són obligatoris.\n(L'adreça és opcional)");
            return false;
        }
        
        String dni = txtDNI.getText().trim();
        if (!validarDNI(dni)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "DNI invàlid", 
                "El format del DNI no és vàlid",
                "El DNI ha de tenir 8 dígits seguits d'una lletra.\nExemple: 12345678A");
            return false;
        }
        
        String correu = txtCorreu.getText().trim();
        if (!validarEmail(correu)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Correu invàlid", 
                "El format del correu electrònic no és vàlid",
                "Introdueix un correu vàlid.\nExemple: usuari@exemple.com");
            return false;
        }
        
        String telefon = txtTelefon.getText().trim();
        if (!telefon.matches("\\d{9}")) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Telèfon invàlid", 
                "El format del telèfon no és vàlid",
                "El telèfon ha de tenir exactament 9 dígits.");
            return false;
        }
        
        String contrasenya = txtContrasenya.getText();
        if (!validarContrasenya(contrasenya)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Contrasenya insegura", 
                "La contrasenya no compleix els requisits",
                "La contrasenya ha de tenir:\n• Mínim 8 caràcters\n• Almenys una lletra majúscula\n• Almenys una lletra minúscula\n• Almenys un número");
            return false;
        }
        
        String confirmarContrasenya = txtConfirmarContrasenya.getText();
        if (!contrasenya.equals(confirmarContrasenya)) {
            mostrarAlerta(Alert.AlertType.WARNING, 
                "Error de contrasenya", 
                "Les contrasenyes no coincideixen",
                "Assegura't que les dues contrasenyes siguin idèntiques.");
            return false;
        }
        
        return true;
    }
    
    private boolean validarDNI(String dni) {
        String regex = "^[0-9]{8}[A-Z]$";
        if (!dni.toUpperCase().matches(regex)) {
            return false;
        }
        
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letra = dni.toUpperCase().charAt(8);
        return letras.charAt(numero % 23) == letra;
    }
    
    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }
    
    private boolean validarContrasenya(String contrasenya) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(regex, contrasenya);
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
    
    private void limpiarCampos() {
        txtNom.clear();
        txtCognoms.clear();
        txtDNI.clear();
        txtCorreu.clear();
        txtTelefon.clear();
        txtContrasenya.clear();
        txtConfirmarContrasenya.clear();
        txtAdreca.clear();
        txtNom.requestFocus();
    }
    
    private void cancelar(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar cancel·lació");
        alert.setHeaderText("Estàs segur que vols cancel·lar el registre?");
        alert.setContentText("Es perdran totes les dades introduïdes.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                limpiarCampos();
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

