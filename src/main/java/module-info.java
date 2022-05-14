module com.example.proiectfis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;
    requires java.sql;


    opens com.example.proiectfis to javafx.fxml;
    exports com.example.proiectfis;
}