package model;

import controller.Controller;
import model.singleton.Computer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */

public class Angle
{

    private final double ColourMultiplier  = 1.0;
    private final double ColourClosenessMultiplier  = 2.0;
    public Point bobber;
    public final Point startLocation = new Point(5,5);
    private final Computer computer = Computer.INSTANCE;
    private final Keyboard keyboard = new Keyboard();


    public void reconnect()
    {
        Robot bot = computer.getBot();
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        bot.delay(1000);
        keyboard.type("/camp");
        bot.delay(1000);
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(25000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Controller.fishing = true;
    }

    public void move(Point points) throws AWTException {
        Robot bot = new Robot();
        Point coordinates = points;

        if(coordinates.getX() != 0 || coordinates.getY() != 0)
        {
            bot.mouseMove((int) coordinates.getX(), (int) coordinates.getY());
        }
    }

    public void castLoop() throws AWTException {
        Robot bot  = new Robot();
        bot.delay(1500);
        bot.keyPress(KeyEvent.VK_4);
        bot.keyRelease(KeyEvent.VK_4);
    }

    public boolean fish() throws AWTException
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

                if(bobberBounce(Computer.parseByteColor(bufferedImage.getRGB(i,j))))
                {
                    System.out.println("plask!");
                    return true;
                }
            }
        }
        return false;
    }

    public boolean coordinates() throws AWTException {
        Robot bot = new Robot();
        Dimension toolkit = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle captureSize = new Rectangle(toolkit);
        captureSize.setSize((int) (toolkit.getWidth() - ((toolkit.getWidth()/100) * 55.21)),(int) (toolkit.getHeight() - ((toolkit.getHeight()/100) * 51.76)));
        captureSize.setLocation((int) ((toolkit.getWidth()/2) - captureSize.getWidth()/2) , 1);
        BufferedImage bufferedImage = bot.createScreenCapture(captureSize);

        int width = (int) ((toolkit.getWidth()/2) - captureSize.getWidth()/2);


        for (int i = 0; i < captureSize.getWidth(); i++) {
            for (int j = 0; j < captureSize.getHeight(); j++) {
                if (isMatch(Computer.parseByteColor(bufferedImage.getRGB(i, j)))) {
                    System.out.println("color found");
                    bobber = new Point(i + width, j + 1);
                    return true;
                } else {
                    //System.out.println("color not found");
                }
            }
        }
        return false;
    }

    private boolean bobberBounce(Color pixel)
    {
        assert pixel != null;
        return pixel.getRed() > 200 && pixel.getGreen() > 220 && pixel.getBlue() > 220;

    }

    private boolean isMatch(Color pixel)
    {
        byte red = (byte) pixel.getRed();
        byte green = (byte) pixel.getGreen();
        byte blue = (byte) pixel.getBlue();

        return isBigger(red, green) && isBigger(red, blue) && areClose(blue, green);
    }

    private boolean isBigger(byte red, byte other)
    {
        return (red * ColourMultiplier) > other;
    }

    private boolean areClose(byte color1, byte color2)
    {
        var max = Math.max(color1, color2);
        var min = Math.min(color1, color2);

        return min * ColourClosenessMultiplier > max - 20;
    }
}
