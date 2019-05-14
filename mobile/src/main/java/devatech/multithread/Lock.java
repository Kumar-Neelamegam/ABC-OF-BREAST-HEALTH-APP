package devatech.multithread;

/**
 * Created by Ponnusamy M on 2/9/2017.
 */

public class Lock {

    private static int numberofCount;

    public Lock() {
        numberofCount=0;
    }

    public int getNumberofCount()
    {
        return numberofCount;
    }

    public void addNewThread()
    {
        numberofCount++;
    }
    public void removeThread()
    {
        numberofCount--;
    }
}
