package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
public class OptionsPanel extends JPanel {
    private final JSlider penSizeSlider;

    private final JSlider vertexSlider;
    private final JSlider angleSlider;
    private final JSlider externalRadiusSlider;
    private final JSlider internalRadiusSlider;
    private final JSpinner penSizeSpinner;

    private final JSpinner vertexSpinner;
    private final JSpinner angleSpinner;
    private final JSpinner externalRadiusSpinner;
    private final JSpinner internalRadiusSpinner;

    OptionsPanel(@NotNull ApplicationContext context) {
        // TODO: fix hardcode
        setPreferredSize(new Dimension(600, 400));
        //TODO: some other layout???
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        // TODO: fix hardcode
        gbc.insets = new Insets(2, 2, 2, 2);

        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_pen_size", context.properties().locale())), 0, 0, 1, gbc);
        penSizeSlider = createSlider(1, 20, 5, 1);
        addComponent(penSizeSlider, 0, 1, 1, gbc);
        // TODO: fix hardcode
        // Pen
        penSizeSpinner = createSpinner(new SpinnerNumberModel(5, 1, 20, 1), 0, 2, gbc);
        penSizeSpinner.setToolTipText("[1-20]");
        penSizeSlider.setToolTipText("[1-20]");

        // Polygon
        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_regular_vertices_number", context.properties().locale())), 1, 0, 1, gbc);
        vertexSlider = createSlider(3, 16, 5, 1);
        addComponent(vertexSlider, 1, 1, 1, gbc);
        vertexSpinner = createSpinner(new SpinnerNumberModel(5, 3, 16, 1), 1, 2, gbc);
        vertexSlider.setToolTipText("[3-16]");
        vertexSpinner.setToolTipText("[3-16]");

        // Angle
        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_rotation_angle", context.properties().locale())), 2, 0, 1, gbc);
        angleSlider = createSlider(0, 360, 0, 1);
        addComponent(angleSlider, 2, 1, 1, gbc);
        angleSpinner = createSpinner(new SpinnerNumberModel(0, 0, 360, 1), 2, 2, gbc);
        angleSpinner.setToolTipText("[0-360]");
        angleSlider.setToolTipText("[0-360]");

        // External radius
        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_outer_radius", context.properties().locale())), 3, 0, 1, gbc);
        externalRadiusSlider = createSlider(0, 100, 50, 1);
        addComponent(externalRadiusSlider, 3, 1, 1, gbc);
        externalRadiusSpinner = createSpinner(new SpinnerNumberModel(50, 0, 100, 1), 3, 2, gbc);
        externalRadiusSpinner.setToolTipText("[0-100]");
        externalRadiusSlider.setToolTipText("[0-100]");

        // Internal radius
        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_inner_radius", context.properties().locale())), 4, 0, 1, gbc);
        internalRadiusSlider = createSlider(0, 50, 20, 1);
        addComponent(internalRadiusSlider, 4, 1, 1, gbc);
        internalRadiusSpinner = createSpinner(new SpinnerNumberModel(20, 0, 100, 1), 4, 2, gbc);
        internalRadiusSpinner.setToolTipText("[0,100]");
        internalRadiusSlider.setToolTipText("[0,100]");
        setupAllChangeListeners();
    }

    private JSlider createSlider(int min, int max, int value, int majorTickSpacing) {
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setPaintTicks(true);
        return slider;
    }

    // TODO: rename parameters

    private void addComponent(@NotNull Component component, int row, int col, int width, @NotNull GridBagConstraints gbc) {
        // TODO: check fields
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        add(component, gbc);
    }

    private JSpinner createSpinner(@NotNull SpinnerModel model, int row, int col, @NotNull GridBagConstraints gbc) {
        //TODO: check model
        final JSpinner spinner = new JSpinner(model);
        // TODO: think about width parameter (1)
        addComponent(spinner, row, col, 1, gbc);
        return spinner;
    }

    private void setupChangeListener(@NotNull JSlider slider, @NotNull JSpinner spinner) {
        slider.addChangeListener(it -> {
            spinner.setValue(slider.getValue());
        });

        spinner.addChangeListener(it -> {
            slider.setValue((int) spinner.getValue());
        });
    }

    private void setupAllChangeListeners() {
        setupChangeListener(penSizeSlider, penSizeSpinner);
        setupChangeListener(vertexSlider, vertexSpinner);
        setupChangeListener(angleSlider, angleSpinner);
        setupChangeListener(internalRadiusSlider, internalRadiusSpinner);
        setupExternalRadiusChangeListener(externalRadiusSlider, externalRadiusSpinner, internalRadiusSpinner, internalRadiusSlider);
    }

    private void setupExternalRadiusChangeListener(@NotNull JSlider slider, @NotNull JSpinner spinner, @NotNull JSpinner internalRadiusSpinner, @NotNull JSlider internalRadiusSlider) {
        slider.addChangeListener(it -> {
            spinner.setValue(slider.getValue());
            internalRadiusSlider.setMaximum(slider.getValue());
            if (slider.getValue() > (int) internalRadiusSpinner.getValue()) {
                internalRadiusSpinner.setValue(internalRadiusSlider.getValue());
            }
            internalRadiusSpinner.setModel(new SpinnerNumberModel((int) internalRadiusSpinner.getValue(), 0, slider.getValue(), 1));
        });

        spinner.addChangeListener(it -> {
            slider.setValue((int) spinner.getValue());
            internalRadiusSlider.setMaximum(slider.getValue());
            if (slider.getValue() > (int) internalRadiusSpinner.getValue()) {
                internalRadiusSpinner.setValue(internalRadiusSlider.getValue());
            }
            internalRadiusSpinner.setModel(new SpinnerNumberModel((int) internalRadiusSpinner.getValue(), 0, slider.getValue(), 1));
        });
    }


    public int getPenSize() {
        return penSizeSlider.getValue();
    }

    public int getNumOfVertices() {
        return vertexSlider.getValue();
    }

    public int getAngle() {
        return angleSlider.getValue();
    }

    public int getOuterRadius() {
        return externalRadiusSlider.getValue();
    }

    public int getInnerRadius() {
        return internalRadiusSlider.getValue();
    }
}
