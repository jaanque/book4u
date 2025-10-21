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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Perfil extends Application {
    
    private String correuUsuari;
    private TextField txtNom;
    private TextField txtCognoms;
    private TextField txtDNI;
    private TextField txtCorreu;
    private TextField txtTelefon;
    private TextField txtAdreca;
    private TextArea txtPreferencies;
    private PasswordField txtContrasenyaActual;
    private PasswordField txtContrasenyaNova;
    private PasswordField txtConfirmarContrasenya;
    private Label lblEstadoCuenta;
    private Label lblDataRegistre;
    private Label lblRol;
    
    private static final String ARCHIVO_USUARIOS = "usuarios.txt";
    
    public Perfil() {
        this("usuari@example.com");
    }
    
    public Perfil(String correu) {
        this.correuUsuari = correu;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book4u - El Meu Perfil");
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");
        
        // Barra superior
        HBox barraSuperior = crearBarraSuperior(primaryStage);
        root.setTop(barraSuperior);
        
        // Contenido central con scroll
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        VBox contenidoCentral = crearContenidoCentral(primaryStage);
        scrollPane.setContent(contenidoCentral);
        
        root.setCenter(scrollPane);
        
        Scene scene = new Scene(root, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Cargar datos del usuario
        cargarDatosUsuario();
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
            "-fx-cursor: hand;" +
            "-fx-padding: 8 15 8 15;"
        );
        btnVolver.setOnAction(e -> {
            // Volver al menú principal
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tornar al menú");
            alert.setHeaderText("Vols tornar al menú principal?");
            alert.setContentText("Els canvis no guardats es perdran.");
            
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        String[] datosPerfil = obtenerDatosUsuario();
                        if (datosPerfil != null) {
                            String nom = datosPerfil[0] + " " + datosPerfil[1];
                            MenuClient menu = new MenuClient(nom, correuUsuari);
                            menu.start(stage);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
        
        Label lblTitulo = new Label("El Meu Perfil");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        lblTitulo.setTextFill(Color.WHITE);
        
        hbox.getChildren().addAll(btnVolver, lblTitulo);
        
        return hbox;
    }
    
    private VBox crearContenidoCentral(Stage stage) {
        VBox vbox = new VBox(25);
        vbox.setPadding(new Insets(40, 80, 40, 80));
        vbox.setAlignment(Pos.TOP_CENTER);
        
        // Tarjeta de información de cuenta
        VBox tarjetaInfo = crearTarjetaInformacion();
        
        // Sección de datos personales
        VBox seccionDatos = crearSeccionDatosPersonales();
        
        // Sección de cambio de contraseña
        VBox seccionContrasenya = crearSeccionCambioContrasenya();
        
        // Sección de preferencias
        VBox seccionPreferencias = crearSeccionPreferencias();
        
        // Botones de acción
        HBox botonesAccion = crearBotonesAccion(stage);
        
        vbox.getChildren().addAll(
            tarjetaInfo,
            seccionDatos,
            seccionContrasenya,
            seccionPreferencias,
            botonesAccion
        );
        
        return vbox;
    }
    
    private VBox crearTarjetaInformacion() {
        VBox tarjeta = new VBox(15);
        tarjeta.setPadding(new Insets(25));
        tarjeta.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );
        
        Label lblTitulo = new Label("Informació del Compte");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(12);
        
        // Rol
        Label lblRolLabel = new Label("Rol:");
        lblRolLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblRol = new Label("Client");
        lblRol.setFont(Font.font("Arial", 14));
        lblRol.setTextFill(Color.web("#3498db"));
        
        // Estado
        Label lblEstadoLabel = new Label("Estat:");
        lblEstadoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblEstadoCuenta = new Label("Actiu");
        lblEstadoCuenta.setFont(Font.font("Arial", 14));
        lblEstadoCuenta.setTextFill(Color.web("#27ae60"));
        
        // Fecha de registro
        Label lblDataLabel = new Label("Registrat des de:");
        lblDataLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lblDataRegistre = new Label("--/--/----");
        lblDataRegistre.setFont(Font.font("Arial", 14));
        
        grid.add(lblRolLabel, 0, 0);
        grid.add(lblRol, 1, 0);
        grid.add(lblEstadoLabel, 2, 0);
        grid.add(lblEstadoCuenta, 3, 0);
        grid.add(lblDataLabel, 0, 1);
        grid.add(lblDataRegistre, 1, 1);
        
        tarjeta.getChildren().addAll(lblTitulo, grid);
        
        return tarjeta;
    }
    
    private VBox crearSeccionDatosPersonales() {
        VBox seccion = new VBox(15);
        seccion.setPadding(new Insets(25));
        seccion.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );
        
        Label lblTitulo = new Label("Dades Personals");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        txtNom = new TextField();
        txtCognoms = new TextField();
        txtDNI = new TextField();
        txtDNI.setEditable(false); // DNI no se puede modificar
        txtDNI.setStyle("-fx-opacity: 0.6;");
        txtCorreu = new TextField();
        txtCorreu.setEditable(false); // Email no se puede modificar
        txtCorreu.setStyle("-fx-opacity: 0.6;");
        txtTelefon = new TextField();
        txtAdreca = new TextField();
        
        seccion.getChildren().addAll(
            lblTitulo,
            crearCampoPerfil("Nom *", txtNom),
            crearCampoPerfil("Cognoms *", txtCognoms),
            crearCampoPerfil("DNI", txtDNI),
            crearCampoPerfil("Correu electrònic", txtCorreu),
            crearCampoPerfil("Telèfon *", txtTelefon),
            crearCampoPerfil("Adreça", txtAdreca)
        );
        
        return seccion;
    }
    
    private VBox crearSeccionCambioContrasenya() {
        VBox seccion = new VBox(15);
        seccion.setPadding(new Insets(25));
        seccion.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );
        
        Label lblTitulo = new Label("Canviar Contrasenya");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        Label lblInfo = new Label("Deixa aquests camps buits si no vols canviar la contrasenya");
        lblInfo.setFont(Font.font("Arial", 12));
        lblInfo.setTextFill(Color.web("#7f8c8d"));
        lblInfo.setStyle("-fx-font-style: italic;");
        
        txtContrasenyaActual = new PasswordField();
        txtContrasenyaNova = new PasswordField();
        txtConfirmarContrasenya = new PasswordField();
        
        seccion.getChildren().addAll(
            lblTitulo,
            lblInfo,
            crearCampoPerfil("Contrasenya actual", txtContrasenyaActual),
            crearCampoPerfil("Contrasenya nova", txtContrasenyaNova),
            crearCampoPerfil("Confirmar contrasenya nova", txtConfirmarContrasenya)
        );
        
        return seccion;
    }
    
    private VBox crearSeccionPreferencias() {
        VBox seccion = new VBox(15);
        seccion.setPadding(new Insets(25));
        seccion.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 10;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);"
        );
        
        Label lblTitulo = new Label("Preferències");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        lblTitulo.setTextFill(Color.web("#2c3e50"));
        
        Label lblInfo = new Label("Indica les teves preferències per a les reserves (tipus d'estança, ubicació, etc.)");
        lblInfo.setFont(Font.font("Arial", 12));
        lblInfo.setTextFill(Color.web("#7f8c8d"));
        
        txtPreferencies = new TextArea();
        txtPreferencies.setPromptText("Ex: Prefereixo estances prop de la platja, amb WiFi...");
        txtPreferencies.setPrefRowCount(4);
        txtPreferencies.setWrapText(true);
        txtPreferencies.setStyle(
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 10;"
        );
        
        seccion.getChildren().addAll(lblTitulo, lblInfo, txtPreferencies);
        
        return seccion;
    }
    
    private VBox crearCampoPerfil(String etiqueta, TextField campo) {
        VBox contenedor = new VBox(5);
        
        Label label = new Label(etiqueta);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web("#34495e"));
        
        campo.setPrefHeight(40);
        campo.setStyle(
            "-fx-background-color: white;" +
            "-fx-border-color: #bdc3c7;" +
            "-fx-border-radius: 5;" +
            "-fx-background-radius: 5;" +
            "-fx-padding: 0 10 0 10;" +
            "-fx-font-size: 14px;"
        );
        
        contenedor.getChildren().addAll(label, campo);
        return contenedor;
    }
    
    private HBox crearBotonesAccion(Stage stage) {
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20, 0, 20, 0));
        
        Button btnGuardar = new Button("Guardar Canvis");
        btnGuardar.setPrefSize(200, 45);
        btnGuardar.setStyle(
            "-fx-background-color: #27ae60;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnGuardar.setOnAction(e -> guardarCambios());
        
        Button btnCancelar = new Button("Cancel·lar");
        btnCancelar.setPrefSize(200, 45);
        btnCancelar.setStyle(
            "-fx-background-color: #95a5a6;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnCancelar.setOnAction(e -> cargarDatosUsuario());
        
        Button btnEliminar = new Button("Eliminar Compte");
        btnEliminar.setPrefSize(200, 45);
        btnEliminar.setStyle(
            "-fx-background-color: #e74c3c;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 15px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 5;" +
            "-fx-cursor: hand;"
        );
        btnEliminar.setOnAction(e -> eliminarCuenta(stage));
        
        hbox.getChildren().addAll(btnGuardar, btnCancelar, btnEliminar);
        
        return hbox;
    }
    
    private void cargarDatosUsuario() {
        String[] datos = obtenerDatosUsuario();
        if (datos != null) {
            txtNom.setText(datos[0]);
            txtCognoms.setText(datos[1]);
            txtDNI.setText(datos[2]);
            txtCorreu.setText(datos[3]);
            txtTelefon.setText(datos[4]);
            txtAdreca.setText(datos.length > 6 ? datos[6] : "");
            
            String rol = datos.length > 7 ? datos[7] : "client";
            lblRol.setText(rol.substring(0, 1).toUpperCase() + rol.substring(1));
            
            String estado = datos.length > 8 ? datos[8] : "actiu";
            lblEstadoCuenta.setText(estado.substring(0, 1).toUpperCase() + estado.substring(1));
            lblEstadoCuenta.setTextFill(estado.equals("actiu") ? Color.web("#27ae60") : Color.web("#e74c3c"));
            
            String fecha = datos.length > 9 ? datos[9] : "";
            if (!fecha.isEmpty()) {
                try {
                    String[] partes = fecha.split("T");
                    lblDataRegistre.setText(partes[0]);
                } catch (Exception e) {
                    lblDataRegistre.setText(fecha);
                }
            }
            
            // Cargar preferencias si existen
            if (datos.length > 10 && !datos[10].isEmpty()) {
                txtPreferencies.setText(datos[10]);
            }
        }
    }
    
    private String[] obtenerDatosUsuario() {
        File archivo = new File(ARCHIVO_USUARIOS);
        if (!archivo.exists()) return null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 4 && datos[3].equals(correuUsuari)) {
                    return datos;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private void guardarCambios() {
        // Validar campos obligatorios
        if (txtNom.getText().trim().isEmpty() || 
            txtCognoms.getText().trim().isEmpty() ||
            txtTelefon.getText().trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Camps buits", 
                "Omple tots els camps obligatoris",
                "El nom, cognoms i telèfon són obligatoris.");
            return;
        }
        
        // Validar teléfono
        if (!txtTelefon.getText().trim().matches("\\d{9}")) {
            mostrarAlerta(Alert.AlertType.WARNING, "Telèfon invàlid",
                "El telèfon no és vàlid",
                "El telèfon ha de tenir 9 dígits.");
            return;
        }
        
        // Verificar si hay cambio de contraseña
        String contrasenyaActual = txtContrasenyaActual.getText();
        String contrasenyaNova = txtContrasenyaNova.getText();
        String confirmarContrasenya = txtConfirmarContrasenya.getText();
        
        String nuevaContrasenyaEncriptada = null;
        
        if (!contrasenyaActual.isEmpty() || !contrasenyaNova.isEmpty() || !confirmarContrasenya.isEmpty()) {
            // Validar cambio de contraseña
            if (contrasenyaActual.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Contrasenya actual",
                    "Falta la contrasenya actual",
                    "Has d'introduir la contrasenya actual per canviar-la.");
                return;
            }
            
            // Verificar contraseña actual
            String[] datosActuales = obtenerDatosUsuario();
            if (datosActuales != null) {
                String contrasenyaActualEncriptada = encriptarContrasenya(contrasenyaActual);
                if (!datosActuales[5].equals(contrasenyaActualEncriptada)) {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error",
                        "Contrasenya incorrecta",
                        "La contrasenya actual no és correcta.");
                    return;
                }
            }
            
            // Validar nueva contraseña
            if (!validarContrasenya(contrasenyaNova)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Contrasenya insegura",
                    "La nova contrasenya no compleix els requisits",
                    "Mínim 8 caràcters, 1 majúscula, 1 minúscula i 1 número.");
                return;
            }
            
            if (!contrasenyaNova.equals(confirmarContrasenya)) {
                mostrarAlerta(Alert.AlertType.WARNING, "Error",
                    "Les contrasenyes no coincideixen",
                    "La nova contrasenya i la confirmació han de ser iguals.");
                return;
            }
            
            nuevaContrasenyaEncriptada = encriptarContrasenya(contrasenyaNova);
        }
        
        // Actualizar datos en el archivo
        try {
            List<String> lineas = new ArrayList<>();
            boolean actualizado = false;
            
            BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS));
            String linea;
            
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 4 && datos[3].equals(correuUsuari)) {
                    // Actualizar datos
                    datos[0] = txtNom.getText().trim();
                    datos[1] = txtCognoms.getText().trim();
                    datos[4] = txtTelefon.getText().trim();
                    datos[6] = txtAdreca.getText().trim();
                    
                    // Actualizar contraseña si hay cambio
                    if (nuevaContrasenyaEncriptada != null) {
                        datos[5] = nuevaContrasenyaEncriptada;
                    }
                    
                    // Actualizar o añadir preferencias
                    if (datos.length > 10) {
                        datos[10] = txtPreferencies.getText().trim();
                    } else {
                        // Extender array si es necesario
                        String[] nuevosDatos = new String[11];
                        System.arraycopy(datos, 0, nuevosDatos, 0, datos.length);
                        nuevosDatos[10] = txtPreferencies.getText().trim();
                        datos = nuevosDatos;
                    }
                    
                    linea = String.join("|", datos);
                    actualizado = true;
                }
                lineas.add(linea);
            }
            reader.close();
            
            if (actualizado) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS));
                for (String l : lineas) {
                    writer.write(l);
                    writer.newLine();
                }
                writer.close();
                
                mostrarAlerta(Alert.AlertType.INFORMATION, "Èxit",
                    "Perfil actualitzat",
                    "Els teus canvis s'han guardat correctament.");
                
                // Limpiar campos de contraseña
                txtContrasenyaActual.clear();
                txtContrasenyaNova.clear();
                txtConfirmarContrasenya.clear();
            }
            
        } catch (IOException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                "No s'han pogut guardar els canvis",
                e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void eliminarCuenta(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar compte");
        alert.setHeaderText("Estàs segur que vols eliminar el teu compte?");
        alert.setContentText("Aquesta acció no es pot desfer. Perdràs totes les teves dades i reserves.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Pedir contraseña para confirmar
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Confirmar eliminació");
                dialog.setHeaderText("Introdueix la teva contrasenya per confirmar");
                dialog.setContentText("Contrasenya:");
                
                dialog.showAndWait().ifPresent(password -> {
                    String[] datosActuales = obtenerDatosUsuario();
                    if (datosActuales != null) {
                        String passwordEncriptada = encriptarContrasenya(password);
                        if (datosActuales[5].equals(passwordEncriptada)) {
                            // Eliminar cuenta
                            try {
                                List<String> lineas = new ArrayList<>();
                                BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_USUARIOS));
                                String linea;
                                
                                while ((linea = reader.readLine()) != null) {
                                    String[] datos = linea.split("\\|");
                                    if (!datos[3].equals(correuUsuari)) {
                                        lineas.add(linea);
                                    }
                                }
                                reader.close();
                                
                                BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS));
                                for (String l : lineas) {
                                    writer.write(l);
                                    writer.newLine();
                                }
                                writer.close();
                                
                                mostrarAlerta(Alert.AlertType.INFORMATION, "Compte eliminat",
                                    "El teu compte s'ha eliminat",
                                    "Esperem veure't aviat!");
                                
                                // Volver a la pantalla inicial
                                PantallaInicial inicial = new PantallaInicial();
                                inicial.start(stage);
                                
                            } catch (Exception e) {
                                mostrarAlerta(Alert.AlertType.ERROR, "Error",
                                    "No s'ha pogut eliminar el compte",
                                    e.getMessage());
                            }
                        } else {
                            mostrarAlerta(Alert.AlertType.ERROR, "Error",
                                "Contrasenya incorrecta",
                                "La contrasenya no és correcta.");
                        }
                    }
                });
            }
        });
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
