package ru.nsu.sckwo.model;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.model.canvas.DrawField;

import javax.swing.*;

public record ApplicationComponents(
        @NotNull
        JToolBar toolBar,
        @NotNull
        JMenuBar menuBar,
        @NotNull
        JScrollPane scrollPane,
        @NotNull
        DrawField field
) {
}
