package ru.nsu.sckwo.model.toolbar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.nsu.sckwo.model.resource.IconResource;

import java.awt.*;

public record ToolBarButtonConfig(
        @NotNull String id,
        @Nullable IconResource icon,
        @NotNull String toolTipKey,
        @Nullable Color background,
        boolean selected
) {
}
