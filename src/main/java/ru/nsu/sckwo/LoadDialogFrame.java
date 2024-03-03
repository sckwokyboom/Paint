package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoadDialogFrame extends JFrame {
    @NotNull
    private final DrawField drawField;

    @NotNull
    private final ApplicationContext context;

    LoadDialogFrame(@NotNull DrawField drawField, @NotNull ApplicationContext context) {
        this.drawField = drawField;
        this.context = context;
    }

    public void loadImage() throws IOException {
        final FileDialog loadDialog = new FileDialog(this, StringResource.loadString("dialogue_load_file_title", context.properties().locale()), FileDialog.LOAD);
        loadDialog.setFile("*.png; *.jpg; *.jpeg; *.gif; *.bmp");
        loadDialog.setVisible(true);

        final String fileName = loadDialog.getDirectory() + loadDialog.getFile();
        System.out.println(fileName);
        final File imageFile = new File(fileName);

        if (imageFile.exists()) {
            if (!imageFile.canRead()) {
                JOptionPane.showMessageDialog(this,
                        StringResource.loadString("dialogue_error_loadImage", context.properties().locale()),
                        StringResource.loadString("dialogue_error_loadImage_title", context.properties().locale()),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final BufferedImage loadedImage = ImageIO.read(imageFile);
            System.out.println(imageFile);
            if (loadedImage == null) {
                JOptionPane.showMessageDialog(this,
                        StringResource.loadString("dialogue_error_loadImage", context.properties().locale()),
                        StringResource.loadString("dialogue_error_loadImage_title", context.properties().locale()),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            drawField.setImage(loadedImage);
            drawField.resizeImage(loadedImage.getWidth(), loadedImage.getHeight());
        }
    }
}
