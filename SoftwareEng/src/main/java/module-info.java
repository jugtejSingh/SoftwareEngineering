module com.example.softwareeng {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.softwareeng to javafx.fxml;
    exports com.example.softwareeng;
}