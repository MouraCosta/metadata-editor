package com.moura.metadataeditor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import nz.co.rossphillips.thumbnailer.Thumbnailer;

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

        Thumbnailer thumbnailer = new Thumbnailer();
        thumbnailer.setSize(96, 96);

        // Determine file's MIME type
        String filenameString = file.getName();
        String mimeString = getMime(filenameString);

        if (mimeString == null) {
            return null;
        }

        // Get file's thumbnail
        FileInputStream targetFileInputStream = null;
        try {
            targetFileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        byte[] thumbnailBytes = thumbnailer.generateThumbnail(targetFileInputStream, mimeString);
        return new Image(new ByteArrayInputStream(thumbnailBytes));
    }

    private static String getMime(String filename) {
        filename = filename.toLowerCase();
        String mimeString = null;
        if (filename.endsWith(".pdf")) {
            mimeString = "application/pdf";
        } else if (filename.endsWith(".png")) {
            mimeString = "image/png";
        } else if (filename.endsWith(".jpg")) {
            mimeString = "image/jpg";
        } else if (filename.endsWith(".gif")) {
            mimeString = "image/gif";
        } else if (filename.endsWith(".bmp")) {
            mimeString = "image/bmp";
        }

        return mimeString;
    }
}
