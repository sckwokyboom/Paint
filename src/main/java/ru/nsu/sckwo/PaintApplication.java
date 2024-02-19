package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class PaintApplication extends JFrame {
    @NotNull
    private final ApplicationProperties properties;

    PaintApplication(@NotNull ApplicationProperties properties) {
        this.properties = properties;
        final DrawField field = new DrawField();
        // TODO: Подумать о том, может ли setSize() метод менять Dimension, тогда можно код упростить
        this.setSize(properties.windowSize());
        field.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height));

        final JScrollPane scrollPane = new JScrollPane(field);
        // TODO: Почитать про политику скроллпэйнов
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        components = new ApplicationComponents(
                null,
                null,
                scrollPane,
                field
        );
        layoutComponents();
        // TODO: установить обработчики
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        // TODO: почитать про center, north, ...
        this.add(components.scrollPane(), BorderLayout.CENTER);
        // TODO: pack???
        this.pack();
        this.setVisible(true);
    }

    @NotNull
    private final ApplicationComponents components;

    public static void main(String[] args) {
        ApplicationProperties applicationProperties = new ApplicationProperties("Paint", new Dimension(640, 480), new Dimension(640, 480), JFrame.EXIT_ON_CLOSE);
        SwingUtilities.invokeLater(() ->
                new PaintApplication(applicationProperties)
        );
    }
}
