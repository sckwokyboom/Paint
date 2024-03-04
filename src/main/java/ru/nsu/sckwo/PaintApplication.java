package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.model.ActionHandlersSetupManager;
import ru.nsu.sckwo.model.ApplicationComponents;
import ru.nsu.sckwo.model.canvas.DrawField;
import ru.nsu.sckwo.model.menubar.MenuBarManager;
import ru.nsu.sckwo.model.resource.StringResource;
import ru.nsu.sckwo.model.toolbar.ToolBarManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

public class PaintApplication extends JFrame {

    @NotNull
    private final ApplicationComponents components;

    PaintApplication(@NotNull ApplicationProperties properties) {
        super(properties.windowTitle());
        setLocale(properties.locale());
        final DrawField field = new DrawField();
        this.setSize(properties.windowSize());
        this.setMinimumSize(properties.minimumWindowSize());
        //noinspection MagicConstant
        this.setDefaultCloseOperation(properties.defaultCloseOperation());
        field.setPreferredSize(this.getSize());

        final JScrollPane scrollPane = new JScrollPane(field);

        components = new ApplicationComponents(
                ToolBarManager.createToolBar(this.getLocale()),
                MenuBarManager.createMenuBar(this.getLocale()),
                scrollPane,
                field
        );

        layoutComponents();

        ApplicationContext context = new ApplicationContext(properties, components);
        new ActionHandlersSetupManager(context).setupActionHandlers(this);

        final CloseDialog closeDialog = new CloseDialog(properties.locale(), this);
        addWindowListener(closeDialog);
    }

    private void layoutComponents() {
        this.setLayout(new BorderLayout());
        this.add(components.scrollPane(), BorderLayout.CENTER);
        this.add(components.toolBar(), BorderLayout.NORTH);
        this.setJMenuBar(components.menuBar());
        this.pack();
        this.setVisible(true);
    }

    static class CloseDialog extends WindowAdapter {
        private final Locale locale;
        private final Component parent;

        CloseDialog(@NotNull Locale locale, @Nullable Component parent) {
            this.locale = locale;
            this.parent = parent;
        }

        @Override
        public void windowClosing(WindowEvent we) {
            if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                    parent,
                    StringResource.loadString("dialogue_close_message", locale),
                    StringResource.loadString("dialogue_close_label", locale),
                    JOptionPane.YES_NO_OPTION
            )) {
                System.exit(0);
            }
        }

    }

    public static void main(String[] args) {
        ApplicationProperties applicationProperties = new ApplicationProperties(
                "Paint",
                new Dimension(640, 480),
                new Dimension(640, 480),
                JFrame.DO_NOTHING_ON_CLOSE,
                Locale.of("en"));
        try {
            SwingUtilities.invokeLater(() ->
                    new PaintApplication(applicationProperties)
            );
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
