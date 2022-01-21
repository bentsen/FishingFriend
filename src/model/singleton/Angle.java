package model.singleton;


import javafx.application.Platform;
import model.Alarm;
import model.Keyboard;
import model.Lure;
import view.Console;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */

public enum Angle
{
    /*Makes Angle Singleton*/
    INSTANCE;


    /*Lure that is being used*/
    private final Lure lure = new Lure();
    /*Point of the bobber location*/
    private Point bobberLocation = null;
    /*Computer to communicate with hardware*/
    private final Computer computer = Computer.INSTANCE;
    /*Keyboard so we can translate char-sequences into key inputs*/
    private final Keyboard keyboard = new Keyboard();
    /*New thread*/
    private ExecutorService exec = null;
    /*Start location of cursor*/
    private Point startLocation = new Point(5,5);

    /*Hole fishing sequence*/
    public void fish()
    {
        assert isReady();
        exec = Executors.newSingleThreadExecutor();
        exec.submit(()->
        {
            /*30 min and relog will fire(Todo fix)*/
            //final Alarm alarm = new Alarm(1500, () -> reconnect());
            //alarm.start();

            while(!isInterrupted())
            {
                sleep(5000);

                move(startLocation);

                if(lure.isReady())
                {
                    lure.applyLure();
                }

                /*25 seconds to get bobber or lambda event will fire*/
                final Alarm alarm2 = new Alarm(25, () -> interruptFishing());
                alarm2.start();
                coordinates();
                bobbercatch();

                alarm2.cancel();
            }
            //alarm.cancel();
        });

    }
    /*Check and catch the bobber when splash color appear*/
    public boolean bobbercatch()
    {
        Rectangle rectangle = new Rectangle((int) bobberLocation.getX(), (int) bobberLocation.getY(),200,200);

        int width = 200;
        int height = 200;
        while(!isInterrupted()) {
            BufferedImage bufferedImage = computer.getBot().createScreenCapture(rectangle);
            /*Scan BufferedImage for water splash color match*/
            for (int i = 0; i < width; i++)
            {
                for (int j = 0; j < height; j++)
                {
                    if (Computer.SplashColor(Computer.parseByteColor(bufferedImage.getRGB(i, j))))
                    {
                        Platform.runLater(() -> Console.INSTANCE.setConsoleOutput("fish was caught"));
                        move(bobberLocation);
                        computer.getBot().delay(500);
                        computer.getBot().mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        computer.getBot().mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        lure.setReady(true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /*Check and find the bobber point location */
    public boolean coordinates()
    {

        int width = (int) ((computer.getDisplay().getWidth()/2) - computer.getBobberDisplay().getWidth()/2);
        int height = 1;

        while(!isInterrupted())
        {
            BufferedImage bufferedImage = computer.screenshot();
            /*Scan BufferedImage for bobber feather color match*/
            for (int i = 0; i < bufferedImage.getWidth(); i++)
            {
                for (int j = 0; j < bufferedImage.getHeight(); j++)
                {
                    if (Computer.isMatch(Computer.parseByteColor(bufferedImage.getRGB(i, j))))
                    {
                        bobberLocation = new Point(i + width, j + height);
                        Platform.runLater(() -> {
                            Console.INSTANCE.setConsoleOutput("color found");
                            Console.INSTANCE.setConsoleOutput("Bobberlocation: x="+bobberLocation.getX() + ",y="+bobberLocation.getY());
                        });
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /*Moves cursor to the given point*/
    public void move(Point points)
    {
        Point coordinates = points;

        if(coordinates.getX() != 0 || coordinates.getY() != 0)
        {
            computer.getBot().mouseMove((int) coordinates.getX(), (int) coordinates.getY());
        }
    }
    /*Reconnect character*/
    public void reconnect()
    {
        interrupt();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Robot bot = computer.getBot();
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        bot.delay(1000);
        keyboard.type("/camp");
        bot.delay(1000);
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        try
        {
            Thread.sleep(25000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        bot.keyPress(KeyEvent.VK_ENTER);
        bot.keyRelease(KeyEvent.VK_ENTER);
        try
        {
            Thread.sleep(10000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        fish();
    }
    /*Restart fishing session*/
    private void interruptFishing()
    {
        interrupt();
        lure.setReady(true);
        fish();
    }
    /*Check if angle is ready to begin fishing session*/
    public boolean isReady()
    {
        return exec == null || exec.isTerminated();
    }
    /*check if session is interrupted*/
    private boolean isInterrupted()
    {
        assert exec != null;
        return exec.isShutdown();
    }
    /*Interrupts the fishing session*/
    public void interrupt()
    {
        assert !isInterrupted();
        exec.shutdownNow();
    }
    /*Sleep the fishing thread*/
    private void sleep(int time)
    {
        try
        {
            Thread.sleep(time);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
