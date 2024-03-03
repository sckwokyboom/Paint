package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

public enum IconResource {
    PEN("/icon/pen.png"),
    OPTIONS("/icon/tune.png"),
    CLEAN("/icon/delete.png"),
    ERASER("/icon/eraser.png"),
    LINE("/icon/line.png"),
    STAR("/icon/star.png"),
    FILL("/icon/fill.png"),
    PALETTE("/icon/palette.png"),
    POLYGON("/icon/polygon.png"),
    UNDO("/icon/undo.png");
    private final @NotNull String path;

    IconResource(@NotNull String path) {
        this.path = path;
    }

    public ImageIcon loadIcon() {
        // TODO: change icons
        assert (IconResource.class.getResource(path) != null);
        return new ImageIcon(Objects.requireNonNull(IconResource.class.getResource(path)));
    }
}
