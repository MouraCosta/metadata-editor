module com.moura.metadataeditor {
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.logging;

    opens com.moura.metadataeditor to javafx.graphics;
    opens com.moura.metadataeditor.components to javafx.fxml;
    opens com.moura.metadataeditor.controllers to javafx.fxml;

    // Resources
    opens images;
    opens views;
    opens utils;

    exports com.moura.metadataeditor;
}
