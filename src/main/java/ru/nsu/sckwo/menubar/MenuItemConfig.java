package ru.nsu.sckwo.menubar;

import org.jetbrains.annotations.NotNull;

import java.awt.event.ActionListener;

public record MenuItemConfig(
        @NotNull String id,
        @NotNull String keyOfTextResource,

        // TODO: ???
        ActionListener actionListener,
        boolean isRadioButton,
        boolean selected
) {
}
