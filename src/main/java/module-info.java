module com.example.practica1_prueba {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.practica1_prueba to javafx.fxml;
    exports com.example.practica1_prueba;
}