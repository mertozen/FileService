package com.fileservice.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ThumbnailCreatorUtil {

    private final static String PATH = "/Users/mert/Documents/Projects/FileService/";

    public static String createThumbnail(File file, String filename) {
        String absolutePath = PATH + filename + "_thumbnail";
        try {
            BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(ImageIO.read(file).getScaledInstance(100, 100, Image.SCALE_SMOOTH), 0, 0, null);
            ImageIO.write(img, "jpg", new File(absolutePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return absolutePath;
    }
}
