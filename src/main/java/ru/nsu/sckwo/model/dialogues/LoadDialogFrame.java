package ru.nsu.sckwo.model.dialogues;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.model.canvas.DrawField;
import ru.nsu.sckwo.model.resource.StringResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class LoadDialogFrame extends JFrame {
    @NotNull
    private final DrawField drawField;

    @NotNull
    private final Locale locale;

    public LoadDialogFrame(@NotNull DrawField drawField, @NotNull Locale locale) {
        this.drawField = drawField;
        this.locale = locale;
    }

    public void loadImage() throws IOException {
        final FileDialog loadDialog = new FileDialog(this, StringResource.loadString("dialogue_load_file_title", locale), FileDialog.LOAD);
        loadDialog.setFile("*.png; *.jpg; *.jpeg; *.gif; *.bmp");
        loadDialog.setVisible(true);

        final String fileName = loadDialog.getDirectory() + loadDialog.getFile();
        System.out.println(fileName);
        final File imageFile = new File(fileName);

        if (imageFile.exists()) {
            if (!imageFile.canRead()) {
                JOptionPane.showMessageDialog(this,
                        StringResource.loadString("dialogue_error_loadImage", locale),
                        StringResource.loadString("dialogue_error_loadImage_title", locale),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            final BufferedImage loadedImage = ImageIO.read(imageFile);
            System.out.println(imageFile);
            if (loadedImage == null) {
                JOptionPane.showMessageDialog(this,
                        StringResource.loadString("dialogue_error_loadImage", locale),
                        StringResource.loadString("dialogue_error_loadImage_title", locale),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            drawField.setImage(loadedImage);
            drawField.resizeImage(loadedImage.getWidth(), loadedImage.getHeight());
        }
    }
}
