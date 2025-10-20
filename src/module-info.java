/**
 * 
 */
/**
 * 
 */
module BOOK4U {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    exports Pantalles;
    opens Pantalles to javafx.graphics, javafx.fxml;
}
