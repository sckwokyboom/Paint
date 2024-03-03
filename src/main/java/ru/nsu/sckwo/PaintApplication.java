package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.menubar.MenuBarManager;
import ru.nsu.sckwo.toolbar.ToolBarManager;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class PaintApplication extends JFrame {
    @NotNull
    private final ApplicationProperties properties;
    @NotNull
    private final ApplicationContext context;

    PaintApplication(@NotNull ApplicationProperties properties) {
        setLocale(properties.locale());
        this.properties = properties;
        final DrawField field = new DrawField();
        // TODO: Подумать о том, может ли setSize() метод менять Dimension, тогда можно код упростить
        this.setSize(properties.windowSize());
        field.setPreferredSize(new Dimension(this.getSize().width, this.getSize().height));

        final JScrollPane scrollPane = new JScrollPane(field);
        // TODO: scrollpane politics
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        components = new ApplicationComponents(
                ToolBarManager.createToolBar(this.getLocale()),
                MenuBarManager.createMenuBar(this.getLocale()),
                scrollPane,
                field
        );
        context = new ApplicationContext(properties, components);
        layoutComponents();

        new ActionHandlersSetupManager(context).setupActionHandlers(this);
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(components.scrollPane(), BorderLayout.CENTER);
        this.add(components.toolBar(), BorderLayout.NORTH);
        this.setJMenuBar(components.menuBar());
        // TODO: pack???
        this.pack();
        this.setVisible(true);
    }

    @NotNull
    private final ApplicationComponents components;

    public static void main(String[] args) {
        ApplicationProperties applicationProperties = new ApplicationProperties("Paint", new Dimension(640, 480), new Dimension(640, 480), JFrame.EXIT_ON_CLOSE, Locale.of("en"));
        SwingUtilities.invokeLater(() ->
                new PaintApplication(applicationProperties)
        );
    }
}
