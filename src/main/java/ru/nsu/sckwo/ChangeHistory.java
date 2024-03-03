package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;

public class ChangeHistory {
    private final List<Raster> saves = new ArrayList<>();
    private int savesCount = 0;
    private static int SAVES_COUNT_LIMIT = 100;

    public void saveImage(@NotNull Raster imageToSave) {
        if (savesCount > SAVES_COUNT_LIMIT) {
            saves.removeFirst();
            savesCount--;
        }
        saves.add(imageToSave);
        savesCount++;
        System.out.println(saves);
    }

    @Nullable
    public Raster getLastSave() {
        if (savesCount >= 2) {
            saves.removeLast();
            final Raster lastSave = saves.getLast();
            savesCount--;
            return lastSave;
        } else {
            return null;
        }
    }

}
