package model;

import javafx.application.Platform;
import view.Time;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */

public class Alarm
{
    /*Countdown time in seconds*/
    private final int time;
    /*Action when countdown ends*/
    private final Listener listener;
    /*New thread*/
    private final ExecutorService exec = Executors.newSingleThreadExecutor();

    public Alarm(final int time, final Listener listener)
    {
        this.time = time;
        this.listener = listener;
    }

    /*Countdown method*/
    public void run()
    {
        int timet = time;
        long delay = timet * 1000;

        do
        {
            int minutes = timet / 60;
            int seconds = timet % 60;
            System.out.println(minutes + " minute(s), " + seconds + " Second(s)");

            /*will post the Runnable to an event queue and then return immediately to the caller. ... Otherwise, the application may become unresponsive.*/
            /*returns time of alarm to GUI*/
            // TODO: 1/20/2022 fix so only the relog time will use the line under (maybe a if statement that check if time is 1500 seconds).
            Platform.runLater(()-> Time.INSTANCE.setTime(minutes + ":" + seconds));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().stop();
            }
            timet = timet - 1;
            delay = delay - 1000;
        }
        while (delay != 0);
    }

    /*Cancel countdown*/
    public void cancel()
    {
        System.out.println("Alarm got cancel");
        exec.shutdownNow();
    }
    /*Start countdown*/
    public void start()
    {
        assert !exec.isShutdown();

        exec.submit(()->
        {
            run();
            System.out.println("der blev ikke funder noget");
            listener.listen();
        });
        exec.shutdown();
    }
}
