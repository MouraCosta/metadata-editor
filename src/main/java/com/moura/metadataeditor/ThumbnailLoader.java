package com.moura.metadataeditor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;
import nz.co.rossphillips.thumbnailer.Thumbnailer;

public class ThumbnailLoader {

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
