package ru.nsu.sckwo.model.dialogues;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.ApplicationContext;
import ru.nsu.sckwo.model.resource.StringResource;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {
    private final JSlider thicknessSlider;
    private final JSlider vertexSlider;
    private final JSlider angleSlider;
    private final JSlider outerRadiusSlider;
    private final JSlider innerRadiusSlider;
    private final JSpinner penSizeSpinner;

    private final JSpinner vertexSpinner;
    private final JSpinner angleSpinner;
    private final JSpinner externalRadiusSpinner;
    private final JSpinner internalRadiusSpinner;

    public OptionsPanel(@NotNull ApplicationContext context) {
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2);

        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_pen_size", context.properties().locale())), 0, 0, 1, gbc);
        thicknessSlider = createSlider(1, 20, 5, 1);
        addComponent(thicknessSlider, 0, 1, 1, gbc);
        // Pen
        penSizeSpinner = createSpinner(new SpinnerNumberModel(5, 1, 20, 1), 0, 2, gbc);
        penSizeSpinner.setToolTipText("[1-20]");
        thicknessSlider.setToolTipText("[1-20]");

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
        outerRadiusSlider = createSlider(0, 100, 50, 1);
        addComponent(outerRadiusSlider, 3, 1, 1, gbc);
        externalRadiusSpinner = createSpinner(new SpinnerNumberModel(50, 0, 100, 1), 3, 2, gbc);
        externalRadiusSpinner.setToolTipText("[0-100]");
        outerRadiusSlider.setToolTipText("[0-100]");

        // Internal radius
        addComponent(new JLabel(StringResource.loadString("dialogue_options_label_inner_radius", context.properties().locale())), 4, 0, 1, gbc);
        innerRadiusSlider = createSlider(0, 50, 20, 1);
        addComponent(innerRadiusSlider, 4, 1, 1, gbc);
        internalRadiusSpinner = createSpinner(new SpinnerNumberModel(20, 0, 100, 1), 4, 2, gbc);
        internalRadiusSpinner.setToolTipText("[0,100]");
        innerRadiusSlider.setToolTipText("[0,100]");
        setupAllChangeListeners();
    }

    private JSlider createSlider(int min, int max, int value, int majorTickSpacing) {
        final JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing(majorTickSpacing);
        slider.setPaintTicks(true);
        return slider;
    }

    private void addComponent(@NotNull Component component, int row, int col, int width, @NotNull GridBagConstraints gbc) {
        gbc.gridx = col;
        gbc.gridy = row;
        gbc.gridwidth = width;
        add(component, gbc);
    }

    private JSpinner createSpinner(@NotNull SpinnerModel model, int row, int col, @NotNull GridBagConstraints gbc) {
        final JSpinner spinner = new JSpinner(model);
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
        setupChangeListener(thicknessSlider, penSizeSpinner);
        setupChangeListener(vertexSlider, vertexSpinner);
        setupChangeListener(angleSlider, angleSpinner);
        setupChangeListener(innerRadiusSlider, internalRadiusSpinner);
        setupExternalRadiusChangeListener(outerRadiusSlider, externalRadiusSpinner, internalRadiusSpinner, innerRadiusSlider);
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

    public void resetValues(@NotNull OptionsValues options) {
        thicknessSlider.setValue(options.thickness());
        angleSlider.setValue(options.figureRotationAngle());
        vertexSlider.setValue(options.numberOfFigureVertices());
        outerRadiusSlider.setValue(options.outerRadius());
        innerRadiusSlider.setValue(options.innerRadius());
    }

    public int getThickness() {
        return thicknessSlider.getValue();
    }

    public int getNumOfVertices() {
        return vertexSlider.getValue();
    }

    public int getAngle() {
        return angleSlider.getValue();
    }

    public int getOuterRadius() {
        return outerRadiusSlider.getValue();
    }

    public int getInnerRadius() {
        return innerRadiusSlider.getValue();
    }
}
