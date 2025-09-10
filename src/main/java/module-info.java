module com.example.castledefense {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.castledefense to javafx.fxml;
    exports com.example.castledefense;
}