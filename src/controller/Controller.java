package controller;

import javafx.application.Platform;

import model.singleton.Angle;
import model.singleton.Computer;


/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */

public enum Controller
{
    /*Makes Controller Singleton*/
    INSTANCE;

    /*Start fishing session*/
    public void start()
    {
        if(Angle.INSTANCE.isReady())
        {
            Angle.INSTANCE.fish();
        }
    }
    /*Stop fishing session*/
    public void stop()
    {

        if(!Angle.INSTANCE.isReady())
        {
            Angle.INSTANCE.interrupt();
        }
    }
}
