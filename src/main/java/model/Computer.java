package model;

import java.awt.*;
import java.awt.image.BufferedImage;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */


public enum Computer
{
    /*Makes Computter Singleton*/
    INSTANCE;


    /*Robot to communicate to mouse and keyboard*/
    private final Robot bot = createRobot();
    /*Toolkit to get operation system settings*/
    private final Toolkit TOOLKIT = Toolkit.getDefaultToolkit();
    /*Dimension to get main screen size*/
    private final Dimension MAIN_DISPLAY = TOOLKIT.getScreenSize();
    /*Dimension to get size of the fishing area where bobber can appear*/
    private final Dimension BOBBER_DIMENSION = new Dimension((int) (MAIN_DISPLAY.getWidth() - ((MAIN_DISPLAY.getWidth()/100) * 55.21)),
            (int) (MAIN_DISPLAY.getHeight() - ((MAIN_DISPLAY.getHeight()/100) * 51.76)));
    /*Point to get position of the fishing area where bobber can appear*/
    private final Point BOBBER_POINT = new Point((int) ((MAIN_DISPLAY.getWidth()/2) - BOBBER_DIMENSION.getWidth()/2), 1);

    /*color range for the splash color*/
    public static boolean SplashColor(Color pixel)
    {
        assert pixel != null;
        return pixel.getRed() > 200 && pixel.getGreen() > 220 && pixel.getBlue() > 220;

    }
    /*Convert byte color to an object color*/
    public static Color parseByteColor(int color)
    {
        final int alpha = (color >>> 24) & 0xFF;
        final int red = (color >>> 16) & 0xFF;
        final int green = (color >>> 8) & 0xFF;
        final int blue = color & 0xFF;
        return new Color(red, green, blue, alpha);
    }
    /*Check if the color of feather is a match by calling 'isBigger' & 'areClose'*/
    public static boolean isMatch(Color pixel)
    {
        byte red = (byte) pixel.getRed();
        byte green = (byte) pixel.getGreen();
        byte blue = (byte) pixel.getBlue();

        return isBigger(red, green) && isBigger(red, blue) && areClose(blue, green);
    }
    /*Check if the color of the red is 100 bigger than blue/green color*/
    private static boolean isBigger(byte red, byte other)
    {
        final double ColourMultiplier  = 1.0;
        return (red * ColourMultiplier) > other;
    }
    /*Check if the rgb color of blue and green are close to each other*/
    private static boolean areClose(byte color1, byte color2)
    {
        final double ColourClosenessMultiplier  = 2.0;
        double max = Math.max(color1, color2);
        double min = Math.min(color1, color2);

        return min * ColourClosenessMultiplier > max - 20;
    }
    /*Takes a screenshot of the the fishing area on the primary screen*/
    public BufferedImage screenshot()
    {
        Rectangle rectangle = new Rectangle(BOBBER_DIMENSION);
        rectangle.setLocation(BOBBER_POINT);
        return bot.createScreenCapture(rectangle);
    }
    /*create robot to access mouse and keyboard*/
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

    /*return Robot*/
    public Robot getBot()
    {
        return bot;
    }
    /*return Dimension of the entire screen*/
    public Dimension getDisplay()
    {
        return MAIN_DISPLAY;
    }
    /*return Dimension of the fishing area*/
    public Dimension getBobberDisplay()
    {
        return BOBBER_DIMENSION;
    }
}
