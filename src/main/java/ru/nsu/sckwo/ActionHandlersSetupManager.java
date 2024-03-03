package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.tools.ToolType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class ActionHandlersSetupManager {
    @NotNull
    final Collection<Component> toolBarItems;
    @NotNull
    final ApplicationContext context;

    ActionHandlersSetupManager(@NotNull ApplicationContext context) {
        this.context = context;
        toolBarItems = ComponentManager.INSTANCE.all().stream().filter(component -> {
            // TODO: add menu_tools_button
            return component.getName().startsWith("toolbar_button");
        }).collect(Collectors.toList());
    }

    public void setupActionHandlers(@NotNull PaintApplication application) {
        final JMenuItem exitItem = ComponentManager.INSTANCE.findById("menu_file_button_exit", JMenuItem.class);
        exitItem.addActionListener(it -> {
            // TODO: диалоговое окно
        });

        final JMenuItem saveItem = ComponentManager.INSTANCE.findById("menu_file_button_save_file", JMenuItem.class);
        final SaveDialogFrame saveDialogFrame = new SaveDialogFrame(context.components().field());
        saveItem.addActionListener(it -> {
            try {
                saveDialogFrame.saveImage();
            } catch (IOException e) {
                // TODO: think about exceptions!
                throw new RuntimeException(e);
            }
        });

        final JMenuItem loadItem = ComponentManager.INSTANCE.findById("menu_file_button_open_file", JMenuItem.class);
        // TODO: maybe only locale
        loadItem.addActionListener(it -> {
            try {
                new LoadDialogFrame(context.components().field(), context).loadImage();
            } catch (IOException e) {
                //TODO think about exception!
                throw new RuntimeException(e);
            }
        });

        final JButton penTool = ComponentManager.INSTANCE.findById("toolbar_button_pen", JButton.class);
        penTool.addActionListener(this::toolChooseActionListener);
        penTool.setEnabled(false);

        final JButton starTool = ComponentManager.INSTANCE.findById("toolbar_button_star", JButton.class);
        starTool.addActionListener(this::toolChooseActionListener);

        final JButton polygonTool = ComponentManager.INSTANCE.findById("toolbar_button_polygon", JButton.class);
        polygonTool.addActionListener(this::toolChooseActionListener);

        final JButton lineTool = ComponentManager.INSTANCE.findById("toolbar_button_line", JButton.class);
        lineTool.addActionListener(this::toolChooseActionListener);

        final JButton fillTool = ComponentManager.INSTANCE.findById("toolbar_button_fill", JButton.class);
        fillTool.addActionListener(this::toolChooseActionListener);

        final JButton clearAllTool = ComponentManager.INSTANCE.findById("toolbar_button_clear_all", JButton.class);
        clearAllTool.addActionListener(it -> {
            System.out.println("CLEAR!");
            context.components().field().clearAll();
        });

        // TODO: maybe eraser

        final JButton options = ComponentManager.INSTANCE.findById("toolbar_button_options", JButton.class);
        final OptionsPanel optionsPanel = new OptionsPanel(context);
        options.addActionListener(it -> {
            final int confirm = JOptionPane.showConfirmDialog(
                    application,
                    optionsPanel,
                    StringResource.loadString("dialogue_options_label", context.properties().locale()),
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (JOptionPane.OK_OPTION == confirm) {
                final int penSize = optionsPanel.getPenSize();
                final int outerRadius = optionsPanel.getOuterRadius();
                final int innerRadius = optionsPanel.getInnerRadius();
                final int angle = optionsPanel.getAngle();
                final int numOfVertices = optionsPanel.getNumOfVertices();
                context.components().field().setThickness(penSize);
                context.components().field().setFiguresParameters(angle, numOfVertices, outerRadius, innerRadius);
            }
        });


        final JMenuItem menuOptions = ComponentManager.INSTANCE.findById("menu_tools_button_tune", JMenuItem.class);
        // TODO: read about other listeners in array
        menuOptions.addActionListener(options.getActionListeners()[0]);

        final JButton palletButton = ComponentManager.INSTANCE.findById("toolbar_button_palette", JButton.class);
        palletButton.addActionListener(it -> {
            final Color choosenColor = JColorChooser.showDialog(
                    null, StringResource.loadString("dialogue_pallet_label", context.properties().locale()),
                    Color.BLACK);
            context.components().field().setCurrentColor(choosenColor);
            palletButton.setBackground(choosenColor);
        });

        final JButton chooseRedColor = ComponentManager.INSTANCE.findById("toolbar_button_red", JButton.class);
        chooseRedColor.addActionListener(it -> {
            context.components().field().setCurrentColor(Color.RED);
            palletButton.setBackground(Color.RED);
        });

        final JButton chooseGreenColor = ComponentManager.INSTANCE.findById("toolbar_button_green", JButton.class);
        chooseGreenColor.addActionListener(it -> {
            context.components().field().setCurrentColor(Color.GREEN);
            palletButton.setBackground(Color.GREEN);
        });

        final JButton chooseBlueColor = ComponentManager.INSTANCE.findById("toolbar_button_blue", JButton.class);
        chooseBlueColor.addActionListener(it -> {
            context.components().field().setCurrentColor(Color.BLUE);
            palletButton.setBackground(Color.BLUE);
        });

        final JButton chooseBlackColor = ComponentManager.INSTANCE.findById("toolbar_button_black", JButton.class);
        chooseBlackColor.addActionListener(it -> {
            context.components().field().setCurrentColor(Color.BLACK);
            palletButton.setBackground(Color.BLACK);
        });

        final JButton undo = ComponentManager.INSTANCE.findById("toolbar_button_undo", JButton.class);
        undo.addActionListener(it -> {
            context.components().field().undo();
        });

        // Tools menu
        final JMenuItem clearAll = ComponentManager.INSTANCE.findById("menu_tools_button_clear", JMenuItem.class);
        clearAll.addActionListener(it -> {
            context.components().field().clearAll();
        });

        final JMenuItem pen = ComponentManager.INSTANCE.findById("menu_tools_button_pen", JMenuItem.class);
        pen.addActionListener(this::toolChooseActionListener);

        final JMenuItem line = ComponentManager.INSTANCE.findById("menu_tools_button_line", JMenuItem.class);
        line.addActionListener(this::toolChooseActionListener);

        final JMenuItem polygon = ComponentManager.INSTANCE.findById("menu_tools_button_polygon", JMenuItem.class);
        polygon.addActionListener(this::toolChooseActionListener);

        final JMenuItem fill = ComponentManager.INSTANCE.findById("menu_tools_button_fill", JMenuItem.class);
        fill.addActionListener(this::toolChooseActionListener);

        final JMenuItem star = ComponentManager.INSTANCE.findById("menu_tools_button_star", JMenuItem.class);
        star.addActionListener(this::toolChooseActionListener);

        final JMenuItem fieldSizeChanger = ComponentManager.INSTANCE.findById("menu_tools_button_resize", JMenuItem.class);
        fieldSizeChanger.addActionListener(it -> {
            System.out.println("HELLO!");
            final JPanel resizePanel = ComponentManager.INSTANCE.findById("dialogue_resize", JPanel.class);
            final JSpinner widthSpinner = (JSpinner) resizePanel.getComponent(1);
            final JSpinner heightSpinner = (JSpinner) resizePanel.getComponent(3);

            final int confirmResult = JOptionPane.showConfirmDialog(
                    null,
                    resizePanel,
                    StringResource.loadString("dialogue_resize_label", context.properties().locale()),
                    JOptionPane.OK_CANCEL_OPTION
            );

            final int newWidth = (int) widthSpinner.getValue();
            final int newHeight = (int) heightSpinner.getValue();

            if (JOptionPane.OK_OPTION == confirmResult) {
                context.components().field().resizeImage(newWidth, newHeight);
                context.components().scrollPane().updateUI();
            }
        });

        final JMenuItem about = ComponentManager.INSTANCE.findById("menu_about_button_about", JMenuItem.class);
        about.addActionListener(it -> {
            final InstructionDialog instructionDialog = new InstructionDialog(application, context);
            instructionDialog.setVisible(true);
        });
    }

    public void toolChooseActionListener(@NotNull ActionEvent event) {
        System.out.println(event.getSource());

        // TODO: read about source
        final JComponent button = (JComponent) event.getSource();
        final ToolType choosenToolType = switch (button.getName()) {
            case "toolbar_button_pen", "menu_tools_button_pen" -> ToolType.PEN;
            case "toolbar_button_fill", "menu_tools_button_fill" -> ToolType.FILL;
            case "toolbar_button_line", "menu_tools_button_line" -> ToolType.LINE;
            case "toolbar_button_polygon", "menu_tools_button_polygon" -> ToolType.POLYGON;
            case "toolbar_button_star", "menu_tools_button_star" -> ToolType.STAR;
            // TODO: make message
            default -> {
                throw new IllegalStateException("");
            }
        };
        context.components().field().selectTool(choosenToolType);
        context.components().field().resetAllToolsStates();
        updateSelectedTool(button);
    }

    private void updateSelectedTool(@NotNull Component tool) {
        toolBarItems.forEach(item -> {
            switch (item) {
                case JButton jb -> {
                    jb.setSelected(false);
                    jb.setEnabled(true);
                    if (!jb.getName().contains("red") && !jb.getName().contains("green") && !jb.getName().contains("black") && !jb.getName().contains("blue")) {
//                        jb.setBackground(Color.WHITE);
                    }
                }
                case JMenuItem jmi -> {
                    jmi.setSelected(false);
                }
                default -> {
                    // TODO: is it possible???
                    throw new IllegalStateException("An invalid argument that was not a tool control was passed.");
                }
            }
        });
        final String toolName = switch (tool) {
            case JButton jb -> jb.getName().substring("toolbar_button".length());
            case JMenuItem jmi -> jmi.getName().substring("menu_tools_button".length());
            default -> "";
        };
        toolBarItems
                .stream()
                .filter(item -> item.getName().contains(toolName))
                .forEach(item -> {
                    switch (item) {
                        case JButton jb -> {
                            jb.setSelected(true);
                            jb.setEnabled(false);
//                            jb.setBackground(Color.GRAY);
                        }
                        case JMenuItem jmi -> jmi.setSelected(true);
                        default -> {
                            throw new IllegalStateException("A non-existent tool was selected.");
                        }
                    }
                });
    }


}
