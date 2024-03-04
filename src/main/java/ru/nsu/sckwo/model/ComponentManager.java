package ru.nsu.sckwo.model;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class ComponentManager {
    public static final ComponentManager INSTANCE = new ComponentManager();
    private final Map<String, JComponent> components = new HashMap<>();

    private ComponentManager() {
    }

    public void register(@NotNull JComponent component) {
        assert (component.getName() != null);
        String name = component.getName();
        assert (!name.isEmpty());
        assert (!name.isBlank());
        components.put(component.getName(), component);
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public <T extends JComponent> T findById(@NotNull String name, Class<T> type) {
        final JComponent component = components.get(name);
        if (component == null)
            throw new NoSuchElementException("Component with ID " + name + " not found.");
        if (type.isInstance(component)) {
            return (T) component;
        } else {
            throw new IllegalArgumentException("Component with name " + name + " is either not registered or has an incompatible type");
        }
    }

    @NotNull
    public Collection<JComponent> all() {
        return components.values().stream().toList();
    }
}
