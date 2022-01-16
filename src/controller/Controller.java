package controller;

import model.Alarm;
import model.Angle;

import java.awt.*;
import java.awt.event.InputEvent;

public class Controller {

    public static Alarm alarm = new Alarm();
    public static Angle angle = new Angle();
    public static boolean fishing = true;

    public static void main(String[] args) throws AWTException
    {
        Robot bot = new Robot();
        bot.delay(2000);
        while(true)
        {
            alarm.start();
            while(fishing)
            {
                angle.castLoop();
                bot.delay(2000);
                while (!angle.coordinates()) {
                    angle.coordinates();
                }
                angle.move(angle.bobber);
                while (true) {
                    if (angle.fish()) {
                        System.out.println("jeg klikker");
                        bot.delay(1000);
                        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        angle.move(angle.startLocation);
                        bot.delay(1000);
                        break;
                    }
                }
            }
            alarm.stop();
            angle.reconnect();
        }
    }
}
