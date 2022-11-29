module com.moura.metadataeditor {
    requires javafx.fxml;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.logging;

    requires scala.thumbnailer;
    requires scala.reflect;
    requires scala.library;
    requires imgscalr.lib;
    requires commons.io;
    requires utils;
    requires poi.ooxml;
    requires ooxml.schemas;
    requires pdfbox;
    requires jempbox;
    requires commons.logging;
    

    opens com.moura.metadataeditor to javafx.graphics, scala.thumbnailer;
    opens com.moura.metadataeditor.components to javafx.fxml;
    opens com.moura.metadataeditor.controllers to javafx.fxml;

    // Resources
    opens images;
    opens views;
    opens utils;
    opens stylesheets;

    exports com.moura.metadataeditor;
}
