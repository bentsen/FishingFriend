package model;

import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;

/*
* Name: Mikkel Bentsen && Oliver rasoli
* Date: 1/16/2022
 */


/** Kyeboard class convert string to keyEvents */
public class Keyboard
{
    /*Robot so we can communicate with hardware*/
    private final Robot robot = Computer.INSTANCE.getBot();

    public void type(String keys) {
        for (int i = 0; i < keys.length(); i++) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(keys.charAt(i));
            if (CHAR_UNDEFINED == keyCode) {
                throw new RuntimeException("Key code not found for " + keys.charAt(i));
            }
            robot.keyPress(keyCode);
            robot.delay(100);
            robot.keyRelease(keyCode);
            robot.delay(100);
        }
    }
}
