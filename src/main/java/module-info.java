module com.example.project1maxledlighting {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project1maxledlighting to javafx.fxml;
    exports com.example.project1maxledlighting;
}