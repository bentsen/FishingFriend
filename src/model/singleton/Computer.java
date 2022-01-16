package model.singleton;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */


public enum Computer
{

    INSTANCE;

    private final Robot bot = createRobot();

    private final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();

    private final Clipboard CLIPBOARD = TOOLKIT.getSystemClipboard();

    private final Dimension MAIN_DISPLAY = TOOLKIT.getScreenSize();

    private final int screenWidth = (int)MAIN_DISPLAY.getWidth();

    private final int screenHeight = (int)MAIN_DISPLAY.getHeight();


    public static Color parseByteColor(int color)
    {
        final int alpha = (color >>> 24) & 0xFF;
        final int red = (color >>> 16) & 0xFF;
        final int green = (color >>> 8) & 0xFF;
        final int blue = color & 0xFF;
        return new Color(red, green, blue, alpha);
    }

    public BufferedImage screenshot()
    {
        return bot.createScreenCapture(new Rectangle(MAIN_DISPLAY));
    }

    private Robot createRobot()
    {
        Robot bot = null;
        try
        {
            bot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
        return bot;
    }

    public Robot getBot()
    {
        return bot;
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }
}
