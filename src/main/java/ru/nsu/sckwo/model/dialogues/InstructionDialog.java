package ru.nsu.sckwo.model.dialogues;

import org.jetbrains.annotations.NotNull;
import ru.nsu.sckwo.ApplicationContext;
import ru.nsu.sckwo.model.resource.StringResource;

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
        setLocationRelativeTo(parentFrame);


    }
}
