package ru.nsu.sckwo.model.resource;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.ResourceBundle;

public class StringResource {

    @NotNull
    public static String loadString(@NotNull String key, @NotNull Locale locale) {
        return ResourceBundle.getBundle("strings", locale).getString(key);
    }
}
