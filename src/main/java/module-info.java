module com.example.porterstemmergui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.porterstemmergui to javafx.fxml;
    exports com.example.porterstemmergui;
}