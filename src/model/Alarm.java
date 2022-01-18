package model;

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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }
            timet = timet - 1;
            delay = delay - 1000;
        }
        while (delay != 0);
    }

    /*Cancel countdown*/
    public void cancel()
    {
        exec.shutdownNow();
    }
    /*Start countdown*/
    public void start()
    {
        assert !exec.isShutdown();

        exec.submit(()->
        {
            run();
            listener.listen();
        });
        exec.shutdown();
    }
}
