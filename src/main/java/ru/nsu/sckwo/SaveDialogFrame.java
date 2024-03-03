package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveDialogFrame extends JFrame {
    private final DrawField drawField;

    SaveDialogFrame(@NotNull DrawField drawField) {
        // TODO: bounds? visible?
        this.drawField = drawField;
    }

    public void saveImage() throws IOException {
        // TODO: string resource
        final FileDialog saveDialog = new FileDialog(this, "Save image", FileDialog.SAVE);
        saveDialog.setVisible(true);
        // TODO: сделать остальные расширения и выбор
        final String fileName = saveDialog.getDirectory() + saveDialog.getFile() + ".png";

        final File image = new File(fileName);
        ImageIO.write(drawField.getImage(), "png", image);
//        System.out.println(image.toPath());
        // TODO: save image does not work :(
        System.out.println("SAVE!!!");
    }
}
