package com.company;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Main  {

    public static void main(String[] args) throws AWTException
    {
        Robot bot = new Robot();
        bot.delay(2000);

        while (true) {
            bot.delay(1000);
            move(coordinates());
            bot.delay(1000);
            if(coordinates().getX() == 0 || coordinates().getY() == 0)
            {
                bot.delay(1500);
                bot.keyPress(KeyEvent.VK_4);
                bot.keyRelease(KeyEvent.VK_4);
            }
            waitingLoop:
            while(coordinates().getX() != 0 || coordinates().getY() != 0)
            {
                move(coordinates());
                if (fish()) {
                    System.out.println("jeg klikker");
                    bot.delay(1000);
                    bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

                    bot.delay(1500);
                    bot.keyPress(KeyEvent.VK_4);
                    bot.keyRelease(KeyEvent.VK_4);
                    break waitingLoop;
                }
            }
        }
    }


    public static void move(Point points) throws AWTException {
        Robot bot = new Robot();
        Point coordinates = points;

            if(coordinates.getX() != 0 || coordinates.getY() != 0)
            {
                bot.mouseMove((int) coordinates.getX(), (int) coordinates.getY());
            }
    }

    public static boolean fish() throws AWTException
    {
        Robot bot = new Robot();
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point mousePoint = pointerInfo.getLocation();
        int x = (int) mousePoint.getX();
        int y = (int) mousePoint.getY();

        Rectangle rectangle = new Rectangle(x,y,200,200);
        BufferedImage bufferedImage = bot.createScreenCapture(rectangle);

        int width = 200;
        int height = 200;

        for (int i = 0; i < width; i++) {

            for (int j = 0; j < height; j++) {

                if(bobberBounce(parseByteColor(bufferedImage.getRGB(i,j))))
                {
                    System.out.println("plask!");
                    return true;
                }
            }
        }
        return false;
    }


    public static Point coordinates() throws AWTException {
        Robot bot = new Robot();
        Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage bufferedImage = bot.createScreenCapture(captureSize);

        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                if(colorMatch(parseByteColor(bufferedImage.getRGB(i,j))))
                {
                    return new Point(i,j);
                }
            }
        }
        System.out.println("Color not detected!");
        return new Point(0,0);
    }

    public static Color parseByteColor(int color)
    {
        final int alpha = (color >>> 24) & 0xFF;
        final int red = (color >>> 16) & 0xFF;
        final int green = (color >>> 8) & 0xFF;
        final int blue = color & 0xFF;
        return new Color(red, green, blue, alpha);
    }


    public static boolean colorMatch(Color pixel)
    {
        assert pixel != null;
        return pixel.getRed() > 90 && pixel.getGreen() < 50 && pixel.getBlue() < 50;
    }

    public static boolean bobberBounce(Color pixel)
    {
        assert pixel != null;
        return pixel.getRed() > 200 && pixel.getGreen() > 220 && pixel.getBlue() > 220;
    }

}
