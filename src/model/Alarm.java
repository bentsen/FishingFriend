package model;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */


import controller.Controller;

public class Alarm implements Runnable
{
    private Thread thread;
    private boolean running = false;
    private final Angle angle = new Angle();

    @Override
    public void run() {
        int timet = 25 * 60;
        long delay = timet * 1000;

        do{
            int minutes = timet / 60;
            int seconds = timet % 60;
            System.out.println(minutes + " minute(s), " + seconds + " Second(s)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timet = timet - 1;
            delay = delay - 1000;
        }
        while (delay != 0);
        Controller.fishing = false;
    }

    public synchronized void start()
    {
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop()
    {
        if(!running)
            return;
        running = false;
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
