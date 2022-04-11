module com.example.proiectfis {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proiectfis to javafx.fxml;
    exports com.example.proiectfis;
}