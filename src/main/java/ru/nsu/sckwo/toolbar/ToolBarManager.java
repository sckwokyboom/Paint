package ru.nsu.sckwo.toolbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.ComponentManager;
import ru.nsu.sckwo.IconResource;
import ru.nsu.sckwo.StringResource;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class ToolBarManager {
    private ToolBarManager() {
    }

    private static JButton buildToolBarButton(@NotNull String id, @Nullable Icon icon, @NotNull String toolTipText, @Nullable Color background, boolean selected) {
        // TODO: hardcode
        final Dimension size = new Dimension(52, 52);
        final JButton button = new JButton();
        button.setName(id);
        button.setBackground(background);
        button.setIcon(icon);
        button.setSelected(selected);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setToolTipText(toolTipText);
        button.setBorderPainted(false);
        button.setOpaque(true);
        //TODO: check properties
        button.setFocusPainted(false);
        return button;
    }

    public static JToolBar createToolBar(@NotNull Locale locale) {
        final JToolBar toolBar = new JToolBar();
        //TODO: check parameters
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setVisible(true);

        //TODO: think about color with icons
        List<ToolBarButtonConfig> toolBarButtonConfigList = List.of(
                new ToolBarButtonConfig("toolbar_button_red", null, "toolbar_button_hint_red", Color.RED, false),
                new ToolBarButtonConfig("toolbar_button_green", null, "toolbar_button_hint_green", Color.GREEN, false),
                new ToolBarButtonConfig("toolbar_button_blue", null, "toolbar_button_hint_blue", Color.BLUE, false),
                new ToolBarButtonConfig("toolbar_button_black", null, "toolbar_button_hint_black", Color.BLACK, false),
                new ToolBarButtonConfig("toolbar_button_palette", IconResource.PALETTE, "toolbar_button_hint_palette", null, false),
                new ToolBarButtonConfig("toolbar_button_undo", IconResource.UNDO, "toolbar_button_hint_undo", null, false),
//                new ToolBarButtonConfig("toolbar_button_eraser", IconResource.ERASER, "toolbar_button_hint_eraser", null, false),
                new ToolBarButtonConfig("toolbar_button_pen", IconResource.PEN, "toolbar_button_hint_pen", null, true),
                new ToolBarButtonConfig("toolbar_button_line", IconResource.LINE, "toolbar_button_hint_line", null, false),
                new ToolBarButtonConfig("toolbar_button_fill", IconResource.FILL, "toolbar_button_hint_fill", null, false),
                new ToolBarButtonConfig("toolbar_button_polygon", IconResource.POLYGON, "toolbar_button_hint_polygon", null, false),
                new ToolBarButtonConfig("toolbar_button_star", IconResource.STAR, "toolbar_button_hint_star", null, false),
                new ToolBarButtonConfig("toolbar_button_options", IconResource.OPTIONS, "toolbar_button_hint_options", null, false),
                new ToolBarButtonConfig("toolbar_button_clear_all", IconResource.CLEAN, "toolbar_button_hint_clear_all", null, false)
        );

        toolBarButtonConfigList.forEach(config -> {
            // TODO: check icons
            Icon icon = null;
            final IconResource iconRes = config.icon();
            if (iconRes != null)
                icon = iconRes.loadIcon();
            final String toolTip = StringResource.loadString(config.toolTipKey(), locale);
            final JButton button = buildToolBarButton(config.id(), icon, toolTip, config.background(), config.selected());
            toolBar.add(button);
            ComponentManager.INSTANCE.register(button);
        });

        return toolBar;
    }
}
