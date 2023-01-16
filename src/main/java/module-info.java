module com.moura.metadataeditor {
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires java.logging;

    requires thumbnails4j.all;
    requires thumbnails4j.core;
    requires thumbnails4j.doc;
    requires thumbnails4j.docx;
    requires thumbnails4j.image;
    requires thumbnails4j.pdf;
    

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
