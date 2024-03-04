package ru.nsu.sckwo.model.dialogues;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.model.canvas.DrawField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveDialogFrame extends JFrame {
    private final DrawField drawField;

    public SaveDialogFrame(@NotNull DrawField drawField) {
        this.drawField = drawField;
    }

    public void saveImage() throws IOException {
        final FileDialog saveDialog = new FileDialog(this, "Save image", FileDialog.SAVE);
        saveDialog.setVisible(true);
        final String fileName = saveDialog.getDirectory() + saveDialog.getFile() + ".png";
        final File image = new File(fileName);
        ImageIO.write(drawField.getImage(), "png", image);
    }
}
