package ru.nsu.sckwo;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class InstructionDialog extends JDialog {
    public InstructionDialog(@NotNull JFrame parentFrame, @NotNull ApplicationContext context) {
        super(parentFrame, StringResource.loadString("dialogue_instruction_title", context.properties().locale()));
        final JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        final String instructionsHtml = StringResource.loadString("dialogue_instruction_content", context.properties().locale());
        textPane.setText(instructionsHtml);
        final JScrollPane scrollPane = new JScrollPane(textPane);
        getContentPane().add(scrollPane);
        setSize(400, 400);
        //TODO: check
        setLocationRelativeTo(parentFrame);


    }
}
