package view;

import javafx.scene.control.Label;

public enum Time
{
    /*Makes Time Singleton*/
    INSTANCE;

    /*String of time left in alarm for GUI*/
    private final Label timer = new Label();

    /*Get time string*/
    public Label getLabel() {

        return timer;
    }
    /*Set time string*/
    public void setTime(final String time)
    {
        timer.setText("Relog in: "+ time);
    }
}
