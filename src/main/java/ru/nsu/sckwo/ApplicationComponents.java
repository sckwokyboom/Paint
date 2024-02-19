package ru.nsu.sckwo;

import javax.swing.*;

public record ApplicationComponents(
        JToolBar toolBar,
        JMenuBar menuBar,
        JScrollPane scrollPane,
        DrawField field
) {
}
