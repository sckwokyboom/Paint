package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

public record ApplicationContext(
        @NotNull
        ApplicationProperties properties,
        @NotNull
        ApplicationComponents components
) {
}
