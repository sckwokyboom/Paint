package ru.nsu.sckwo.model.menubar;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionListener;

public record MenuItemConfig(
        @NotNull String id,
        @NotNull String keyOfTextResource,
        ActionListener actionListener,
        boolean isRadioButton,
        boolean selected
) {
}
