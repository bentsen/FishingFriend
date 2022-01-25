package view;

import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.List;

public enum Console
{
    INSTANCE;

    private List<String> consoleOutput = new ArrayList();

    private StringBuilder output = new StringBuilder();

    private TextArea textArea = new TextArea();

    public TextArea getOutput()
    {
        return textArea;
    }

    public void setConsoleOutput(String outputter)
    {
        consoleOutput.add(outputter);
        String lastElement = consoleOutput.stream().reduce((first, second) -> second).orElse("no last element");
        output.append(lastElement + "\n");
        textArea.setText(output.toString());
    }
}
