package ru.nsu.sckwo.model.menubar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.model.ComponentManager;
import ru.nsu.sckwo.model.resource.StringResource;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.Locale;

public class MenuBarManager {
    private static JMenuItem createMenuItem(@NotNull MenuItemConfig config, @NotNull Locale locale) {
        final String text = StringResource.loadString(config.keyOfTextResource(), locale);
        final JMenuItem menuItem = config.isRadioButton() ? new JRadioButtonMenuItem(text) : new JMenuItem(text);
        menuItem.addActionListener(config.actionListener());
        menuItem.setName(config.id());
        menuItem.setSelected(config.selected());
        return menuItem;
    }

    private static JMenu createMenuWithItems(@NotNull String text, List<MenuItemConfig> items, @NotNull Locale locale, @Nullable ButtonGroup buttonGroup) {
        final JMenu menu = new JMenu(text);
        items.forEach(config -> {
            final JMenuItem menuItem = createMenuItem(config, locale);
            menu.add(menuItem);
            if (buttonGroup != null)
                buttonGroup.add(menuItem);
            ComponentManager.INSTANCE.register(menuItem);
        });
        return menu;
    }

    public static JMenuBar createMenuBar(@NotNull Locale locale) {
        final JMenuBar menuBar = new JMenuBar();
        List<MenuItemConfig> fileMenuItems = List.of(
                new MenuItemConfig("menu_file_button_save_file", "menu_file_button_save_file", null, false, false),
                new MenuItemConfig("menu_file_button_open_file", "menu_file_button_open_file", null, false, false),
                new MenuItemConfig("menu_file_button_exit", "menu_file_button_exit", null, false, false));
        final JMenu fileMenu = createMenuWithItems(
                StringResource.loadString("menu_file_label", locale),
                fileMenuItems,
                locale,
                null);
        menuBar.add(fileMenu);

        List<MenuItemConfig> toolMenuItems = List.of(
                new MenuItemConfig("menu_tools_button_pen", "menu_tools_button_pen", null, true, true),
                new MenuItemConfig("menu_tools_button_line", "menu_tools_button_line", null, true, false),
                new MenuItemConfig("menu_tools_button_polygon", "menu_tools_button_polygon", null, true, false),
                new MenuItemConfig("menu_tools_button_star", "menu_tools_button_star", null, true, false),
                new MenuItemConfig("menu_tools_button_fill", "menu_tools_button_fill", null, true, false),
                new MenuItemConfig("menu_tools_button_eraser", "menu_tools_button_eraser", null, true, false),
                new MenuItemConfig("menu_tools_button_clear", "menu_tools_button_clear", null, false, false)
        );
        final ButtonGroup toolsGroup = new ButtonGroup();
        JMenu toolsMenu = createMenuWithItems(
                StringResource.loadString("menu_tools_label", locale),
                toolMenuItems,
                locale,
                toolsGroup
        );
        menuBar.add(toolsMenu);

        List<MenuItemConfig> settingsMenuItems = List.of(
                new MenuItemConfig("menu_tools_button_resize", "menu_tools_button_resize", null, false, false),
                new MenuItemConfig("menu_tools_button_tune", "menu_tools_button_tune", null, false, false)
        );

        JMenu settingsMenu = createMenuWithItems(
                StringResource.loadString("menu_settings_label", locale),
                settingsMenuItems,
                locale,
                null
        );
        menuBar.add(settingsMenu);

        // About
        final List<MenuItemConfig> aboutMenuItems = List.of(
                new MenuItemConfig("menu_about_button_about", "menu_about_button_about", null, false, false),
                new MenuItemConfig("menu_about_button_author", "menu_about_button_author", null, false, false)
        );
        final JMenu aboutMenu = createMenuWithItems(
                StringResource.loadString("menu_about_label", locale),
                aboutMenuItems,
                locale,
                null
        );
        menuBar.add(aboutMenu);

        // Resize dialogue
        final JPanel resizeDialogue = new JPanel();
        final SpinnerNumberModel widthModel = new SpinnerNumberModel(640, 100, 10000, 1);
        final JSpinner widthFieldSpinner = new JSpinner(widthModel);
        widthFieldSpinner.addChangeListener(e -> {
            int value = (int) widthFieldSpinner.getValue();
            if (value < 100 || value > 10000) {
                JOptionPane.showMessageDialog(null, "Incorrect value! Enter a value between 100 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JComponent widthEditor = widthFieldSpinner.getEditor();
        JFormattedTextField widthTextField = ((JSpinner.DefaultEditor) widthEditor).getTextField();

        widthTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                int value;
                try {
                    value = Integer.parseInt(widthTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect value, non integer! Enter a value between 100 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
                    value = (Integer) widthModel.getMinimum();
                }

                int min = (Integer) widthModel.getMinimum();
                int max = (Integer) widthModel.getMaximum();

                if (value < min || value > max) {
                    JOptionPane.showMessageDialog(null, "Incorrect value! Enter a value between 100 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
                    value = min;
                }

                widthFieldSpinner.setValue(value);
            }
        });


        final SpinnerNumberModel heightModel = new SpinnerNumberModel(480, 100, 10000, 1);
        final JSpinner heightFieldSpinner = new JSpinner(heightModel);
        JComponent heightEditor = heightFieldSpinner.getEditor();
        JFormattedTextField heightTextField = ((JSpinner.DefaultEditor) heightEditor).getTextField();

        heightTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                int value;
                try {
                    value = Integer.parseInt(heightTextField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect value, non integer! Enter a value between 100 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
                    value = (Integer) heightModel.getMinimum();
                }

                int min = (Integer) heightModel.getMinimum();
                int max = (Integer) heightModel.getMaximum();

                if (value < min || value > max) {
                    JOptionPane.showMessageDialog(null, "Incorrect value! Enter a value between 100 and 10000.", "Error", JOptionPane.ERROR_MESSAGE);
                    value = min;
                }

                heightFieldSpinner.setValue(value);
            }
        });
        resizeDialogue.add(new JLabel(StringResource.loadString("dialogue_resize_button_width", locale)));
        resizeDialogue.add(widthFieldSpinner);
        resizeDialogue.add(new JLabel(StringResource.loadString("dialogue_resize_button_height", locale)));
        resizeDialogue.add(heightFieldSpinner);
        resizeDialogue.setName("dialogue_resize");
        ComponentManager.INSTANCE.register(resizeDialogue);

        return menuBar;
    }
}

