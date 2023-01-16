package com.moura.metadataeditor;

import java.io.File;
import java.util.Collections;
import java.util.List;

import co.elastic.thumbnails4j.core.Dimensions;
import co.elastic.thumbnails4j.core.Thumbnailer;
import co.elastic.thumbnails4j.core.ThumbnailingException;
import co.elastic.thumbnails4j.docx.DOCXThumbnailer;
import co.elastic.thumbnails4j.image.ImageThumbnailer;
import co.elastic.thumbnails4j.pdf.PDFThumbnailer;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * Class containing a main utility method for fetching thumbnails from files.
 * 
 * @author De Moura
 */
public class ThumbnailLoader {

    /**
     * Obtains the thumbnail of a file.
     * 
     * For now, there are a few supported file types from which it's possible to
     * fetch a thumbnail. The following file types are:
     * <ul>
     * <li>PDF
     * <li>PNG
     * <li>JPG
     * <li>GIF
     * <li>BMP
     * <li>DOCX
     * </ul>
     * 
     * When the given file type is not in the supported list above, this method
     * will return a generic thumbnail.
     * 
     * @param file The file from which the thumbnail will be fetched
     * @return A Image which is the thumbnail itself
     */
    public static Image generateThumbnail(File file) {
        String filenameString = file.getName();
        Thumbnailer thumbnailer = getThumbnailer(filenameString);
        if (thumbnailer == null) {
            return null;
        }

        // Get file's thumbnail
        List<Dimensions> outputDimensions = Collections.singletonList(new Dimensions(99, 97));
        Image image = null;
        try {
            image = SwingFXUtils.toFXImage(thumbnailer.getThumbnails(file, outputDimensions).get(0), null);
        } catch (ThumbnailingException e) {
            e.printStackTrace();
        }
        return image;
    }

    private static Thumbnailer getThumbnailer(String filename) {
        filename = filename.toLowerCase();
        Thumbnailer thumbnailer = null;
        if (filename.endsWith(".pdf")) {
            thumbnailer = new PDFThumbnailer();
        } else if (filename.endsWith(".png") || filename.endsWith(".jpg") || (filename.endsWith(".bmp"))) {
            String imageType = filename.substring(filename.lastIndexOf('.')+1);
            thumbnailer = new ImageThumbnailer(imageType);
        } else if (filename.endsWith("docx")) {
            thumbnailer = new DOCXThumbnailer();
        } else {
            return null;
        }

        return thumbnailer;
    }
}
