package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.model.ApplicationComponents;

public record ApplicationContext(
        @NotNull
        ApplicationProperties properties,
        @NotNull
        ApplicationComponents components
) {
}
