package model;

/*
 * Name: Mikkel Bentsen && Oliver rasoli
 * Date: 1/16/2022
 */

public class Lure
{
    /*Check if lure is ready to be applied*/
    private boolean ready = true;
    /*Keyboard so we can translate char-sequences into key inputs*/
    private final Keyboard keyboard = new Keyboard();

    /*Apply lure*/
    public void applyLure()
    {
        assert isReady();
        ready = false;
        keyboard.type("4");
    }
    /*set if lure is ready to be applied*/
    public void setReady(boolean ready)
    {
        this.ready = ready;
    }
    /*get if lure is ready to be applied*/
    public boolean isReady()
    {
        return ready;
    }
}
