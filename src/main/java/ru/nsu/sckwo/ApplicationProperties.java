package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public record ApplicationProperties(
        @NotNull
        String windowTitle,
        @NotNull
        Dimension windowSize,
        @NotNull
        Dimension minimumWindowSize,
        int defaultCloseOperation
) {
}
